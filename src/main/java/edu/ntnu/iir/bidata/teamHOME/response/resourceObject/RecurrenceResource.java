package edu.ntnu.iir.bidata.teamhome.response.resourceobject;

import edu.ntnu.iir.bidata.teamhome.enity.Recurrence;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.RelationshipObject;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.ResourceObject;
import edu.ntnu.iir.bidata.teamhome.response.resourceobjectattributes.RecurrenceAttributes;
import io.micrometer.common.lang.NonNull;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;

/**
 * Represents a recurrence resource.
 */
public class RecurrenceResource<T> extends ResourceObject<T> {
  @NonNull
  private static final String type = "recurrences";

  private RecurrenceResource(String id, T attributes,
      Map<String, RelationshipObject> relationships) {
    super(id, type, attributes, relationships);
  }

  /**
   * Creates a new recurrence resource from a recurrence entity.
   */
  public static RecurrenceResource<RecurrenceAttributes> fromEntity(Recurrence recurrence) {
    return new RecurrenceResource<>(
        Integer.toString(recurrence.getId()),
        new RecurrenceAttributes(
            recurrence.getIntervalDays(),
            recurrence.getStartDate(),
            recurrence.getEndDate()),
        null);
  }

  @Schema(example = type)
  @Override
  public String getType() {
    return type;
  }
}
