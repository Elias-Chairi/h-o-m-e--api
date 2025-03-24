package edu.ntnu.iir.bidata.teamhome.util;

import edu.ntnu.iir.bidata.teamhome.enity.Home;
import edu.ntnu.iir.bidata.teamhome.enity.Resident;
import edu.ntnu.iir.bidata.teamhome.enity.Task;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.RelationshipObject;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.RelationshipObjectToOne;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.ResourceIdentifierObject;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.HomesResource;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.ResidentsResource;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.TasksResource;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.TasksResource.TasksAttributes;
import edu.ntnu.iir.bidata.teamhome.util.exception.BadResourceException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Maps a resource object to an entity object.
 */
public class EntityResourceMapper {

  /**
   * Maps a ResidentsResource to a Resident.
   *
   * @param residentsResource The resource object to map.
   * @param homeId The ID of the home the resident belongs to.
   * @return The mapped Resident object.
   */
  public static Resident fromResource(ResidentsResource residentsResource, String homeId) {
    String name = residentsResource.getAttributes().getName();
    return new Resident(name, homeId);
  }

  /**
   * Maps a HomesResource to a Home.
   *
   * @param homesResource The resource object to map.
   * @return The mapped Home object.
   */
  public static Home fromResource(HomesResource homesResource) {
    String name = homesResource.getAttributes().getName();
    return new Home(name);
  }

  /**
   * Maps a TasksResource to a Task.
   *
   * @param tasksResource The resource object to map.
   * @param residentId The ID of the resident who created the task.
   * @return The mapped Task object.
   * @throws BadResourceException If the resource object is invalid.
   */
  public static Task fromResource(TasksResource tasksResource, int residentId)
      throws BadResourceException {
    TasksAttributes att = tasksResource.getAttributes();
    Task.Builder taskBuilder = new Task.Builder(
        att.getName(),
        att.getDescription(),
        att.getDue(),
        residentId, // createdBy
        att.isDone());

    Map<String, RelationshipObject> relationships = tasksResource.getRelationships();
    if (relationships == null) {
      return taskBuilder.build();
    }

    try {
      for (Entry<String, RelationshipObject> entry : relationships.entrySet()) {
        switch (entry.getKey()) {
          case "assignedTo":
            ResourceIdentifierObject assignedTo = ((RelationshipObjectToOne) entry.getValue())
                .getData();
            if (!assignedTo.getType().equals("residents")) {
              throw new BadResourceException("Invalid relationship type found in resource object");
            }
            taskBuilder.assignedTo(Integer.parseInt(assignedTo.getId()));
            break;
          case "recurrence":
            ResourceIdentifierObject recurrence = ((RelationshipObjectToOne) entry.getValue())
                .getData();
            if (!recurrence.getType().equals("recurrences")) {
              throw new BadResourceException("Invalid relationship type found in resource object");
            }
            taskBuilder.recurrenceId(Integer.parseInt(recurrence.getId()));
            break;
          default:
            throw new BadResourceException("Invalid relationship name found in resource object");
        }
      }
    } catch (Exception e) {
      throw new BadResourceException("Invalid relationship object found in resource object");
    }

    return taskBuilder.build();
  }

  /**
   * Maps a Task to a TasksResource.
   *
   * @param task The entity object to map.
   * @return The mapped TasksResource object.
   */
  public static TasksResource fromEntity(Task task) {
    Map<String, RelationshipObject> relationships = new HashMap<>();
    relationships.put("createdBy",
        new RelationshipObjectToOne(
            new ResourceIdentifierObject(
                "residents", Integer.toString(task.getCreatedBy()))));

    if (task.getAssignedTo() != null) {
      relationships.put(
          "assignedTo",
          new RelationshipObjectToOne(
              new ResourceIdentifierObject("residents", Integer.toString(task.getAssignedTo()))));
    }
    if (task.getRecurrenceId() != null) {
      relationships.put(
          "recurrence",
          new RelationshipObjectToOne(
              new ResourceIdentifierObject(
                  "recurrences", Integer.toString(task.getRecurrenceId()))));
    }
    return new TasksResource(task, relationships);
  }
}