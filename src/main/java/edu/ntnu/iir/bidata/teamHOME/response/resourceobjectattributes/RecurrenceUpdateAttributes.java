package edu.ntnu.iir.bidata.teamhome.response.resourceobjectattributes;

import edu.ntnu.iir.bidata.teamhome.util.NullableOptional;
import java.time.LocalDate;

/**
 * Represents the attributes of a recurrence update resource.
 */
public class RecurrenceUpdateAttributes {

  private NullableOptional<Integer> intervalDays = NullableOptional.empty();
  private NullableOptional<LocalDate> startDate = NullableOptional.empty();
  private NullableOptional<LocalDate> endDate = NullableOptional.empty();


  private RecurrenceUpdateAttributes() {    
    // private constructor to prevent instantiation (only used for deserialization with Gson)
  }

  public NullableOptional<Integer> getIntervalDays() {
    return intervalDays;
  }

  public NullableOptional<LocalDate> getStartDate() {
    return startDate;
  }

  public NullableOptional<LocalDate> getEndDate() {
    return endDate;
  }
}
