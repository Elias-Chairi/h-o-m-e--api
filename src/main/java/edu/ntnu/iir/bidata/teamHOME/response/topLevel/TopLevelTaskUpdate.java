package edu.ntnu.iir.bidata.teamhome.response.toplevel;

import edu.ntnu.iir.bidata.teamhome.response.attributesobject.TasksUpdateAttributes;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.TopLevel;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.TasksResource;

/**
 * Represents the top level of a home resource.
 */
public class TopLevelTaskUpdate extends TopLevel<TasksResource<TasksUpdateAttributes>> {
  public TopLevelTaskUpdate(TasksResource<TasksUpdateAttributes> data) {
    super(data);
  }
}
