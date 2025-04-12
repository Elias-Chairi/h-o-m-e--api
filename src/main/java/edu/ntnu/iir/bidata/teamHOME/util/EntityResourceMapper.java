package edu.ntnu.iir.bidata.teamhome.util;

import edu.ntnu.iir.bidata.teamhome.enity.Home;
import edu.ntnu.iir.bidata.teamhome.enity.Recurrence;
import edu.ntnu.iir.bidata.teamhome.enity.Resident;
import edu.ntnu.iir.bidata.teamhome.enity.Task;
import edu.ntnu.iir.bidata.teamhome.entityupdate.RecurrenceUpdate;
import edu.ntnu.iir.bidata.teamhome.entityupdate.TaskUpdate;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.RelationshipObject;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.RelationshipObjectToOne;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.ResourceIdentifierObject;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.HomesResource;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.RecurrenceResource;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.ResidentsResource;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.TasksResource;
import edu.ntnu.iir.bidata.teamhome.response.resourceobjectattributes.RecurrenceAttributes;
import edu.ntnu.iir.bidata.teamhome.response.resourceobjectattributes.RecurrenceUpdateAttributes;
import edu.ntnu.iir.bidata.teamhome.response.resourceobjectattributes.TasksAttributes;
import edu.ntnu.iir.bidata.teamhome.response.resourceobjectattributes.TasksUpdateAttributes;
import edu.ntnu.iir.bidata.teamhome.util.exception.BadResourceException;
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
   * Maps a TasksResource to a TaskUpdate.
   *
   * @param tasksResource The resource object to map.
   * @return The mapped TaskUpdate object.
   * @throws BadResourceException If the resource object is invalid.
   */
  public static TaskUpdate fromUpdateResource(TasksResource<TasksUpdateAttributes> tasksResource)
      throws BadResourceException {
    TasksUpdateAttributes att = tasksResource.getAttributes();
    if (att == null) {
      throw new BadResourceException("Invalid attributes object found in resource object");
    }

    TaskUpdate update = new TaskUpdate();

    try {
      att.getName().ifPresent(name -> {
        update.setName(name);
      });
      att.getDescription().ifPresent(description -> {
        update.setDescription(description);
      });
      att.getDue().ifPresent(due -> {
        update.setDue(due);
      });
      att.isDone().ifPresent(done -> {
        update.setDone(done);
      });
    } catch (NullPointerException e) {
      throw new BadResourceException("Invalid attributes object found in resource object", e);
    }

    Map<String, RelationshipObject> relationships = tasksResource.getRelationships();
    if (relationships == null) {
      return update;
    }

    try {
      for (Entry<String, RelationshipObject> entry : relationships.entrySet()) {
        switch (entry.getKey()) {
          case "assignedTo":
            ResourceIdentifierObject assignedTo = ((RelationshipObjectToOne) entry.getValue())
                .getData();
            if (assignedTo == null) {
              update.setAssignedTo(null);
            } else if (!assignedTo.getType().equals("residents")) {
              throw new BadResourceException("Invalid relationship type found in resource object");
            } else {
              update.setAssignedTo(Integer.parseInt(assignedTo.getId()));
            }

            break;
          case "recurrence":
            ResourceIdentifierObject recurrence = ((RelationshipObjectToOne) entry.getValue())
                .getData();
            if (recurrence == null) {
              update.setRecurrenceId(null);
            } else {
              throw new BadResourceException("Does not support updating recurrence");
            }
            break;
          default:
            throw new BadResourceException("Invalid relationship name found in resource object");
        }
      }
    } catch (ClassCastException | NullPointerException e) {
      throw new BadResourceException("Invalid relationship object found in resource object", e);
    }
    return update;
  }

  /**
   * Maps a TasksResource to a Task.
   *
   * @param tasksResource The resource object to map.
   * @param residentId The ID of the resident who created the task.
   * @return The mapped Task object.
   * @throws BadResourceException If the resource object is invalid.
   */
  public static Task fromResource(TasksResource<TasksAttributes> tasksResource, int residentId)
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
   * Maps a RecurrenceResource to a RecurrenceUpdate.
   *
   * @param recurrenceResource The resource object to map.
   * @return The mapped RecurrenceUpdate object.
   * @throws BadResourceException If the resource object is invalid.
   */
  public static RecurrenceUpdate fromUpdateResource(
      RecurrenceResource<RecurrenceUpdateAttributes> recurrenceResource)
      throws BadResourceException {

    RecurrenceUpdateAttributes att = recurrenceResource.getAttributes();
    if (att == null) {
      throw new BadResourceException("Invalid attributes object found in resource object");
    }

    RecurrenceUpdate update = new RecurrenceUpdate();

    try {
      att.getIntervalDays().ifPresent(intervalDays -> {
        update.setIntervalDays(intervalDays);
      });
      att.getStartDate().ifPresent(startDate -> {
        update.setStartDate(startDate);
      });
      att.getEndDate().ifPresent(endDate -> {
        update.setEndDate(endDate);
      });
    } catch (NullPointerException e) {
      throw new BadResourceException("Invalid attributes object found in resource object", e);
    }

    return update;
  }

  /**
   * Maps a RecurrenceResource to a Recurrence.
   *
   * @param recurrenceResource The resource object to map.
   * @return The mapped Recurrence object.
   */
  public static Recurrence fromResource(
      RecurrenceResource<RecurrenceAttributes> recurrenceResource) {
    RecurrenceAttributes att = recurrenceResource.getAttributes();
    return new Recurrence(
        att.getIntervalDays(),
        att.getStartDate(),
        att.getEndDate());
  }
}