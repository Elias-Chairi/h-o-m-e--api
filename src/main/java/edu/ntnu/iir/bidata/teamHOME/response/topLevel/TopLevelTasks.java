package edu.ntnu.iir.bidata.teamHOME.response.topLevel;

import java.util.List;

import edu.ntnu.iir.bidata.teamHOME.response.jsonapi.TopLevel;
import edu.ntnu.iir.bidata.teamHOME.response.resourceObject.TasksResource;

/**
 * Represents the top level of a home resource.
 */
public class TopLevelTasks extends TopLevel<List<TasksResource>> {
    public TopLevelTasks(List<TasksResource> data) {
        super(data);
    }
}
