package edu.ntnu.iir.bidata.teamhome.enity;

import java.time.LocalDate;

/**
 * Represents a task.
 */
public class Task {
  private int id;
  private String name;
  private String description;
  private Integer assignedTo;
  private LocalDate due;
  private LocalDate created;
  private int createdBy;
  private boolean done;
  private Integer recurrenceId;

  /**
   * Creates a new task.
   */
  public Task(int id, String name, String description, Integer assignedTo, LocalDate due,
      LocalDate created, int createdBy, boolean done, Integer recurrenceId) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.assignedTo = assignedTo;
    this.due = due;
    this.created = created;
    this.createdBy = createdBy;
    this.done = done;
    this.recurrenceId = recurrenceId;
  }

  /**
   * Creates a new task.
   */
  public Task(String name, String description, Integer assignedTo, LocalDate due,
      LocalDate created, int createdBy,
      boolean done, Integer recurrenceId) {
    this.name = name;
    this.description = description;
    this.assignedTo = assignedTo;
    this.due = due;
    this.created = created;
    this.createdBy = createdBy;
    this.done = done;
    this.recurrenceId = recurrenceId;
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

  public Integer getRecurrenceId() {
    return recurrenceId;
  }
}