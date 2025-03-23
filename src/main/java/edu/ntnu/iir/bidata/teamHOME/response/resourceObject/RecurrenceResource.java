package edu.ntnu.iir.bidata.teamhome.response.resourceobject;

import edu.ntnu.iir.bidata.teamhome.enity.Recurrence;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.RelationshipObject;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.ResourceObject;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.Map;

/**
 * Represents a recurrence resource.
 */
public class RecurrenceResource extends ResourceObject<RecurrenceResource.RecurrenceAttributes> {
  private static final String type = "recurrences";

  /**
   * Represents the attributes of a recurrence resource.
   */
  public static class RecurrenceAttributes {
    private int intervalDays;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Creates a new recurrence attributes.
     */
    public RecurrenceAttributes(int intervalDays, LocalDate startDate, LocalDate endDate) {
      this.intervalDays = intervalDays;
      this.startDate = startDate;
      this.endDate = endDate;
    }

    public int getIntervalDays() {
      return intervalDays;
    }

    public LocalDate getStartDate() {
      return startDate;
    }

    public LocalDate getEndDate() {
      return endDate;
    }
  }

  private RecurrenceResource(String id, RecurrenceAttributes attributes,
      Map<String, RelationshipObject> relationships) {
    super(id, type, attributes, relationships);
  }

  /**
   * Creates a new user resource.
   */
  public RecurrenceResource(Recurrence recurrence, Map<String, RelationshipObject> relationships) {
    this(
        Integer.toString(recurrence.getId()),
        new RecurrenceAttributes(
          recurrence.getIntervalDays(), 
          recurrence.getStartDate(), 
          recurrence.getEndDate()),
        relationships);
  }

  @Schema(example = type)
  @Override
  public String getType() {
    return type;
  }
}
