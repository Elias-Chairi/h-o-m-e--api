package edu.ntnu.iir.bidata.teamhome.response.toplevel;

import edu.ntnu.iir.bidata.teamhome.response.jsonapi.CompoundDocument;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.ResourceObject;
import edu.ntnu.iir.bidata.teamhome.response.meta.Action;
import edu.ntnu.iir.bidata.teamhome.response.meta.MetaAction;
import java.util.List;

/**
 * Represents the top level of a home resource.
 */
public class CompoundDocumentMeta extends CompoundDocument<ResourceObject<?>> {
  private MetaAction meta;

  public CompoundDocumentMeta(ResourceObject<?> data, List<ResourceObject<?>> included,
      Action action, String clientId) {
    super(data, included);
    this.meta = new MetaAction(action, clientId);
  }

  public MetaAction getMeta() {
    return meta;
  }
}