package edu.ntnu.iir.bidata.teamhome.response.meta;

/**
 * Represents a meta action object.
 * This class is used to encapsulate an action object.
 */
public class MetaAction {
  private Action action;
  private String clientId;

  public MetaAction(Action action, String clientId) {
    this.action = action;
    this.clientId = clientId;
  }
  
  public String getAction() {
    return action.toString();
  }

  public String getClientId() {
    return clientId;
  }
}
