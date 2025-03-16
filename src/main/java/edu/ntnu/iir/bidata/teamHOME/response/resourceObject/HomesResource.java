package edu.ntnu.iir.bidata.teamHOME.response.resourceObject;

import java.util.Map;

import edu.ntnu.iir.bidata.teamHOME.enity.Home;
import edu.ntnu.iir.bidata.teamHOME.response.jsonapi.RelationshipObjectToMany;
import edu.ntnu.iir.bidata.teamHOME.response.jsonapi.ResourceObject;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents a home resource.
 */
public class HomesResource extends ResourceObject<HomesResource.HomesAttributes> {
    private static final String type = "homes";

    public static class HomesAttributes {
        private String name;

        public HomesAttributes(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private HomesResource(String id, HomesAttributes attributes, Map<String, RelationshipObjectToMany> relationships) {
        super(id, type, attributes, relationships);
    }

    public HomesResource(Home home, Map<String, RelationshipObjectToMany> relationships) {
        this(home.getId(), new HomesAttributes(home.getName()), relationships);
    }

    @Schema(example = type)
    @Override
    public String getType() {
        return type;
    }
}
