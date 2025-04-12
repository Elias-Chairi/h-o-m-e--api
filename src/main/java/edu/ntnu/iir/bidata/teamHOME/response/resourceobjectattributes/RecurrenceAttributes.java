package edu.ntnu.iir.bidata.teamhome.response.resourceobjectattributes;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Represents the attributes of a recurrence resource.
 */
public class RecurrenceAttributes {

  @Min(1)
  @Max(365)
  private int intervalDays;

  @NotNull
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