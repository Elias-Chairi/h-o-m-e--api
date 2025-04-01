package edu.ntnu.iir.bidata.teamhome.controller;

import edu.ntnu.iir.bidata.teamhome.enity.Recurrence;
import edu.ntnu.iir.bidata.teamhome.enity.Task;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.RecurrenceResource;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.TasksResource;
import edu.ntnu.iir.bidata.teamhome.response.toplevel.TopLevelRecurrence;
import edu.ntnu.iir.bidata.teamhome.response.toplevel.TopLevelTask;
import edu.ntnu.iir.bidata.teamhome.service.MysqlService;
import edu.ntnu.iir.bidata.teamhome.service.exception.DbEntityNotFoundException;
import edu.ntnu.iir.bidata.teamhome.service.exception.DbForeignKeyViolationException;
import edu.ntnu.iir.bidata.teamhome.util.EntityResourceMapper;
import edu.ntnu.iir.bidata.teamhome.util.exception.BadResourceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/** Controller for the task endpoints. */
@Tag(name = "Task", description = "The Task API")
@RestController
public class TasksController {
  // private SimpMessagingTemplate template;
  private static final Logger logger = LoggerFactory.getLogger(HomesController.class);

  /**
   * Create a task.
   *
   * @param req The request object containing the task details.
   * @param residentId The ID of the resident that creates the task.
   * @return 201 CREATED if the task was created, 422 UNPROCESSABLE ENTITY if the request is
   *     invalid, 500 INTERNAL SERVER ERROR if an error occurred.
   */
  @Operation(summary = "Create a task", description = "Create a task")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Task created",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TopLevelTask.class))),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(
            responseCode = "422",
            description = "Unprocessable entity (for any id that does not exist)",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content),
      })
  @PostMapping("/api/residents/{residentId}/tasks")
  public ResponseEntity<TopLevelTask> createTask(
      @io.swagger.v3.oas.annotations.parameters.RequestBody @Valid @RequestBody TopLevelTask req,
      @Parameter(description = "The ID of the residents that creates the task") @PathVariable
          int residentId,
      UriComponentsBuilder uriBuilder) {
    try {
      Task task =
          MysqlService.getInstance()
              .createTask(EntityResourceMapper.fromResource(req.getData(), residentId));
      TasksResource resource = EntityResourceMapper.fromEntity(task);
      URI location = uriBuilder.path("/api/tasks/{id}").buildAndExpand(task.getId()).toUri();
      return ResponseEntity.created(location).body(new TopLevelTask(resource));
    } catch (BadResourceException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } catch (DbForeignKeyViolationException e) {
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    } catch (SQLException e) {
      logger.error("Failed to create task", e);
      return ResponseEntity.status(500).build();
    }
  }

  /**
   * Create a recurrence for a task.
   *
   * @param req The request object containing the recurrence details.
   * @param taskId The ID of the task to create the recurrence for.
   * @return 201 CREATED if the recurrence was created, 400 BAD REQUEST if the request is invalid,
   *     422 UNPROCESSABLE ENTITY if the request is invalid, 500 INTERNAL SERVER ERROR if an error
   *     occurred.
   */
  @Operation(summary = "Create a recurrence", description = "Create a recurrence")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Recurrence created",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TopLevelRecurrence.class))),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(
            responseCode = "422",
            description = "Unprocessable entity (for any id that does not exist)",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content),
      })
  @PostMapping("/api/tasks/{taskId}/relationships/recurrence")
  public ResponseEntity<TopLevelRecurrence> createRecurrence(
      @io.swagger.v3.oas.annotations.parameters.RequestBody @Valid @RequestBody
          TopLevelRecurrence req,
      @Parameter(description = "The ID of the task to create the recurrence for") @PathVariable
          int taskId,
      UriComponentsBuilder uriBuilder) {
    try {

      Recurrence recurrence =
          MysqlService.getInstance()
              .createRecurrence(EntityResourceMapper.fromResource(req.getData()), taskId);

      RecurrenceResource resource = EntityResourceMapper.fromEntity(recurrence, taskId);
      URI location = uriBuilder.path("/api/tasks/{id}").buildAndExpand(recurrence.getId()).toUri();
      return ResponseEntity.created(location).body(new TopLevelRecurrence(resource));
    } catch (DbForeignKeyViolationException e) {
      return ResponseEntity.unprocessableEntity().build();
    } catch (SQLException e) {
      logger.error("Failed to create recurrence", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
   * Update a task.
   *
   * @param req The request object containing the task details.
   * @param taskId The ID of the task to update.
   * @return 204 NO CONTENT if the task was updated, 400 BAD REQUEST if the request is invalid, 404
   *     NOT FOUND if the task does not exist, 500 INTERNAL SERVER ERROR if an error occurred.
   */
  @Operation(summary = "Update a task", description = "Update a task")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Task updated"),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Task not found", content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content),
      })
  @PatchMapping("/api/tasks/{taskId}")
  public ResponseEntity<Void> updateTask(
      @io.swagger.v3.oas.annotations.parameters.RequestBody @RequestBody TopLevelTask req,
      @Parameter(description = "The ID of the task to update") @PathVariable int taskId,
      UriComponentsBuilder uriBuilder) {
    try {
      MysqlService.getInstance()
          .updateTask(EntityResourceMapper.fromResource(req.getData()), taskId);
      URI location = uriBuilder.path("/api/tasks/{id}").buildAndExpand(taskId).toUri();
      return ResponseEntity.noContent().location(location).build();
    } catch (IllegalArgumentException | BadResourceException e) {
      return ResponseEntity.badRequest().build();
    } catch (DbEntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (SQLException e) {
      logger.error("Failed to update task", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
   * Delete the recurrence of a task.
   *
   * @param taskId The ID of the task to delete the recurrence for.
   * @return 204 NO CONTENT if the recurrence was deleted, 404 NOT FOUND if the task does not exist,
   *     500 INTERNAL SERVER ERROR if an error occurred.
   */
  @Operation(summary = "Delete the recurrence of a task", description = "Delete the recurrence")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Recurrence deleted"),
        @ApiResponse(responseCode = "404", description = "Task not found", content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content),
      })
  @DeleteMapping("/api/tasks/{taskId}/relationships/recurrence")
  public ResponseEntity<Void> deleteRecurrence(
      @Parameter(description = "The ID of the task to delete the recurrence for") @PathVariable
          int taskId) {
    try {
      MysqlService.getInstance().deleteTaskRecurrence(taskId);
      return ResponseEntity.noContent().build();
    } catch (DbEntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (SQLException e) {
      logger.error("Failed to delete recurrence", e);
      return ResponseEntity.internalServerError().build();
    }
  }
}
