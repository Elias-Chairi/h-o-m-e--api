package edu.ntnu.iir.bidata.teamhome.response.toplevel;

import edu.ntnu.iir.bidata.teamhome.response.jsonapi.TopLevel;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.TasksResource;
import edu.ntnu.iir.bidata.teamhome.response.resourceobjectattributes.TasksUpdateAttributes;

/**
 * Represents the top level of a task update resource.
 */
public class TopLevelTaskUpdate extends TopLevel<TasksResource<TasksUpdateAttributes>> {
  public TopLevelTaskUpdate(TasksResource<TasksUpdateAttributes> data) {
    super(data);
  }
}
