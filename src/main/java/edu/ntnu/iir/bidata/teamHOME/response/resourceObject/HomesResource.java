package edu.ntnu.iir.bidata.teamhome.response.resourceobject;

import edu.ntnu.iir.bidata.teamhome.enity.Home;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.RelationshipObject;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.ResourceObject;
import io.micrometer.common.lang.NonNull;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Map;

/**
 * Represents a home resource.
 */
public class HomesResource extends ResourceObject<HomesResource.HomesAttributes> {
  @NonNull
  private static final String type = "homes";

  /**
   * Represents the attributes of a home resource.
   */
  public static class HomesAttributes {
    @NotBlank
    @Size(min = 2, max = 30)
    private String name;

    public HomesAttributes(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }

  private HomesResource(String id, HomesAttributes attributes,
      Map<String, RelationshipObject> relationships) {
    super(id, type, attributes, relationships);
  }

  public HomesResource(Home home, Map<String, RelationshipObject> relationships) {
    this(home.getId(), new HomesAttributes(home.getName()), relationships);
  }

  @Schema(example = type)
  @Override
  public String getType() {
    return type;
  }
}
