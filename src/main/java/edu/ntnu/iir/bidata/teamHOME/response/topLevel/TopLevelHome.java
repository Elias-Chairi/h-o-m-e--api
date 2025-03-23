package edu.ntnu.iir.bidata.teamhome.response.toplevel;

import edu.ntnu.iir.bidata.teamhome.response.jsonapi.TopLevel;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.HomesResource;

/**
 * Represents the top level of a home resource.
 */
public class TopLevelHome extends TopLevel<HomesResource> {
  public TopLevelHome(HomesResource data) {
    super(data);
  }
}
