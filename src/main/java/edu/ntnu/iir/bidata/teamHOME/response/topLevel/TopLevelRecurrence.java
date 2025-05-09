package edu.ntnu.iir.bidata.teamhome.response.toplevel;

import edu.ntnu.iir.bidata.teamhome.response.jsonapi.TopLevel;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.RecurrenceResource;
import edu.ntnu.iir.bidata.teamhome.response.resourceobjectattributes.RecurrenceAttributes;

/**
 * Represents the top level of a recurrence resource.
 */
public class TopLevelRecurrence extends TopLevel<RecurrenceResource<RecurrenceAttributes>> {
  public TopLevelRecurrence(RecurrenceResource<RecurrenceAttributes> data) {
    super(data);
  }
}
