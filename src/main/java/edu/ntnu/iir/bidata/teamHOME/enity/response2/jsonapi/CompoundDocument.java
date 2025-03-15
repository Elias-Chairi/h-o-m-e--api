package edu.ntnu.iir.bidata.teamHOME.enity.response2.jsonapi;

import java.util.List;

/**
 * Represents a compound document.
 */
public abstract class CompoundDocument<T> extends TopLevel<T> {
    private List<ResourceObject<?>> included;

    public CompoundDocument(T data, List<ResourceObject<?>> included) {
        super(data);
        this.included = included;
    }

    public List<ResourceObject<?>> getIncluded() {
        return included;
    }
}
