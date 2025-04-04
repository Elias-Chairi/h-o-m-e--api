package edu.ntnu.iir.bidata.teamhome.controller;

import edu.ntnu.iir.bidata.teamhome.enity.Home;
import edu.ntnu.iir.bidata.teamhome.enity.Resident;
import edu.ntnu.iir.bidata.teamhome.enity.Task;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.RelationshipObject;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.RelationshipObjectToMany;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.ResourceIdentifierObject;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.ResourceObject;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.HomesResource;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.ResidentsResource;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.TasksResource;
import edu.ntnu.iir.bidata.teamhome.response.toplevel.CompoundDocumentHome;
import edu.ntnu.iir.bidata.teamhome.response.toplevel.TopLevelHome;
import edu.ntnu.iir.bidata.teamhome.service.MysqlService;
import edu.ntnu.iir.bidata.teamhome.service.exception.DbEntityNotFoundException;
import edu.ntnu.iir.bidata.teamhome.util.EntityResourceMapper;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/** Controller for the home endpoints. */
@Tag(name = "Home", description = "The Home API")
@RestController
public class HomesController {
  // private SimpMessagingTemplate template;
  private static final Logger logger = LoggerFactory.getLogger(HomesController.class);

  /**
   * Create a home.
   *
   * @param req The request object containing the home details.
   * @return 201 CREATED if the home was created, 400 BAD REQUEST if the request is invalid, 500
   *     INTERNAL SERVER ERROR if an error occurred.
   */
  @Operation(summary = "Create a home", description = "Create a home")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Home created",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TopLevelHome.class))),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content),
      })
  @PostMapping("/api/homes")
  public ResponseEntity<TopLevelHome> createHome(
      @io.swagger.v3.oas.annotations.parameters.RequestBody @Valid @RequestBody TopLevelHome req,
      UriComponentsBuilder uriBuilder) {
    try {
      Home home =
          MysqlService.getInstance().createHome(EntityResourceMapper.fromResource(req.getData()));
      URI location =
          uriBuilder.path("/api/homes/{id}").buildAndExpand(home.getId()).toUri();
      return ResponseEntity.created(location)
          .body(new TopLevelHome(new HomesResource(home, null)));
    } catch (SQLException e) {
      logger.error("Failed to create home", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
   * Get home.
   *
   * @param homeId The ID of the home to get tasks from.
   * @param include (optional) The resources to include in the response.
   * @return The response object containing the home details.
   */
  @Operation(summary = "Get home details", description = "Get home details")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Home details",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CompoundDocumentHome.class))),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Home not found", content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content),
      })
  @GetMapping("/api/homes/{homeId}")
  public ResponseEntity<CompoundDocumentHome> getHome(
      @Parameter(description = "The ID of the home to get") @PathVariable String homeId,
      @Parameter(description = "The resources to include in the response (residents,tasks)")
          @RequestParam(required = false)
          String include) {
    try {
      MysqlService mysqlService = MysqlService.getInstance();
      List<Resident> residents = null;
      final Home home = mysqlService.getHome(homeId);

      Map<String, RelationshipObject> relationships = new HashMap<>();
      List<ResourceObject<?>> included = new ArrayList<>();
      if (include != null) {
        String[] includedResources = include.split(",");
        for (String includedResource : includedResources) {
          switch (includedResource) {
            case "tasks":
              List<Task> tasks = mysqlService.getTasks(homeId);
              List<ResourceIdentifierObject> taskIdentifers = new ArrayList<>();
              for (Task task : tasks) {
                taskIdentifers.add(
                    new ResourceIdentifierObject("tasks", Integer.toString(task.getId())));
                included.add(TasksResource.fromEntity(task));
              }
              relationships.put("tasks", new RelationshipObjectToMany(taskIdentifers));
              break;
            case "residents":
              if (residents == null) {
                residents = mysqlService.getResidents(homeId);
              }
              List<ResourceIdentifierObject> residentIdentifers = new ArrayList<>();
              for (Resident resident : residents) {
                residentIdentifers.add(
                    new ResourceIdentifierObject("residents", Integer.toString(resident.getId())));
                included.add(ResidentsResource.fromEntity(resident));
              }
              relationships.put("residents", new RelationshipObjectToMany(residentIdentifers));
              break;
            default:
              return ResponseEntity.badRequest().build();
          }
        }
      }

      if (relationships.isEmpty()) {
        relationships = null;
      }

      if (included.isEmpty()) {
        included = null;
      }

      HomesResource homeResource = new HomesResource(home, relationships);
      return ResponseEntity.ok(new CompoundDocumentHome(homeResource, included));
    } catch (DbEntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (SQLException e) {
      logger.error("Failed to get tasks", e);
      return ResponseEntity.internalServerError().build();
    }
  }
}
