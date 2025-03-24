package edu.ntnu.iir.bidata.teamhome.response.toplevel;

import edu.ntnu.iir.bidata.teamhome.response.jsonapi.TopLevel;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.RecurrenceResource;

/**
 * Represents the top level of a recurrence resource.
 */
public class TopLevelRecurrence extends TopLevel<RecurrenceResource> {
  public TopLevelRecurrence(RecurrenceResource data) {
    super(data);
  }
}
