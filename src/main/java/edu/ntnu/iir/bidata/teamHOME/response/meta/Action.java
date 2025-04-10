package edu.ntnu.iir.bidata.teamhome.response.meta;

/**
 * Enum representing the action types for tasks.
 * 
 * <p>This enum is used to specify the type of action being performed on a task.
 */
public enum Action {
  ADD("add"),
  REMOVE("remove"),
  UPDATE("update");

  private final String action;

  Action(String action) {
    this.action = action;
  }

  @Override
  public String toString() {
    return this.action;
  }
}
