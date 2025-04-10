package edu.ntnu.iir.bidata.teamhome.response.resourceobject;

import edu.ntnu.iir.bidata.teamhome.enity.Task;
import edu.ntnu.iir.bidata.teamhome.response.attributesobject.TasksAttributes;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.RelationshipObject;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.RelationshipObjectToOne;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.ResourceIdentifierObject;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.ResourceObject;
import io.micrometer.common.lang.NonNull;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a user resource.
 */
public class TasksResource<T> extends ResourceObject<T> {
  @NonNull
  private static final String type = "tasks";

  public TasksResource(String id, T attributes,
      Map<String, RelationshipObject> relationships) {
    super(id, type, attributes, relationships);
  }

  /**
   * Creates a new task resource object from a task entity.
   */
  public static TasksResource<TasksAttributes> fromEntity(Task task) {
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

    return new TasksResource<>(Integer.toString(task.getId()),
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
