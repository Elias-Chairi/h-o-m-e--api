package edu.ntnu.iir.bidata.teamhome.response.jsonapi;

/**
 * Represents a top level object.
 */
public abstract class TopLevel<T> {
  private T data;

  public TopLevel(T data) {
    this.data = data;
  }

  public T getData() {
    return data;
  }
}
