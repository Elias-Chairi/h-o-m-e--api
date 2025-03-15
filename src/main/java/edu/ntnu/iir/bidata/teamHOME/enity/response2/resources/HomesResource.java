package edu.ntnu.iir.bidata.teamHOME.enity.response2.resources;

import java.util.Map;

import edu.ntnu.iir.bidata.teamHOME.enity.response2.jsonapi.RelationshipObjectToMany;
import edu.ntnu.iir.bidata.teamHOME.enity.response2.jsonapi.ResourceObject;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents a home resource.
 */
public class HomesResource extends ResourceObject<HomesAttributes> {
    private static final String type = "homes";

    public HomesResource(String id, HomesAttributes attributes, Map<String, RelationshipObjectToMany> relationships) {
        super(id, type, attributes, relationships);
    }

    @Schema(example = type)
    @Override
    public String getType() {
        return type;
    }
}
