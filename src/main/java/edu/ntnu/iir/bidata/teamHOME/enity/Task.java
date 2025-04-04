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
  private Integer createdBy;
  private Boolean done;
  private Integer recurrenceId;

  /**
   * Creates a new task.
   */
  public Task(int id, String name, String description, Integer assignedTo, LocalDate due,
      LocalDate created, Integer createdBy, Boolean done, Integer recurrenceId) {
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
      LocalDate created, Integer createdBy, Boolean done, Integer recurrenceId) {
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
   * Task builder.
   */
  public static class Builder {
    private String name;
    private String description;
    private Integer assignedTo;
    private LocalDate due;
    private LocalDate created;
    private Integer createdBy;
    private Boolean done;
    private Integer recurrenceId;

    /**
     * Creates a new task builder.
     */
    public Builder(String name, String description, LocalDate due, Integer createdBy,
        Boolean done) {
      this.name = name;
      this.description = description;
      this.due = due;
      this.createdBy = createdBy;
      this.done = done;
    }

    public Builder assignedTo(Integer assignedTo) {
      this.assignedTo = assignedTo;
      return this;
    }

    public Builder recurrenceId(Integer recurrenceId) {
      this.recurrenceId = recurrenceId;
      return this;
    }

    public Task build() {
      return new Task(name, description, assignedTo, due, created, createdBy, done, recurrenceId);
    }
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

  public Integer getCreatedBy() {
    return createdBy;
  }

  public Boolean isDone() {
    return done;
  }

  public Integer getRecurrenceId() {
    return recurrenceId;
  }
}