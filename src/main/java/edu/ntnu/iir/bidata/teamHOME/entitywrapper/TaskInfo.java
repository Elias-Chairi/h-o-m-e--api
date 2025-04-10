package edu.ntnu.iir.bidata.teamhome.entitywrapper;

import edu.ntnu.iir.bidata.teamhome.enity.Recurrence;
import edu.ntnu.iir.bidata.teamhome.enity.Task;

/**
 * Represents a task info object that contains a task, its recurrence, and the home ID.
 */
public class TaskInfo {
  Task task;
  Recurrence recurrence;
  String homeId;

  /**
   * Creates a new TaskInfo object.
   *
   * @param task       the task
   * @param recurrence the recurrence
   * @param homeId     the home ID
   */
  public TaskInfo(Task task, Recurrence recurrence, String homeId) {
    this.task = task;
    this.recurrence = recurrence;
    this.homeId = homeId;
  }

  public Task getTask() {
    return task;
  }

  public Recurrence getRecurrence() {
    return recurrence;
  }

  public String getHomeId() {
    return homeId;
  }
}
