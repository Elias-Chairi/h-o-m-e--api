package edu.ntnu.iir.bidata.teamHOME.enity;

import java.time.LocalDate;

/**
 * Represents a recurrence for a task.
 */
public class Recurrence {
    private int recurrenceID;
    private int intervalDays;
    private LocalDate startDate;
    private LocalDate endDate;

    public Recurrence(int recurrenceID, int intervalDays, LocalDate startDate, LocalDate endDate) {
        this.recurrenceID = recurrenceID;
        this.intervalDays = intervalDays;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getRecurrenceID() {
        return recurrenceID;
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
