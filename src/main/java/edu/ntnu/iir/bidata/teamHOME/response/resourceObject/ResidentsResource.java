package edu.ntnu.iir.bidata.teamHOME.response.resourceObject;

import java.util.Map;

import edu.ntnu.iir.bidata.teamHOME.enity.Resident;
import edu.ntnu.iir.bidata.teamHOME.response.jsonapi.RelationshipObjectToMany;
import edu.ntnu.iir.bidata.teamHOME.response.jsonapi.ResourceObject;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents a user resource.
 */
public class ResidentsResource extends ResourceObject<ResidentsResource.ResidentsAttributes> {
    private static final String type = "residents";

    public static class ResidentsAttributes {
        private String name;

        public ResidentsAttributes(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private ResidentsResource(String id, ResidentsAttributes attributes,
            Map<String, RelationshipObjectToMany> relationships) {
        super(id, type, attributes, relationships);
    }

    public ResidentsResource(Resident resident, Map<String, RelationshipObjectToMany> relationships) {
        this(Integer.toString(resident.getId()), new ResidentsAttributes(resident.getName()), relationships);
    }

    @Schema(example = type)
    @Override
    public String getType() {
        return type;
    }
}
