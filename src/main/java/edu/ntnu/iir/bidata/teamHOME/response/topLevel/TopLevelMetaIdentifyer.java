package edu.ntnu.iir.bidata.teamhome.response.toplevel;

import edu.ntnu.iir.bidata.teamhome.response.jsonapi.ResourceIdentifierObject;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.TopLevel;
import edu.ntnu.iir.bidata.teamhome.response.meta.Action;
import edu.ntnu.iir.bidata.teamhome.response.meta.MetaAction;

/**
 * Represents a top level object with an action.
 */
public class TopLevelMetaIdentifyer extends TopLevel<ResourceIdentifierObject> {
  private MetaAction meta;

  public TopLevelMetaIdentifyer(ResourceIdentifierObject data, Action action, String clientId) {
    super(data);
    this.meta = new MetaAction(action, clientId);
  }
  
  public MetaAction getMeta() {
    return meta;
  }
}
