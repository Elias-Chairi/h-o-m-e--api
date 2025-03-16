package edu.ntnu.iir.bidata.teamHOME.controller;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import edu.ntnu.iir.bidata.teamHOME.enity.CreateTaskRequest;
import edu.ntnu.iir.bidata.teamHOME.enity.response.ApiResponseTask;
import edu.ntnu.iir.bidata.teamHOME.enity.response.Task;
import edu.ntnu.iir.bidata.teamHOME.service.MysqlService;
import edu.ntnu.iir.bidata.teamHOME.service.exception.SQLForeignKeyViolationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controller for the task endpoints.
 */
@Tag(name = "Task", description = "The Task API")
@RestController
public class TaskController {
    // private SimpMessagingTemplate template;
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    // /**
    //  * Create a task.
    //  * 
    //  * @param req The request object containing the task details.
    //  * @return 201 CREATED if the task was created, 400 BAD REQUEST if the request
    //  *         is invalid, 422 UNPROCESSABLE ENTITY if the request is valid but has
    //  *         invalid values or 500 INTERNAL SERVER ERROR if an error occurred.
    //  */
    // @Operation(summary = "Create a task", description = "Create a task")
    // @ApiResponses(value = {
    //         @ApiResponse(responseCode = "201", description = "Task created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseTask.class))),
    //         @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
    //         @ApiResponse(responseCode = "422", description = "Unprocessable entity", content = @Content),
    //         @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),

    // })
    // @PostMapping("/api/task")
    // public ResponseEntity<ApiResponseTask> createTask(
    //         @io.swagger.v3.oas.annotations.parameters.RequestBody @RequestBody @Valid CreateTaskRequest req) {
    //     try {
    //         Task task = MysqlController.getInstance().createTask(req);
    //         return ResponseEntity.status(HttpStatus.CREATED)
    //                 .body(new ApiResponseTask("New task created", task));
    //     } catch (SQLForeignKeyViolationException e) {
    //         return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    //     } catch (SQLException e) {
    //         logger.error("Failed to create task", e);
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    //     }
    // }
}
