package edu.ntnu.iir.bidata.teamhome.entityupdate;

import edu.ntnu.iir.bidata.teamhome.util.NullableOptional;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Represents a recurrence update.
 */
public class RecurrenceUpdate {
  private Optional<Integer> intervalDays;
  private Optional<LocalDate> startDate;
  private NullableOptional<LocalDate> endDate;

  /**
   * Creates a new recurrence update with empty values.
   */
  public RecurrenceUpdate() {
    this.intervalDays = Optional.empty();
    this.startDate = Optional.empty();
    this.endDate = NullableOptional.empty();
  }

  public Optional<Integer> getIntervalDays() {
    return intervalDays;
  }

  public void setIntervalDays(Integer intervalDays) {
    this.intervalDays = Optional.of(intervalDays);
  }

  public Optional<LocalDate> getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = Optional.of(startDate);
  }

  public NullableOptional<LocalDate> getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = NullableOptional.of(endDate);
  }

  /**
   * Checks if the recurrence update is empty.
   *
   * @return true if the recurrence update is empty, false otherwise
   */
  public boolean isEmpty() {
    return intervalDays.isEmpty() && startDate.isEmpty() && endDate.isEmpty();
  }
}