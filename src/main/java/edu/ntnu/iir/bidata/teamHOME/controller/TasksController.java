package edu.ntnu.iir.bidata.teamhome.controller;

import edu.ntnu.iir.bidata.teamhome.response.toplevel.TopLevelHome;
import edu.ntnu.iir.bidata.teamhome.response.toplevel.TopLevelTasks;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/** Controller for the task endpoints. */
@Tag(name = "Task", description = "The Task API")
@RestController
public class TasksController {
  // private SimpMessagingTemplate template;
  private static final Logger logger = LoggerFactory.getLogger(HomesController.class);

  @PostMapping("api/residents/{residentID}/tasks")
  public ResponseEntity<TopLevelTasks> createTask(
      @io.swagger.v3.oas.annotations.parameters.RequestBody @Valid @RequestBody TopLevelHome req,
      @RequestHeader String host) {
    // try {
    //     MysqlService.getInstance().createTask()
    // }
    return null;
  }
}
