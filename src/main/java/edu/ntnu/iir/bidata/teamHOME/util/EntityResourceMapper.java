package edu.ntnu.iir.bidata.teamhome.util;

import edu.ntnu.iir.bidata.teamhome.enity.Home;
import edu.ntnu.iir.bidata.teamhome.enity.Resident;
import edu.ntnu.iir.bidata.teamhome.enity.Task;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.HomesResource;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.ResidentsResource;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.TasksResource;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.TasksResource.TasksAttributes;

/**
 * Maps a resource object to an entity object.
 */
public class EntityResourceMapper {

  /**
   * Maps a ResidentsResource to a Resident.
   */
  public static Resident fromResource(ResidentsResource residentsResource, String homeId) {
    String name = residentsResource.getAttributes().getName();
    return new Resident(name, homeId);
  }

  /**
   * Maps a HomesResource to a Home.
   */
  public static Home fromResource(HomesResource homesResource) {
    String name = homesResource.getAttributes().getName();
    return new Home(name);
  }

  /**
   * Maps a TasksResource to a Task.
   */
  public static Task fromResource(TasksResource tasksResource) {
    TasksAttributes att = tasksResource.getAttributes();
    return new Task(
        att.getName(),
        att.getDescription(),
        att.getAssignedTo(),
        att.getDue(),
        att.getCreated(),
        att.getCreatedBy(),
        att.isDone(),
        att.getRecurrenceId());
  }
}