package edu.ntnu.iir.bidata.teamhome.response.jsonapi;

/**
 * Represents a relationship object with a to-one relationship.
 */
public class RelationshipObjectToOne extends RelationshipObject {
  private ResourceIdentifierObject data;

  public RelationshipObjectToOne(ResourceIdentifierObject data) {
    this.data = data;
  }

  public ResourceIdentifierObject getData() {
    return data;
  }
}
