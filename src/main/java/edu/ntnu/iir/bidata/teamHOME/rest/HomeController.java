package edu.ntnu.iir.bidata.teamHOME.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import edu.ntnu.iir.bidata.teamHOME.MysqlController;
import edu.ntnu.iir.bidata.teamHOME.enity.CreateHomeReposponse;
import edu.ntnu.iir.bidata.teamHOME.enity.CreateHomeRequest;

/**
 * Controller for the home endpoint.
 */
@RestController
public class HomeController {
	// private SimpMessagingTemplate template;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Check if a home exists.
	 * 
	 * @param homeID The ID of the home to check.
	 * @return 200 OK if the home exists, 404 NOT FOUND if the home does not exist, or
	 *         500 INTERNAL SERVER ERROR if an error occurred.
	 */
	@GetMapping("/api/home/{homeID}")
	public HttpStatus isHome(@PathVariable("homeID") String homeID) {
		try {
			if (MysqlController.getInstance().isHome(homeID)) {
				return HttpStatus.OK;
			}
			return HttpStatus.NOT_FOUND;
		} catch (SQLException e) {
			logger.error("Failed to check if home exists", e);
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}

	/**
	 * Create a home with a user.
	 * 
	 * @param req The request object containing the name of the home and the name of
	 *            the user.
	 * @return The response object containing the homeID and the userID.
	 */
	@PostMapping("api/home")
	public ResponseEntity<CreateHomeReposponse> createHomeWithUser(@Valid @RequestBody CreateHomeRequest req){
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

}