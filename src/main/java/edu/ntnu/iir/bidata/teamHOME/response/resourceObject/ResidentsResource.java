package edu.ntnu.iir.bidata.teamhome.response.resourceobject;

import edu.ntnu.iir.bidata.teamhome.enity.Resident;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.RelationshipObject;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.RelationshipObjectToOne;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.ResourceIdentifierObject;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.ResourceObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Map;

/**
 * Represents a user resource.
 */
public class ResidentsResource extends ResourceObject<ResidentsResource.ResidentsAttributes> {
  private static final String type = "residents";

  /**
   * Represents the attributes of a user resource.
   */
  public static class ResidentsAttributes {
    @NotBlank
    @Size(min = 2, max = 30)
    private String name;

    public ResidentsAttributes(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }

  private ResidentsResource(String id, ResidentsAttributes attributes,
      Map<String, RelationshipObject> relationships) {
    super(id, type, attributes, relationships);
  }

  /**
   * Creates a new user resource.
   */
  public static ResidentsResource fromEntity(Resident resident) {
    Map<String, RelationshipObject> relationships = Map.of(
        "home",
        new RelationshipObjectToOne(
            new ResourceIdentifierObject("homes", resident.getHomeId())));

    return new ResidentsResource(
        Integer.toString(resident.getId()),
        new ResidentsAttributes(resident.getName()),
        relationships);
  }

  @Schema(example = type)
  @Override
  public String getType() {
    return type;
  }
}
