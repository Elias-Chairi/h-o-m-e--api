package edu.ntnu.iir.bidata.teamhome.response.toplevel;

import edu.ntnu.iir.bidata.teamhome.response.jsonapi.TopLevel;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.TasksResource;
import edu.ntnu.iir.bidata.teamhome.response.resourceobjectattributes.TasksAttributes;

/**
 * Represents the top level of a task resource.
 */
public class TopLevelTask extends TopLevel<TasksResource<TasksAttributes>> {
  public TopLevelTask(TasksResource<TasksAttributes> data) {
    super(data);
  }
}
