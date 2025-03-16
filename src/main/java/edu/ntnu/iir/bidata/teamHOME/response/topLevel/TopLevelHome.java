package edu.ntnu.iir.bidata.teamHOME.response.topLevel;

import edu.ntnu.iir.bidata.teamHOME.response.jsonapi.TopLevel;
import edu.ntnu.iir.bidata.teamHOME.response.resourceObject.HomesResource;

/**
 * Represents the top level of a home resource.
 */
public class TopLevelHome extends TopLevel<HomesResource> {
    public TopLevelHome(HomesResource data) {
        super(data);
    }
}
