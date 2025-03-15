package edu.ntnu.iir.bidata.teamHOME.enity.response2;

import edu.ntnu.iir.bidata.teamHOME.enity.response2.jsonapi.TopLevel;
import edu.ntnu.iir.bidata.teamHOME.enity.response2.resources.HomesResource;

/**
 * Represents the top level of a home resource.
 */
public class TopLevelHome extends TopLevel<HomesResource> {
    public TopLevelHome(HomesResource data) {
        super(data);
    }
}
