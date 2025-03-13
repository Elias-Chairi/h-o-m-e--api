package edu.ntnu.iir.bidata.teamHOME.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import edu.ntnu.iir.bidata.teamHOME.enity.CreateHomeReposponse;
import edu.ntnu.iir.bidata.teamHOME.enity.CreateHomeRequest;
import edu.ntnu.iir.bidata.teamHOME.enity.CreateUserReposponse;
import edu.ntnu.iir.bidata.teamHOME.enity.CreateUserRequest;
import edu.ntnu.iir.bidata.teamHOME.enity.GetTasksReposponse.GetTasksReposponse;
import edu.ntnu.iir.bidata.teamHOME.enity.GetTasksReposponse.Task;
import edu.ntnu.iir.bidata.teamHOME.enity.GetTasksReposponse.User;
import edu.ntnu.iir.bidata.teamHOME.mysql.MysqlController;
import edu.ntnu.iir.bidata.teamHOME.mysql.SQLEntityNotFoundException;
import edu.ntnu.iir.bidata.teamHOME.mysql.SQLForeignKeyViolationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller for the home endpoints.
 */
@Tag(name = "Home", description = "The Home API")
@RestController
public class HomeController {
	// private SimpMessagingTemplate template;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Check if a home exists.
	 * 
	 * @param homeID The ID of the home to check.
	 * @return 200 OK if the home exists, 404 NOT FOUND if the home does not exist,
	 *         or 500 INTERNAL SERVER ERROR if an error occurred.
	 */
	@Operation(summary = "Check if home exists", description = "Check if a home exists")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Home exists", content = @Content),
			@ApiResponse(responseCode = "404", description = "Home does not exist", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),

	})
	@GetMapping("/api/home/{homeID}")
	public ResponseEntity<Void> isHome(
			@Parameter(description = "The ID of the home to check") @PathVariable("homeID") String homeID) {
		try {
			if (MysqlController.getInstance().getHomeName(homeID) != null) {
				return ResponseEntity.status(HttpStatus.OK).build();
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (SQLEntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (SQLException e) {
			logger.error("Failed to check if home exists", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Create a home with a user.
	 * 
	 * @param req The request object containing the name of the home and the name of
	 *            the user.
	 * @return The response object containing the homeID and the userID.
	 */
	@Operation(summary = "Create home with user", description = "Create a home with a user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Home successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateHomeReposponse.class))),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),

	})
	@PostMapping("api/home")
	public ResponseEntity<CreateHomeReposponse> createHomeWithUser(
			@io.swagger.v3.oas.annotations.parameters.RequestBody @Valid @RequestBody CreateHomeRequest req) {
		try {
			MysqlController mysqlController = MysqlController.getInstance();
			String homeID = mysqlController.createHome(req.getHomeName());
			int userID = mysqlController.createResident(homeID, req.getUserName());
			return ResponseEntity.status(HttpStatus.CREATED).body(new CreateHomeReposponse(homeID, userID));
		} catch (SQLException e) {
			logger.error("Failed to create home", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Create a user in a home.
	 *
	 * @param homeID The ID of the home to add the user to.
	 * @param req    The request object containing the name of the user.
	 * @return The response object containing the userID.
	 */
	@Operation(summary = "Create user in home", description = "Create a user in a home")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "User successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateUserReposponse.class))),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Home not found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),

	})
	@PostMapping("api/home/{homeID}")
	public ResponseEntity<CreateUserReposponse> createUserInHome(
			@Parameter(description = "The ID of the home to add the user to") @PathVariable("homeID") String homeID,
			@io.swagger.v3.oas.annotations.parameters.RequestBody @Valid @RequestBody CreateUserRequest req) {
		try {
			int userID = MysqlController.getInstance().createResident(homeID, req.getUserName());
			return ResponseEntity.status(HttpStatus.CREATED).body(new CreateUserReposponse(userID));
		} catch (SQLForeignKeyViolationException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (SQLException e) {
			logger.error("Failed to add user to home", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * <p>
	 * Get tasks for a home.
	 * 
	 * <p>
	 * The {@code userID} is used by the client to check if the stored user is valid
	 * for the
	 * home. If the user is not valid, the client needs to create a new user in the
	 * home and get a new {@code userID} by calling
	 * {@link #createUserInHome(String, CreateUserRequest)} before calling this
	 * method again.
	 * 
	 * @param homeID The ID of the home to get tasks from.
	 * @param userID The ID of the user that requested the tasks.
	 * @return The response object containing the home name, a list of users in the
	 */
	@Operation(summary = "Get tasks", description = "Get tasks for a home")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Tasks successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetTasksReposponse.class))),
			@ApiResponse(responseCode = "404", description = "Home not found", content = @Content),
			@ApiResponse(responseCode = "409", description = "User not associated with home", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),

	})
	@GetMapping("api/home/{homeID}/{userID}")
	public ResponseEntity<GetTasksReposponse> getTasks(
			@Parameter(description = "The ID of the home to get tasks from") @PathVariable("homeID") String homeID,
			@Parameter(description = "The ID of the user that requested the tasks") @PathVariable("userID") int userID) {
		try {
			MysqlController mysqlController = MysqlController.getInstance();
			List<User> users = mysqlController.getUsers(homeID);
			if (users.stream().noneMatch(user -> user.getUserID() == userID)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
			String homeName = mysqlController.getHomeName(homeID);
			List<Task> tasks = mysqlController.getTasks(homeID);
			return ResponseEntity.ok(new GetTasksReposponse(homeName, users, tasks));
		} catch (SQLEntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (SQLException e) {
			logger.error("Failed to get tasks", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}