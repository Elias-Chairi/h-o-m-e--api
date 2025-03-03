package edu.ntnu.iir.bidata.teamHOME.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.ntnu.iir.bidata.teamHOME.MysqlController;

/**
 * Controller for the home endpoint.
 */
@RestController
public class HomeController {
	// private SimpMessagingTemplate template;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

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
}