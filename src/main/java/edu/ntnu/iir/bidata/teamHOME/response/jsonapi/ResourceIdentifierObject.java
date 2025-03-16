package edu.ntnu.iir.bidata.teamHOME.response.jsonapi;

/**
 * Represents a resource identifier object.
 */
public class ResourceIdentifierObject {
    private String type;
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
