package edu.ntnu.iir.bidata.teamHOME.enity;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents a request to create a task.
 */
public class CreateTaskRequest {

    @NotBlank
    @Size(min=2, max=100)
    private String name;

    @Size(max=500)
    private String description;

    private Integer assignedTo;
    private LocalDate due;

    @NotNull
    private int createdBy;

    private boolean done;
    private Integer recurrenceID;

    public CreateTaskRequest(String name, String description, Integer assignedTo, LocalDate due, int createdBy, boolean done, Integer recurrenceID) {
        this.name = name;
        this.description = description;
        this.assignedTo = assignedTo;
        this.due = due;
        this.createdBy = createdBy;
        this.done = done;
        this.recurrenceID = recurrenceID;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Integer assignedTo) {
        this.assignedTo = assignedTo;
    }

    public LocalDate getDue() {
        return due;
    }

    public void setDue(LocalDate due) {
        this.due = due;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Integer getRecurrenceID() {
        return recurrenceID;
    }

    public void setRecurrenceID(Integer recurrenceID) {
        this.recurrenceID = recurrenceID;
    }
}
