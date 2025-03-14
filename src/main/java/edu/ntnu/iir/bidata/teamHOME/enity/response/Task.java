package edu.ntnu.iir.bidata.teamHOME.enity.response;

import java.time.LocalDate;

public class Task {
    private String name;
    private int id;
    private String description;
    private Integer assignedTo;
    private LocalDate due;
    private LocalDate created;
    private int createdBy;
    private boolean done;
    private Integer recurrenceID;

    public Task(String name, int id, String description, Integer assignedTo, LocalDate due, LocalDate created, int createdBy, boolean done, Integer recurrenceID) {
        this.name = name;
        this.id = id;
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

    public int getId() {
        return id;
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
