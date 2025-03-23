package edu.ntnu.iir.bidata.teamhome.response.toplevel;

import edu.ntnu.iir.bidata.teamhome.response.jsonapi.TopLevel;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.ResidentsResource;

/**
 * Represents the top level of a resident resource.
 */
public class TopLevelResident extends TopLevel<ResidentsResource> {
  public TopLevelResident(ResidentsResource data) {
    super(data);
  }
}
