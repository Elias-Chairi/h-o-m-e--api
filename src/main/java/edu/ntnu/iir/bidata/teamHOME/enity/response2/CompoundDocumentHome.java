package edu.ntnu.iir.bidata.teamHOME.enity.response2;

import java.util.List;
import edu.ntnu.iir.bidata.teamHOME.enity.response2.jsonapi.CompoundDocument;
import edu.ntnu.iir.bidata.teamHOME.enity.response2.jsonapi.ResourceObject;
import edu.ntnu.iir.bidata.teamHOME.enity.response2.resources.HomesResource;

/**
 * Represents the top level of a home resource.
 */
public class CompoundDocumentHome extends CompoundDocument<HomesResource> {
    public CompoundDocumentHome(HomesResource data, List<ResourceObject<?>> included) {
        super(data, included);
    }
}
