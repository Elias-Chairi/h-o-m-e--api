package edu.ntnu.iir.bidata.teamhome.controller;

import edu.ntnu.iir.bidata.teamhome.enity.Resident;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.ResidentsResource;
import edu.ntnu.iir.bidata.teamhome.response.toplevel.TopLevelResident;
import edu.ntnu.iir.bidata.teamhome.service.MysqlService;
import edu.ntnu.iir.bidata.teamhome.service.NotificationService;
import edu.ntnu.iir.bidata.teamhome.service.exception.DbForeignKeyViolationException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/** Controller for the resident endpoints. */
@Tag(name = "Resident", description = "The Resident API")
@RestController
public class ResidentsController {
  private static final Logger logger = LoggerFactory.getLogger(ResidentsController.class);
  private final NotificationService notificationService;
  private final MysqlService mysqlService;

  public ResidentsController(NotificationService notificationService, MysqlService mysqlService) {
    this.notificationService = notificationService;
    this.mysqlService = mysqlService;
  }

  /**
   * Create a resident.
   *
   * @param req The request object containing the resident details.
   * @param homeId The ID of the home to create the resident in.
   * @return 201 CREATED if the resident was created, 400 BAD REQUEST if the request is invalid, 404
   *     NOT FOUND if the home does not exist, 500 INTERNAL SERVER ERROR if an error occurred.
   */
  @Operation(summary = "Create a resident", description = "Create a resident")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Resident created",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TopLevelResident.class))),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Home not found", content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content),
      })
  @PostMapping("/api/homes/{homeId}/residents")
  public ResponseEntity<TopLevelResident> createResident(
      @io.swagger.v3.oas.annotations.parameters.RequestBody @Valid @RequestBody
          TopLevelResident req,
      @Parameter(description = "The ID of the home to create residents in") @PathVariable
          String homeId,
      @RequestHeader(name = "X-Client-Id", required = false) String clientId,
      UriComponentsBuilder uriBuilder) {
    try {
      Resident resident = 
          mysqlService.createResident(EntityResourceMapper.fromResource(req.getData(), homeId));
      
      ResidentsResource residentResource = ResidentsResource.fromEntity(resident);

      this.notificationService.notifyResidentCreation(homeId, residentResource, clientId);

      URI location =
          uriBuilder.path("/api/residents/{id}").buildAndExpand(resident.getId()).toUri();
      return ResponseEntity.created(location)
          .body(new TopLevelResident(residentResource));
    } catch (DbForeignKeyViolationException e) {
      return ResponseEntity.notFound().build();
    } catch (SQLException e) {
      logger.error("Failed to create resident", e);
      return ResponseEntity.internalServerError().build();
    }
  }
}
