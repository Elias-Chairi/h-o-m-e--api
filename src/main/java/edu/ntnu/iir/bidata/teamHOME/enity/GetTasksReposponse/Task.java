package edu.ntnu.iir.bidata.teamHOME.enity.GetTasksReposponse;

import java.time.LocalDate;

/**
 * Represents a task.
 */
public class Task {
    private int taskID;
    private String name;
    private String description;
    private int assignedTo;
    private LocalDate due;
    private LocalDate created;
    private int createdBy;
    private boolean done;
    private int recurrenceID;

    public Task(int taskID, String name, String description, int assignedTo, LocalDate due, LocalDate created, int createdBy, boolean done, int recurrenceID) {
        this.taskID = taskID;
        this.name = name;
        this.description = description;
        this.assignedTo = assignedTo;
        this.due = due;
        this.created = created;
        this.createdBy = createdBy;
        this.done = done;
        this.recurrenceID = recurrenceID;
    }

    public int getTaskID() {
        return taskID;
    }
    
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getAssignedTo() {
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

    public int getRecurrenceID() {
        return recurrenceID;
    }
}
