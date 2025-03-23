package edu.ntnu.iir.bidata.teamhome.enity;

import java.time.LocalDate;

/**
 * Represents a recurrence.
 */
public class Recurrence {
  private int id;
  private int intervalDays;
  private LocalDate startDate;
  private LocalDate endDate;

  /**
   * Creates a new recurrence.
   */
  public Recurrence(int id, int intervalDays, LocalDate startDate, LocalDate endDate) {
    this.id = id;
    this.intervalDays = intervalDays;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  /**
   * Creates a new recurrence.
   */
  public Recurrence(int intervalDays, LocalDate startDate, LocalDate endDate) {
    this.intervalDays = intervalDays;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public int getId() {
    return id;
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
