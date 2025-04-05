package edu.ntnu.iir.bidata.teamhome.service;

import edu.ntnu.iir.bidata.teamhome.controller.HomesController;
import edu.ntnu.iir.bidata.teamhome.enity.Home;
import edu.ntnu.iir.bidata.teamhome.enity.Recurrence;
import edu.ntnu.iir.bidata.teamhome.enity.Resident;
import edu.ntnu.iir.bidata.teamhome.enity.Task;
import edu.ntnu.iir.bidata.teamhome.service.exception.DbEntityNotFoundException;
import edu.ntnu.iir.bidata.teamhome.service.exception.DbForeignKeyViolationException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for interacting with the MySQL database. Uses the Singleton
 * pattern.
 */
public class MysqlService {

  private static final Logger logger = LoggerFactory.getLogger(HomesController.class);
  private static MysqlService instance = null;
  private String connectionString;

  private MysqlService() {
    // private constructor
    this.connectionString = System.getenv("AZURE_MYSQL_CONNECTIONSTRING");
  }

  private static List<String> getQueriesFromResourceSchema(String filename) throws IOException {
    InputStream in = MysqlService.class.getResourceAsStream(filename);
    if (in == null) {
      throw new FileNotFoundException("Cannot find sql schema");
    }
    try (BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(in))) {
      StringBuilder text = new StringBuilder();
      String line = null;
      while ((line = reader.readLine()) != null) {
        text.append(line + System.lineSeparator());
      }

      List<String> queries = Stream.of(text.toString().split(";"))
          .filter(q -> !q.isBlank())
          .map(q -> q.trim() + ";")
          .toList();

      return queries;
    }

  }

  /**
   * Creates the tables in the MySQL database.
   * Executes the queries "resources/schema.sql".
   */
  public void createTables() throws SQLException {
    try (Connection connection = DriverManager.getConnection(this.connectionString)) {
      logger.debug("Creating tables in MySQL database");

      List<String> queries;
      try {
        queries = getQueriesFromResourceSchema("/schema.sql");
      } catch (IOException e) {
        e.printStackTrace();
        return;
      }

      logger.debug("Creating tables in MySQL database");
      Statement statement = connection.createStatement();

      logger.debug("Executing queries");
      for (String query : queries) {
        logger.debug("Executing query: " + query);
        statement.execute(query);
      }
    }
  }

  /**
   * Gets the home name from the database.
   *
   * @param homeId The ID of the home to get the name of.
   * @return The name of the home.
   * @throws DbEntityNotFoundException if the home is not found.
   * @throws SQLException               if an error occurs while getting the home
   *                                    name.
   */
  public Home getHome(String homeId) throws SQLException {
    try (Connection connection = DriverManager.getConnection(this.connectionString)) {
      String query = "SELECT home_id, name FROM Home WHERE home_id = ?";
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, homeId);
        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            return new Home(resultSet.getString(1), resultSet.getString(2));
          }
          throw new DbEntityNotFoundException("Home not found");
        }
      }
    }
  }

  /**
   * Inserts a new home into the database.
   *
   * @param home The home to create.
   * @return The ID of the created home.
   * @throws SQLException if an error occurs while creating the home.
   */
  public Home createHome(Home home) throws SQLException {
    try (Connection connection = DriverManager.getConnection(this.connectionString)) {
      String generatedHomeId = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
      String homeQuery = "INSERT INTO Home (home_id, name) VALUES (?, ?)";
      try (PreparedStatement statement = connection.prepareStatement(homeQuery)) {
        statement.setString(1, generatedHomeId);
        statement.setString(2, home.getName());
        statement.executeUpdate();
      }
      return new Home(generatedHomeId, home.getName());
    }
  }

  /**
   * Inserts a new resident into the database.
   *
   * @param resident The resident to create.
   * @return The ID of the created resident.
   * @throws DbForeignKeyViolationException if the home is not found.
   * @throws SQLException                    if an error occurs while creating the
   *                                         resident.
   */
  public Resident createResident(Resident resident) throws SQLException {
    try (Connection connection = DriverManager.getConnection(this.connectionString)) {
      String userQuery = "INSERT INTO Resident (home_id, name) VALUES (?, ?)";
      try (PreparedStatement statement = connection.prepareStatement(userQuery,
          Statement.RETURN_GENERATED_KEYS)) {
        statement.setString(1, resident.getHomeId());
        statement.setString(2, resident.getName());
        statement.executeUpdate();

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
          if (generatedKeys.next()) {
            int userId = generatedKeys.getInt(1);
            return new Resident(userId, resident.getName(), resident.getHomeId());
          } else {
            throw new SQLException("Failed to insert resident.");
          }
        }
      } catch (SQLIntegrityConstraintViolationException e) {
        throw new DbForeignKeyViolationException("Home not found");
      }
    }
  }

  /**
   * Gets all residents in a home.
   *
   * @param homeId The ID of the home to get residents from.
   * @return A list of residents in the home.
   * @throws SQLException               if an error occurs while getting the
   *                                    residents.
   */
  public List<Resident> getResidents(String homeId) throws SQLException {
    try (Connection connection = DriverManager.getConnection(this.connectionString)) {
      String query = "SELECT resident_id, name, home_id FROM Resident WHERE home_id = ?";
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, homeId);
        try (ResultSet resultSet = statement.executeQuery()) {
          List<Resident> residents = new ArrayList<>();
          while (resultSet.next()) {
            residents.add(
                new Resident(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
          }
          return residents;
        }
      }
    }
  }

  /**
   * Gets all tasks in a home.
   *
   * @param homeId The ID of the home to get tasks from.
   * @return A list of users in the home.
   */
  public List<Task> getTasks(String homeId)
      throws SQLException {
    try (Connection connection = DriverManager.getConnection(this.connectionString)) {
      String query = "SELECT task_id, name, description, assignedTo, due, created, createdBy, "
          + "done, recurrence_id FROM Task WHERE createdBy IN "
          + "(SELECT resident_id FROM Resident WHERE home_id = ?);";
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, homeId);
        try (ResultSet resultSet = statement.executeQuery()) {
          List<Task> tasks = new ArrayList<>();
          while (resultSet.next()) {
            Date sqldue = resultSet.getDate(5);
            LocalDate due = null;
            if (sqldue != null) {
              due = sqldue.toLocalDate();
            }
            Integer assignedTo = resultSet.getInt(4);
            if (resultSet.wasNull()) {
              assignedTo = null;
            }
            Integer recurrenceId = resultSet.getInt(9);
            if (resultSet.wasNull()) {
              recurrenceId = null;
            }
            tasks.add(new Task(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                assignedTo,
                due,
                resultSet.getDate(6).toLocalDate(),
                resultSet.getInt(7),
                resultSet.getBoolean(8),
                recurrenceId));
          }
          return tasks;
        }
      }
    }
  }

  /**
   * Creates a task in the database.
   *
   * @param task The task to create.
   * @return The created task.
   * @throws DbForeignKeyViolationException if the assignedTo, createdBy
   *                                                  or recurrence_id is not
   *                                                  found.
   * @throws SQLException if an error occurs while creating the task.
   */
  public Task createTask(Task task) throws SQLException {
    try (Connection connection = DriverManager.getConnection(this.connectionString)) {

      List<String> columns = new ArrayList<>(
          List.of("name", "description", "assignedTo", "due", "createdBy", "recurrence_id"));
      if (task.isDone() != null) {
        columns.add("done");
      }
      List<String> values = Collections.nCopies(columns.size(), "?");
      final String taskQuery = String.format("INSERT INTO Task (%s) VALUES (%s)",
          String.join(", ", columns), String.join(", ", values));

      int taskId;
      try (PreparedStatement statement = connection.prepareStatement(taskQuery,
          Statement.RETURN_GENERATED_KEYS)) {
        statement.setString(1, task.getName());
        statement.setString(2, task.getDescription());
        if (task.getAssignedTo() == null) {
          statement.setNull(3, java.sql.Types.INTEGER);
        } else {
          statement.setInt(3, task.getAssignedTo());
        }
        if (task.getDue() == null) {
          statement.setNull(4, java.sql.Types.DATE);
        } else {
          statement.setDate(4, java.sql.Date.valueOf(task.getDue()));
        }
        statement.setInt(5, task.getCreatedBy());
        if (task.getRecurrenceId() == null) {
          statement.setNull(6, java.sql.Types.INTEGER);
        } else {
          statement.setInt(6, task.getRecurrenceId());
        }
        if (task.isDone() != null) {
          statement.setBoolean(7, task.isDone());
        }
        statement.executeUpdate();

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
          if (generatedKeys.next()) {
            taskId = generatedKeys.getInt(1);
          } else {
            throw new SQLException("Failed to insert task.");
          }
        }
      } catch (SQLIntegrityConstraintViolationException e) {
        throw new DbForeignKeyViolationException(
            "assignedTo, createdBy or recurrence_id not found");
      }
      final String taskQuery2 = "SELECT "
          + "task_id, name, description, assignedTo, due, created, createdBy, done, recurrence_id "
          + "FROM Task WHERE task_id = ?";
      // need to get the task again to get the created date
      try (PreparedStatement statement = connection.prepareStatement(taskQuery2)) {
        statement.setInt(1, taskId);
        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            Date sqldue = resultSet.getDate(5);
            LocalDate due = null;
            if (sqldue != null) {
              due = sqldue.toLocalDate();
            }
            Integer assignedTo = resultSet.getInt(4);
            if (resultSet.wasNull()) {
              assignedTo = null;
            }
            Integer recurrenceId = resultSet.getInt(9);
            if (resultSet.wasNull()) {
              recurrenceId = null;
            }
            return new Task(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                assignedTo,
                due,
                resultSet.getDate(6).toLocalDate(),
                resultSet.getInt(7),
                resultSet.getBoolean(8),
                recurrenceId);
          }
          throw new DbEntityNotFoundException("Task not found after creation");
        }
      }
    }
  }

  /**
   * Deletes a recurrence from the database.
   *
   * @param recurrenceId The ID of the recurrence to delete.
   * @return true if the recurrence was deleted, false otherwise.
   * @throws SQLException               if an error occurs while getting the task.
   */
  public boolean deleteRecurrence(int recurrenceId) throws SQLException {
    try (Connection connection = DriverManager.getConnection(this.connectionString)) {
      final String query = "DELETE FROM Recurrence WHERE recurrence_id = ?";
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, recurrenceId);
        int effectedRows = statement.executeUpdate();
        return effectedRows > 0;
      }
    }
  }

  /**
   * Gets the recurrences from the database.
   *
   * @param ids The IDs of the recurrences to get.
   * @return A list of recurrences.
   * @throws SQLException if an error occurs while getting the recurrences.
   */
  public List<Recurrence> getRecurrences(List<Integer> ids) throws SQLException {
    if (ids == null || ids.isEmpty()) {
      return List.of();
    }
    try (Connection connection = DriverManager.getConnection(this.connectionString)) {
      final String query = String.format("SELECT recurrence_id, interval_days, start_date, "
          + "end_date FROM Recurrence WHERE recurrence_id IN (%s)",
          String.join(", ", ids.stream().map(id -> Integer.toString(id)).toList()));

      System.out.println("QUERY");
      System.out.println(query);

      try (PreparedStatement statement = connection.prepareStatement(query)) {
        try (ResultSet resultSet = statement.executeQuery()) {
          List<Recurrence> recurrences = new ArrayList<>();
          while (resultSet.next()) {
            recurrences.add(new Recurrence(
                resultSet.getInt(1),
                resultSet.getInt(2),
                resultSet.getDate(3).toLocalDate(),
                resultSet.getDate(4) == null ? null : resultSet.getDate(4).toLocalDate()));
          }
          return recurrences;
        }
      }

      // final String query = "DELETE FROM Recurrence WHERE recurrence_id = ?";
      // try (PreparedStatement statement = connection.prepareStatement(query)) {
      //   statement.setInt(1, recurrenceId);
      //   int effectedRows = statement.executeUpdate();
      //   return effectedRows > 0;
      // }
    }
  }

  /**
   * Deletes the recurrence of a task.
   * The recurrence is deleted from the database.
   * The task is updated to set the recurrence_id to null.
   *
   * @param taskId The ID of the task to delete the recurrence for.
   * @return true if the recurrence was deleted, false otherwise.
   * @throws DbEntityNotFoundException if the task is not found.
   * @throws SQLException               if an error occurs while deleting the
   *                                    recurrence.
   */
  public boolean deleteTaskRecurrence(int taskId) throws SQLException {
    try (Connection connection = DriverManager.getConnection(this.connectionString)) {
      final String query = "SELECT recurrence_id FROM Task WHERE task_id = ?";
      int recurrenceId;
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, taskId);
        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            recurrenceId = resultSet.getInt(1);
            if (resultSet.wasNull()) {
              return false; // no recurrence
            }
          } else {
            throw new DbEntityNotFoundException("Task not found");
          }
        }
      }
      return deleteRecurrence(recurrenceId);
    }
  }

  /**
   * Creates a recurrence in the database.
   * The recurrence is associated with a task.
   * The task is updated with the recurrence_id.
   *
   * @param recurrence The recurrence to create.
   * @return The created recurrence.
   * @throws DbEntityNotFoundException if the task is not found.
   * @throws SQLException if an error occurs while creating the recurrence.
   */
  public Recurrence createRecurrence(Recurrence recurrence, int taskId) throws SQLException {
    try (Connection connection = DriverManager.getConnection(this.connectionString)) {
      final String query = "INSERT INTO Recurrence "
          + "(interval_days, start_date, end_date) VALUES (?, ?, ?)";
      int recurrenceId;
      try (PreparedStatement statement = connection.prepareStatement(query,
          Statement.RETURN_GENERATED_KEYS)) {
        statement.setInt(1, recurrence.getIntervalDays());
        statement.setDate(2, Date.valueOf(recurrence.getStartDate()));
        if (recurrence.getEndDate() == null) {
          statement.setNull(3, java.sql.Types.DATE);
        } else {
          statement.setDate(3, Date.valueOf(recurrence.getEndDate()));
        }
        statement.executeUpdate();

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
          if (generatedKeys.next()) {
            recurrenceId = generatedKeys.getInt(1);
          } else {
            throw new SQLException("Failed to insert task.");
          }
        }
      }

      final String query2 = "UPDATE Task SET recurrence_id = ? WHERE task_id = ?";
      try (PreparedStatement statement = connection.prepareStatement(query2)) {
        statement.setInt(1, recurrenceId);
        statement.setInt(2, taskId);
        int effectedRows = statement.executeUpdate();
        if (effectedRows == 0) {
          deleteRecurrence(recurrenceId); // cleanup
          throw new DbEntityNotFoundException("Task not found");
        }
      } catch (SQLIntegrityConstraintViolationException e) {
        throw new DbForeignKeyViolationException("Task not found");
      }

      return new Recurrence(
          recurrenceId,
          recurrence.getIntervalDays(),
          recurrence.getStartDate(),
          recurrence.getEndDate());
    }
  }

  public static final Map<String, Class<?>> UPDATABLE_TASK_FIELDS = Map.of(
      "name", String.class,
      "description", String.class,
      "assignedTo", Integer.class,
      "due", LocalDate.class,
      "done", Boolean.class);

  public static final Map<String, Class<?>> NULLABLE_TASK_FIELDS = Map.of(
      "description", String.class,
      "assignedTo", Integer.class,
      "due", LocalDate.class,
      "recurrence_id", Integer.class);

  /**
   * Updates a task in the database.
   * The task is identified by the taskId.
   * The updates are provided as a map where the key is the field to update and
   * the value is the new value. Task fields that can be updated are defined in 
   * {@link MysqlService#UPDATABLE_TASK_FIELDS} and nullable fields are defined in
   * {@link MysqlService#NULLABLE_TASK_FIELDS}.
   *
   * @param updates The updates to apply to the task.
   * @param taskId The ID of the task to update.
   * @throws IllegalArgumentException if the updates are invalid.
   * @throws DbEntityNotFoundException if the task is not found.
   * @throws SQLException if an error occurs while updating the task.
   */
  public void updateTask(Map<String, Object> updates, int taskId) throws SQLException {
    try (Connection connection = DriverManager.getConnection(this.connectionString)) {
      if (updates == null || updates.isEmpty()) {
        throw new IllegalArgumentException("No updates provided");
      }
      updates.forEach((key, value) -> {
        if (value == null) {
          if (!NULLABLE_TASK_FIELDS.containsKey(key)) {
            throw new IllegalArgumentException("Field " + key + " cannot be null");
          }
        } else if (!UPDATABLE_TASK_FIELDS.containsKey(key)) {
          throw new IllegalArgumentException("Invalid field: " + key);
        } else if (!UPDATABLE_TASK_FIELDS.get(key).isInstance(value)) {
          throw new IllegalArgumentException("Invalid type for field " + key);
        }
      });

      StringBuilder query = new StringBuilder("UPDATE Task SET ");
      for (Entry<String, Object> update : updates.entrySet()) {
        query.append(update.getKey()).append(" = ?, ");
      }

      query.delete(query.length() - 2, query.length()); // remove last comma
      query.append(" WHERE task_id = ?");

      try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
        int i = 1;
        for (Entry<String, Object> entry : updates.entrySet()) {
          if (entry.getValue() == null) {
            if (NULLABLE_TASK_FIELDS.get(entry.getKey()) == Integer.class) {
              statement.setNull(i, java.sql.Types.INTEGER);
            } else if (NULLABLE_TASK_FIELDS.get(entry.getKey()) == String.class) {
              statement.setNull(i, java.sql.Types.LONGNVARCHAR);
            } else if (NULLABLE_TASK_FIELDS.get(entry.getKey()) == LocalDate.class) {
              statement.setNull(i, java.sql.Types.DATE);
            } else {
              throw new IllegalArgumentException("failed to set null value in DB for field "
                  + entry.getKey());
            }
          } else if (entry.getValue() instanceof String) {
            statement.setString(i, (String) entry.getValue());
          } else if (entry.getValue() instanceof Integer) {
            statement.setInt(i, (Integer) entry.getValue());
          } else if (entry.getValue() instanceof LocalDate) {
            statement.setDate(i, Date.valueOf((LocalDate) entry.getValue()));
          } else if (entry.getValue() instanceof Boolean) {
            statement.setBoolean(i, (Boolean) entry.getValue());
          }
          i++;
        }
        statement.setInt(i, taskId);
        int effectedRows = statement.executeUpdate();
        if (effectedRows == 0) {
          throw new DbEntityNotFoundException("Task not found");
        }
      }
    }
  }

  /**
   * Returns the instance of the MysqlController.
   *
   * @return the instance of the MysqlController
   */
  public static MysqlService getInstance() {
    if (MysqlService.instance == null) {
      MysqlService.instance = new MysqlService();
    }
    return MysqlService.instance;
  }

}
