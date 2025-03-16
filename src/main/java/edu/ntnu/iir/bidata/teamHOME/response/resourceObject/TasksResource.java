package edu.ntnu.iir.bidata.teamHOME.response.resourceObject;

import java.time.LocalDate;
import java.util.Map;
import edu.ntnu.iir.bidata.teamHOME.enity.Task;
import edu.ntnu.iir.bidata.teamHOME.response.jsonapi.RelationshipObjectToMany;
import edu.ntnu.iir.bidata.teamHOME.response.jsonapi.ResourceObject;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents a user resource.
 */
public class TasksResource extends ResourceObject<TasksResource.TasksAttributes> {
    private static final String type = "tasks";

    public static class TasksAttributes {
        private String name;
        private String description;
        private Integer assignedTo;
        LocalDate due;
        LocalDate created;
        private int createdBy;
        private boolean done;
        private Integer recurrenceID;

        public TasksAttributes(String name, String description, Integer assignedTo, LocalDate due, LocalDate created,
                int createdBy, boolean done, Integer recurrenceID) {
            this.name = name;
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

    private TasksResource(String id, TasksAttributes attributes, Map<String, RelationshipObjectToMany> relationships) {
        super(id, type, attributes, relationships);
    }

    public TasksResource(Task task, Map<String, RelationshipObjectToMany> relationships) {
        this(Integer.toString(task.getId()),
                new TasksAttributes(task.getName(), task.getDescription(), task.getAssignedTo(),
                        task.getDue(), task.getCreated(), task.getCreatedBy(), task.isDone(),
                        task.getRecurrenceID()),
                relationships);
    }

    @Schema(example = type)
    @Override
    public String getType() {
        return type;
    }
}
