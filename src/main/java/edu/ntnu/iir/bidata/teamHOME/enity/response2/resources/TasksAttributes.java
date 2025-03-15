package edu.ntnu.iir.bidata.teamHOME.enity.response2.resources;

import java.time.LocalDate;

/**
 * Represents task attributes.
 */
public class TasksAttributes {
    private String name;
    private String description;
    private Integer assignedTo;
    LocalDate due;
    LocalDate created;
    private int createdBy;
    private boolean done;
    private Integer recurrenceID;

    public TasksAttributes(String name, String description, Integer assignedTo, LocalDate due, LocalDate created, int createdBy, boolean done, Integer recurrenceID) {
        this.name = name;
        this.description = description;
        this.assignedTo = assignedTo;
        this.due = due;
        this.created = created;
        this.createdBy = createdBy;
        this.done = done;
        this.recurrenceID = recurrenceID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getAssignedTo() {
        return assignedTo;
    }

    public LocalDate getDue() {
        return due;
    }

    public LocalDate getCreated() {
        return created;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public boolean isDone() {
        return done;
    }

    public Integer getRecurrenceID() {
        return recurrenceID;
    }
}