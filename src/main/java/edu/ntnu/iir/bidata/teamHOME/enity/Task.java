package edu.ntnu.iir.bidata.teamHOME.enity;

import java.time.LocalDate;

public class Task {
    private int id;
    private String name;
    private String description;
    private Integer assignedTo;
    private LocalDate due;
    private LocalDate created;
    private int createdBy;
    private boolean done;
    private Integer recurrenceID;

    public Task(int id, String name, String description, Integer assignedTo, LocalDate due, LocalDate created, int createdBy, boolean done, Integer recurrenceID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.assignedTo = assignedTo;
        this.due = due;
        this.created = created;
        this.createdBy = createdBy;
        this.done = done;
        this.recurrenceID = recurrenceID;
    }

    public int getId() {
        return id;
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