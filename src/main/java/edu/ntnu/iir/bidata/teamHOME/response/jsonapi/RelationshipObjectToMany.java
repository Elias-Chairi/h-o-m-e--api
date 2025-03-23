package edu.ntnu.iir.bidata.teamhome.response.jsonapi;

import java.util.List;

/**
 * Represents a relationship object with a to-many relationship.
 */
public class RelationshipObjectToMany implements RelationshipObject {
  private List<ResourceIdentifierObject> data;

  public RelationshipObjectToMany(List<ResourceIdentifierObject> data) {
    this.data = data;
  }

  public List<ResourceIdentifierObject> getData() {
    return data;
  }
}
