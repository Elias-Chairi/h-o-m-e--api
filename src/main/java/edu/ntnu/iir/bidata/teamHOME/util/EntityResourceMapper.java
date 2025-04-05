package edu.ntnu.iir.bidata.teamhome.util;

import edu.ntnu.iir.bidata.teamhome.enity.Home;
import edu.ntnu.iir.bidata.teamhome.enity.Recurrence;
import edu.ntnu.iir.bidata.teamhome.enity.Resident;
import edu.ntnu.iir.bidata.teamhome.enity.Task;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.RelationshipObject;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.RelationshipObjectToOne;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.ResourceIdentifierObject;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.HomesResource;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.RecurrenceResource;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.ResidentsResource;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.TasksResource;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.TasksResource.TasksAttributes;
import edu.ntnu.iir.bidata.teamhome.service.MysqlService;
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
   * Maps a Task to a Map containing the task's attributes as key-value pairs.
   *
   * @param tasksResource The entity object to map.
   * @return The mapped Map object.
   * @throws BadResourceException If the resource object is invalid.
   * @see MysqlService#UPDATABLE_TASK_FIELDS
   * @see MysqlService#NULLABLE_TASK_FIELDS
   */
  public static Map<String, Object> fromResource(TasksResource tasksResource)
      throws BadResourceException {
    TasksAttributes att = tasksResource.getAttributes();
    Map<String, Object> map = new HashMap<>();
    if (att.getName() != null) {
      map.put("name", att.getName());
    }
    if (att.getDescription() != null) {
      map.put("description", att.getDescription());
    }
    if (att.getDue() != null) {
      map.put("due", att.getDue());
    }
    if (att.isDone() != null) {
      map.put("done", att.isDone());
    }

    Map<String, RelationshipObject> relationships = tasksResource.getRelationships();
    if (relationships == null) {
      return map;
    }

    try {
      for (Entry<String, RelationshipObject> entry : relationships.entrySet()) {
        switch (entry.getKey()) {
          case "assignedTo":
            ResourceIdentifierObject assignedTo = ((RelationshipObjectToOne) entry.getValue())
                .getData();
            if (assignedTo == null) {
              map.put("assignedTo", null);
            } else if (!assignedTo.getType().equals("residents")) {
              throw new BadResourceException("Invalid relationship type found in resource object");
            } else {
              map.put("assignedTo", Integer.parseInt(assignedTo.getId()));
            }

            break;
          case "recurrence":
            ResourceIdentifierObject recurrence = ((RelationshipObjectToOne) entry.getValue())
                .getData();
            if (recurrence == null) {
              map.put("recurrence_id", null);
            } else {
              throw new BadResourceException("Does not support updating recurrence");
            }
            break;
          default:
            throw new BadResourceException("Invalid relationship name found in resource object");
        }
      }
    } catch (ClassCastException | NullPointerException e) {
      throw new BadResourceException("Invalid relationship object found in resource object");
    }
    return map;
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
    } catch (ClassCastException | NullPointerException e) {
      throw new BadResourceException("Invalid relationship object found in resource object");
    }

    return taskBuilder.build();
  }

  /**
   * Maps a RecurrenceResource to a Recurrence.
   *
   * @param recurrenceResource The resource object to map.
   * @return The mapped Recurrence object.
   */
  public static Recurrence fromResource(RecurrenceResource recurrenceResource) {
    RecurrenceResource.RecurrenceAttributes att = recurrenceResource.getAttributes();
    return new Recurrence(
        att.getIntervalDays(),
        att.getStartDate(),
        att.getEndDate());
  }
}