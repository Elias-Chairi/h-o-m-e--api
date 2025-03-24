package edu.ntnu.iir.bidata.teamhome.response.toplevel;

import edu.ntnu.iir.bidata.teamhome.response.jsonapi.CompoundDocument;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.ResourceObject;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.HomesResource;
import java.util.List;

/**
 * Represents the top level of a home resource.
 */
public class CompoundDocumentHome extends CompoundDocument<HomesResource> {
  public CompoundDocumentHome(HomesResource data, List<ResourceObject<?>> included) {
    super(data, included);
  }
}