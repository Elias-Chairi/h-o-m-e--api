package edu.ntnu.iir.bidata.teamhome.response.toplevel;

import edu.ntnu.iir.bidata.teamhome.response.attributesobject.TasksAttributes;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.TopLevel;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.TasksResource;
import java.util.List;

/**
 * Represents the top level of a home resource.
 */
public class TopLevelTasks extends TopLevel<List<TasksResource<TasksAttributes>>> {
  public TopLevelTasks(List<TasksResource<TasksAttributes>> data) {
    super(data);
  }
}
