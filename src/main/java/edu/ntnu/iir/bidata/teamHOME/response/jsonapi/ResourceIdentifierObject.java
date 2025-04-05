package edu.ntnu.iir.bidata.teamhome.response.jsonapi;

import jakarta.validation.constraints.NotNull;

/**
 * Represents a resource identifier object.
 */
public class ResourceIdentifierObject {
  @NotNull
  private String type;
  @NotNull
  private String id;

  public ResourceIdentifierObject(String type, String id) {
    this.type = type;
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public String getId() {
    return id;
  }
}
