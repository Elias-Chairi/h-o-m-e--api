package edu.ntnu.iir.bidata.teamhome.response.toplevel;

import edu.ntnu.iir.bidata.teamhome.response.jsonapi.TopLevel;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.RecurrenceResource;
import edu.ntnu.iir.bidata.teamhome.response.resourceobjectattributes.RecurrenceUpdateAttributes;

/**
 * Represents the top level of a recurrence update resource.
 */
public class TopLevelRecurrenceUpdate extends TopLevel<RecurrenceResource<RecurrenceUpdateAttributes>> {
  public TopLevelRecurrenceUpdate(RecurrenceResource<RecurrenceUpdateAttributes> data) {
    super(data);
  }
}
