package edu.ntnu.iir.bidata.teamhome.response.jsonapi;

import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;

/**
 * Represents a resource object.
 */
public abstract class ResourceObject<T> {
  private String id;
  @Expose
  private String type;
  private T attributes;
  @Schema(example = "{\"additionalProp1\": {\"data\": {\"type\": \"string\", \"id\": \"string\"}}}")
  private Map<String, RelationshipObject> relationships;

  /**
   * Creates a new resource object.
   */
  public ResourceObject(String id, String type, T attributes,
      Map<String, RelationshipObject> relationships) {
    this.id = id;
    this.type = type;
    this.attributes = attributes;
    this.relationships = relationships;
  }

  public String getId() {
    return id;
  }

  public abstract String getType();

  public T getAttributes() {
    return attributes;
  }

  public Map<String, RelationshipObject> getRelationships() {
    return relationships;
  }
}