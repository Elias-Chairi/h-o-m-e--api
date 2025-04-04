package edu.ntnu.iir.bidata.teamhome.response.resourceobject;

import edu.ntnu.iir.bidata.teamhome.enity.Task;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.RelationshipObject;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.RelationshipObjectToOne;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.ResourceIdentifierObject;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.ResourceObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a user resource.
 */
public class TasksResource extends ResourceObject<TasksResource.TasksAttributes> {
  private static final String type = "tasks";

  /**
   * Represents the attributes of a user resource.
   */
  public static class TasksAttributes {
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    LocalDate due;
    LocalDate created;
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

  private TasksResource(String id, TasksAttributes attributes,
      Map<String, RelationshipObject> relationships) {
    super(id, type, attributes, relationships);
  }

  /**
   * Creates a new task resource object from a task entity.
   */
  public static TasksResource fromEntity(Task task) {
    Map<String, RelationshipObject> relationships = new HashMap<>(Map.of(
        "createdBy",
        new RelationshipObjectToOne(new ResourceIdentifierObject("residents",
            Integer.toString(task.getCreatedBy())))));

    if (task.getAssignedTo() != null) {
      relationships.put("assignedTo",
          new RelationshipObjectToOne(new ResourceIdentifierObject("residents",
              Integer.toString(task.getAssignedTo()))));
    }
    if (task.getRecurrenceId() != null) {
      relationships.put("recurrence",
          new RelationshipObjectToOne(new ResourceIdentifierObject("recurrences",
              Integer.toString(task.getRecurrenceId()))));
    }

    return new TasksResource(Integer.toString(task.getId()),
        new TasksAttributes(
            task.getName(),
            task.getDescription(),
            task.getDue(),
            task.getCreated(),
            task.isDone()),
        relationships);
  }

  @Schema(example = type)
  @Override
  public String getType() {
    return type;
  }
}
