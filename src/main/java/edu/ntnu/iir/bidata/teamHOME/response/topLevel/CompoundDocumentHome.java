package edu.ntnu.iir.bidata.teamHOME.response.topLevel;

import java.util.List;

import edu.ntnu.iir.bidata.teamHOME.response.jsonapi.CompoundDocument;
import edu.ntnu.iir.bidata.teamHOME.response.jsonapi.ResourceObject;
import edu.ntnu.iir.bidata.teamHOME.response.resourceObject.HomesResource;

/**
 * Represents the top level of a home resource.
 */
public class CompoundDocumentHome extends CompoundDocument<HomesResource> {
    public CompoundDocumentHome(HomesResource data, List<ResourceObject<?>> included) {
        super(data, included);
    }
}