package edu.ntnu.iir.bidata.teamhome.response.resourceobjectattributes;

import edu.ntnu.iir.bidata.teamhome.service.MysqlService;
import edu.ntnu.iir.bidata.teamhome.util.NullableOptional;
import java.time.LocalDate;

/**
 * Represents the attributes of a task resource.
 * This class is used to update the attributes of a task.
 *
 * @see MysqlService#UPDATABLE_TASK_FIELDS
 * @see MysqlService#NULLABLE_TASK_FIELDS
 */
public class TasksUpdateAttributes {

  private NullableOptional<String> name = NullableOptional.empty();
  private NullableOptional<String> description = NullableOptional.empty();
  private NullableOptional<LocalDate> due = NullableOptional.empty();
  private NullableOptional<Boolean> done = NullableOptional.empty();


  private TasksUpdateAttributes() {    
    // private constructor to prevent instantiation (only used for deserialization with Gson)
  }

  public NullableOptional<String> getName() {
    return name;
  }

  public NullableOptional<String> getDescription() {
    return description;
  }

  public NullableOptional<LocalDate> getDue() {
    return due;
  }

  public NullableOptional<Boolean> isDone() {
    return done;
  }
}
