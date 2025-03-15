package edu.ntnu.iir.bidata.teamHOME.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import edu.ntnu.iir.bidata.teamHOME.enity.CreateHomeRequest;
import edu.ntnu.iir.bidata.teamHOME.enity.CreateUserRequest;
import edu.ntnu.iir.bidata.teamHOME.enity.data.Home;
import edu.ntnu.iir.bidata.teamHOME.enity.data.Resident;
import edu.ntnu.iir.bidata.teamHOME.enity.data.Task;
import edu.ntnu.iir.bidata.teamHOME.enity.response.ApiResponseHomeDetails;
import edu.ntnu.iir.bidata.teamHOME.enity.response2.CompoundDocumentHome;
import edu.ntnu.iir.bidata.teamHOME.enity.response2.TopLevelHome;
import edu.ntnu.iir.bidata.teamHOME.enity.response2.jsonapi.RelationshipObjectToMany;
import edu.ntnu.iir.bidata.teamHOME.enity.response2.jsonapi.ResourceIdentifierObject;
import edu.ntnu.iir.bidata.teamHOME.enity.response2.jsonapi.ResourceObject;
import edu.ntnu.iir.bidata.teamHOME.enity.response2.jsonapi.TopLevel;
import edu.ntnu.iir.bidata.teamHOME.enity.response2.resources.HomesResource;
import edu.ntnu.iir.bidata.teamHOME.enity.response2.resources.ResidentsAttributes;
import edu.ntnu.iir.bidata.teamHOME.enity.response2.resources.ResidentsResource;
import edu.ntnu.iir.bidata.teamHOME.enity.response2.resources.TasksAttributes;
import edu.ntnu.iir.bidata.teamHOME.enity.response2.resources.TasksResource;
import edu.ntnu.iir.bidata.teamHOME.enity.response2.resources.HomesAttributes;
// import edu.ntnu.iir.bidata.teamHOME.enity.response.User;
// import edu.ntnu.iir.bidata.teamHOME.enity.response.Home;
// import edu.ntnu.iir.bidata.teamHOME.enity.response.HomeDetails;
// import edu.ntnu.iir.bidata.teamHOME.enity.response.Task;
// import edu.ntnu.iir.bidata.teamHOME.enity.response.ApiResponseHomeDetails;
// import edu.ntnu.iir.bidata.teamHOME.enity.response.ApiResponseHomeUser;
// import edu.ntnu.iir.bidata.teamHOME.enity.response.ApiResponseUser;
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

	// /**
	// * Get home.
	// *
	// * @param homeID The ID of the home.
	// * @return 200 OK if the home exists, 404 NOT FOUND if the home does not
	// exist,
	// * or 500 INTERNAL SERVER ERROR if an error occurred.
	// */
	// @Operation(summary = "Get home", description = "Get home")
	// @ApiResponses(value = {
	// @ApiResponse(responseCode = "200", description = "Home exists", content =
	// @Content(mediaType = "application/json", schema = @Schema(implementation =
	// TopLevelHome.class))),
	// @ApiResponse(responseCode = "404", description = "Home does not exist",
	// content = @Content),
	// @ApiResponse(responseCode = "500", description = "Internal server error",
	// content = @Content),

	// })
	// @GetMapping("/api/homes/{homeID}")
	// public ResponseEntity<TopLevelHome> getHome(
	// @Parameter(description = "The ID of the home") @PathVariable("homeID") String
	// homeID) {
	// try {
	// Home home = MysqlController.getInstance().getHome(homeID);
	// return ResponseEntity
	// .ok(new TopLevelHome(new HomesResource(home.getId(), new
	// HomesAttributes(home.getName()), null)));
	// } catch (SQLEntityNotFoundException e) {
	// return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	// } catch (SQLException e) {
	// logger.error("Failed to check if home exists", e);
	// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	// }
	// }

	// /**
	// * Create a home with a user.
	// *
	// * @param req The request object containing the home and the user resources.
	// * @return The response object containing the homeID and the userID.
	// */
	// @Operation(summary = "Create home with user", description = "Create a home
	// with a user")

	// @ApiResponses(value = {
	// @ApiResponse(responseCode = "201", description = "Home successfully created",
	// content = @Content(mediaType = "application/json", schema =
	// @Schema(implementation = ApiResponseHomeUser.class))),
	// @ApiResponse(responseCode = "400", description = "Bad request", content =
	// @Content),
	// @ApiResponse(responseCode = "500", description = "Internal server error",
	// content = @Content),

	// })
	// @PostMapping("api/homes")
	// public ResponseEntity<ApiResponseHomeUser> createHomeWithUser(
	// @io.swagger.v3.oas.annotations.parameters.RequestBody @Valid @RequestBody
	// CreateHomeRequest req) {
	// try {
	// MysqlController mysqlController = MysqlController.getInstance();
	// Home home = mysqlController.createHome(req.getHomeName());
	// User user = mysqlController.createResident(home.getId(), req.getUserName());
	// return ResponseEntity.status(HttpStatus.CREATED)
	// .body(new ApiResponseHomeUser("New home and user created", home, user));
	// } catch (SQLException e) {
	// logger.error("Failed to create home", e);
	// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	// }
	// }

	// /**
	// * Create a user in a home.
	// *
	// * @param homeID The ID of the home to add the user to.
	// * @param req The request object containing the name of the user.
	// * @return The response object containing the userID.
	// */
	// @Operation(summary = "Create user in home", description = "Create a user in a
	// home")
	// @ApiResponses(value = {
	// @ApiResponse(responseCode = "201", description = "User successfully created",
	// content = @Content(mediaType = "application/json", schema =
	// @Schema(implementation = ApiResponseUser.class))),
	// @ApiResponse(responseCode = "400", description = "Bad request", content =
	// @Content),
	// @ApiResponse(responseCode = "404", description = "Home not found", content =
	// @Content),
	// @ApiResponse(responseCode = "500", description = "Internal server error",
	// content = @Content),

	// })
	// @PostMapping("api/home/{homeID}")
	// public ResponseEntity<ApiResponseUser> createUserInHome(
	// @Parameter(description = "The ID of the home to add the user to")
	// @PathVariable("homeID") String homeID,
	// @io.swagger.v3.oas.annotations.parameters.RequestBody @Valid @RequestBody
	// CreateUserRequest req) {
	// try {
	// User user = MysqlController.getInstance().createResident(homeID,
	// req.getUserName());
	// return ResponseEntity.status(HttpStatus.CREATED).body(new
	// ApiResponseUser("New user created", user));
	// } catch (SQLForeignKeyViolationException e) {
	// return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	// } catch (SQLException e) {
	// logger.error("Failed to add user to home", e);
	// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	// }
	// }

	// TODO: /api/homes/{homeID}/users
	// TODO: /api/homes/{homeID}/tasks

	/**
	 * Get home.
	 *
	 * @param homeID               The ID of the home to get tasks from.
	 * @param associatedWithUserID (optional) The ID of the user that requested the
	 *                             tasks.
	 * @param include              (optional) The resources to include in the
	 *                             response.
	 * @return The response object containing the home details.
	 */
	@Operation(summary = "Get home details", description = "Get home details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Home details", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompoundDocumentHome.class))),
			@ApiResponse(responseCode = "404", description = "Home not found", content = @Content),
			@ApiResponse(responseCode = "409", description = "User not associated withhome", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),

	})

	@GetMapping("api/homes/{homeID}")
	public ResponseEntity<CompoundDocumentHome> getHomeDetails(
			@Parameter(description = "The ID of the home to get details for") @PathVariable("homeID") String homeID,
			@Parameter(description = "To get CONFLICT status for userIDs not in home") @RequestParam(required = false) Integer associatedWithUserID,
			@Parameter(description = "The resources to include in the response (residents,tasks)") @RequestParam(required = false) String include) {
		try {

			MysqlController mysqlController = MysqlController.getInstance();

			List<Resident> residents = null;
			if (associatedWithUserID != null) {
				residents = mysqlController.getResidents(homeID);
				if (residents.stream().noneMatch(user -> user.getId() == associatedWithUserID)) {
					return ResponseEntity.status(HttpStatus.CONFLICT).build();
				}
			}

			Home home = mysqlController.getHome(homeID);

			Map<String, RelationshipObjectToMany> relationships = new HashMap<>();
			List<ResourceObject<?>> included = new ArrayList<>();
			if (include != null) {
				String[] includedResources = include.split(",");
				for (String includedResource : includedResources) {
					switch (includedResource) {
						case "tasks":
							List<Task> tasks = mysqlController.getTasks(homeID);
							List<ResourceIdentifierObject> taskIdentifers = new ArrayList<>();
							for (Task task : tasks) {
								taskIdentifers
										.add(new ResourceIdentifierObject("tasks", Integer.toString(task.getId())));
								included.add(new TasksResource(
										Integer.toString(task.getId()),
										new TasksAttributes(task.getName(), task.getDescription(), task.getAssignedTo(),
												task.getDue(), task.getCreated(), task.getCreatedBy(), task.isDone(),
												task.getRecurrenceID()),
										null));
							}
							relationships.put("tasks", new RelationshipObjectToMany(taskIdentifers));
							break;
						case "residents":
							if (residents == null) {
								residents = mysqlController.getResidents(homeID);
							}
							List<ResourceIdentifierObject> residentIdentifers = new ArrayList<>();
							for (Resident resident : residents) {
								residentIdentifers.add(
										new ResourceIdentifierObject("residents", Integer.toString(resident.getId())));
								included.add(new ResidentsResource(Integer.toString(resident.getId()),
										new ResidentsAttributes(resident.getName()),
										null));
							}
							relationships.put("residents", new RelationshipObjectToMany(residentIdentifers));
							break;
					}
				}
			}

			if (relationships.isEmpty()) {
				relationships = null;
			}

			if (included.isEmpty()) {
				included = null;
			}

			HomesResource homeResource = new HomesResource(home.getId(), new HomesAttributes(home.getName()),
					relationships);
			return ResponseEntity.ok(new CompoundDocumentHome(homeResource, included));
		} catch (SQLEntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (SQLException e) {
			logger.error("Failed to get tasks", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}