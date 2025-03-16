package edu.ntnu.iir.bidata.teamHOME.response.jsonapi;

import java.util.List;

public class RelationshipObjectToMany {
    private List<ResourceIdentifierObject> data;

    public RelationshipObjectToMany(List<ResourceIdentifierObject> data) {
        this.data = data;
    }

    public List<ResourceIdentifierObject> getData() {
        return data;
    }
}
