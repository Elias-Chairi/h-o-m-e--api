package edu.ntnu.iir.bidata.teamhome.response.toplevel;

import edu.ntnu.iir.bidata.teamhome.response.attributesobject.TasksAttributes;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.TopLevel;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.TasksResource;

/**
 * Represents the top level of a home resource.
 */
public class TopLevelTask extends TopLevel<TasksResource<TasksAttributes>> {
  public TopLevelTask(TasksResource<TasksAttributes> data) {
    super(data);
  }
}
