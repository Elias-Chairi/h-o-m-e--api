package edu.ntnu.iir.bidata.teamhome.response.attributesobject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Represents the attributes of a user resource.
 */
public class TasksAttributes {
  @NotBlank
  @Size(min = 2, max = 100)
  private String name;

  @Size(max = 500)
  private String description;

  private LocalDate due;
  private LocalDate created;
  private Boolean done;

  /**
   * Creates a new task attributes.
   */
  public TasksAttributes(String name, String description, LocalDate due, LocalDate created,
      Boolean done) {
    this.name = name;
    this.description = description;
    this.due = due;
    this.created = created;
    this.done = done;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public LocalDate getDue() {
    return due;
  }

  public LocalDate getCreated() {
    return created;
  }

  public Boolean isDone() {
    return done;
  }
}
