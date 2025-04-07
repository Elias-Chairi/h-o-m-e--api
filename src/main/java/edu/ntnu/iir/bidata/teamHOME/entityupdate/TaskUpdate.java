package edu.ntnu.iir.bidata.teamhome.entityupdate;

import edu.ntnu.iir.bidata.teamhome.util.NullableOptional;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Represents a task update.
 */
public class TaskUpdate {
  private Optional<String> name;
  private NullableOptional<String> description;
  private NullableOptional<Integer> assignedTo;
  private NullableOptional<LocalDate> due;
  private Optional<Boolean> done;
  private NullableOptional<Integer> recurrenceId;

  /**
   * Creates a new task update with empty values.
   */
  public TaskUpdate() {
    this.name = Optional.empty();
    this.description = NullableOptional.empty();
    this.assignedTo = NullableOptional.empty();
    this.due = NullableOptional.empty();
    this.done = Optional.empty();
    this.recurrenceId = NullableOptional.empty();
  }

  public Optional<String> getName() {
    return name;
  }

  public void setName(String name) {
    this.name = Optional.of(name);
  }

  public NullableOptional<String> getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = NullableOptional.of(description);
  }

  public NullableOptional<Integer> getAssignedTo() {
    return assignedTo;
  }

  public void setAssignedTo(Integer residentId) {
    this.assignedTo = NullableOptional.of(residentId);
  }

  public NullableOptional<LocalDate> getDue() {
    return due;
  }

  public void setDue(LocalDate due) {
    this.due = NullableOptional.of(due);
  }

  public Optional<Boolean> isDone() {
    return done;
  }

  public void setDone(Boolean done) {
    this.done = Optional.of(done);
  }

  public NullableOptional<Integer> getRecurrenceId() {
    return recurrenceId;
  }

  public void setRecurrenceId(Integer recurrenceId) {
    this.recurrenceId = NullableOptional.of(recurrenceId);
  }

  /**
   * Checks if the task update is empty.
   *
   * @return true if the task update is empty, false otherwise
   */
  public boolean isEmpty() {
    return name.isEmpty()
        && description.isEmpty()
        && assignedTo.isEmpty()
        && due.isEmpty()
        && done.isEmpty()
        && recurrenceId.isEmpty();
  }
}