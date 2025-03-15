package edu.ntnu.iir.bidata.teamHOME.enity.response2;

import java.util.List;

import edu.ntnu.iir.bidata.teamHOME.enity.response2.jsonapi.TopLevel;
import edu.ntnu.iir.bidata.teamHOME.enity.response2.resources.TasksResource;

/**
 * Represents the top level of a home resource.
 */
public class TopLevelTasks extends TopLevel<List<TasksResource>> {
    public TopLevelTasks(List<TasksResource> data) {
        super(data);
    }
}
