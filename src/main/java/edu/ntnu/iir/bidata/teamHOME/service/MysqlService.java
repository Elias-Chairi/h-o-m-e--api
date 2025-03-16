package edu.ntnu.iir.bidata.teamHOME.service;

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
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ntnu.iir.bidata.teamHOME.controller.HomeController;
import edu.ntnu.iir.bidata.teamHOME.enity.CreateTaskRequest;
import edu.ntnu.iir.bidata.teamHOME.enity.Home;
import edu.ntnu.iir.bidata.teamHOME.enity.Resident;
import edu.ntnu.iir.bidata.teamHOME.enity.Task;
import edu.ntnu.iir.bidata.teamHOME.service.exception.SQLEntityNotFoundException;

/**
 * Controller for interacting with the MySQL database. Uses the Singleton
 * pattern.
 */
public class MysqlService {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
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
     * @param homeID The ID of the home to get the name of.
     * @return The name of the home.
     * @throws SQLEntityNotFoundException if the home is not found.
     * @throws SQLException               if an error occurs while getting the home
     *                                    name.
     */
    public Home getHome(String homeID) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.connectionString)) {
            String query = "SELECT home_id, name FROM Home WHERE home_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, homeID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return new Home(resultSet.getString(1), resultSet.getString(2));
                    }
                    throw new SQLEntityNotFoundException("Home not found");
                }
            }
        }
    }

    // /**
    //  * Inserts a new home into the database.
    //  * 
    //  * @param homeName The name of the home to create.
    //  * @return The ID of the created home.
    //  * @throws SQLException if an error occurs while creating the home.
    //  */
    // public Home createHome(String homeName) throws SQLException {
    //     try (Connection connection = DriverManager.getConnection(this.connectionString)) {
    //         String generatedHomeID = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    //         String homeQuery = "INSERT INTO Home (home_id, name) VALUES (?, ?)";
    //         try (PreparedStatement statement = connection.prepareStatement(homeQuery)) {
    //             statement.setString(1, generatedHomeID);
    //             statement.setString(2, homeName);
    //             statement.executeUpdate();
    //         }
    //         return new Home(generatedHomeID, homeName);
    //     }
    // }

    // /**
    //  * Inserts a new resident into the database.
    //  *
    //  * @param homeID   The ID of the home the resident belongs to.
    //  * @param userName The name of the resident to create.
    //  * @return The ID of the created resident.
    //  * @throws SQLForeignKeyViolationException if the home is not found.
    //  * @throws SQLException                    if an error occurs while creating the
    //  *                                         resident.
    //  */
    // public User createResident(String homeID, String userName) throws SQLException {
    //     try (Connection connection = DriverManager.getConnection(this.connectionString)) {
    //         String userQuery = "INSERT INTO Resident (home_id, name) VALUES (?, ?)";
    //         try (PreparedStatement statement = connection.prepareStatement(userQuery,
    //                 Statement.RETURN_GENERATED_KEYS)) {
    //             statement.setString(1, homeID);
    //             statement.setString(2, userName);
    //             statement.executeUpdate();

    //             try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
    //                 if (generatedKeys.next()) {
    //                     int userID = generatedKeys.getInt(1);
    //                     return new User(userName, userID);
    //                 } else {
    //                     throw new SQLException("Failed to insert resident.");
    //                 }
    //             }
    //         } catch (SQLIntegrityConstraintViolationException e) {
    //             throw new SQLForeignKeyViolationException("Home not found");
    //         }
    //     }
    // }

    /**
     * Gets all residents in a home.
     *
     * @param homeID
     * @return A list of residents in the home.
     * @throws SQLEntityNotFoundException if no residents are found in the home.
     * @throws SQLException               if an error occurs while getting the
     *                                    residents.
     */
    public List<Resident> getResidents(String homeID) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.connectionString)) {
            String query = "SELECT resident_id, name, home_id FROM Resident WHERE home_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, homeID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<Resident> residents = new ArrayList<>();
                    while (resultSet.next()) {
                        residents.add(new Resident(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
                    }
                    if (residents.isEmpty()) {
                        throw new SQLEntityNotFoundException("No residents found in home");
                    }
                    return residents;
                }
            }
        }
    }

    /**
     * Gets all tasks in a home.
     *
     * @param homeID The ID of the home to get tasks from.
     * @return A list of users in the home.
     */
    public List<Task> getTasks(String homeID)
            throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.connectionString)) {
            String query = "SELECT task_id, name, description, assignedTo, due, created, createdBy, done, recurrence_id FROM Task WHERE createdBy IN (SELECT resident_id FROM Resident WHERE home_id = ?);";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, homeID);
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
                        Integer recurrenceID = resultSet.getInt(9);
                        if (resultSet.wasNull()) {
                            recurrenceID = null;
                        }
                        tasks.add(new Task(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                                assignedTo, due, resultSet.getDate(6).toLocalDate(),
                                resultSet.getInt(7), resultSet.getBoolean(8), recurrenceID));
                    }
                    return tasks;
                }
            }
        }
    }

    // /**
    //  * Creates a task in the database.
    //  *
    //  * @param req
    //  * @throws SQLIntegrityConstraintViolationException if the assignedTo, createdBy
    //  *                                                  or recurrence_id is not
    //  *                                                  found.
    //  * @throws SQLException
    //  */
    // public Task createTask(CreateTaskRequest req) throws SQLException {
    //     try (Connection connection = DriverManager.getConnection(this.connectionString)) {
    //         String taskQuery = "INSERT INTO Task (name, description, assignedTo, due, createdBy, done, recurrence_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    //         int taskID;
    //         try (PreparedStatement statement = connection.prepareStatement(taskQuery,
    //                 Statement.RETURN_GENERATED_KEYS)) {
    //             statement.setString(1, req.getName());
    //             statement.setString(2, req.getDescription());
    //             if (req.getAssignedTo() == null) {
    //                 statement.setNull(3, java.sql.Types.INTEGER);
    //             } else {
    //                 statement.setInt(3, req.getAssignedTo());
    //             }
    //             if (req.getDue() == null) {
    //                 statement.setNull(4, java.sql.Types.DATE);
    //             } else {
    //                 statement.setDate(4, java.sql.Date.valueOf(req.getDue()));
    //             }
    //             statement.setInt(5, req.getCreatedBy());
    //             statement.setBoolean(6, req.isDone());
    //             if (req.getRecurrenceID() == null) {
    //                 statement.setNull(7, java.sql.Types.INTEGER);
    //             } else {
    //                 statement.setInt(7, req.getRecurrenceID());
    //             }
    //             statement.executeUpdate();

    //             try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
    //                 if (generatedKeys.next()) {
    //                     taskID = generatedKeys.getInt(1);
    //                 } else {
    //                     throw new SQLException("Failed to insert task.");
    //                 }
    //             }
    //         } catch (SQLIntegrityConstraintViolationException e) {
    //             throw new SQLForeignKeyViolationException("assignedTo, createdBy or recurrence_id not found");
    //         }
    //         String taskQuery2 = "SELECT name, task_id, description, assignedTo, due, created, createdBy, done, recurrence_id FROM Task WHERE task_id = ?";
    //         try (PreparedStatement statement = connection.prepareStatement(taskQuery2)) {
    //             statement.setInt(1, taskID);
    //             try (ResultSet resultSet = statement.executeQuery()) {
    //                 if (resultSet.next()) {
    //                     Date sqldue = resultSet.getDate(5);
    //                     LocalDate due = null;
    //                     if (sqldue != null) {
    //                         due = sqldue.toLocalDate();
    //                     }
    //                     Integer assignedTo = resultSet.getInt(4);
    //                     if (resultSet.wasNull()) {
    //                         assignedTo = null;
    //                     }
    //                     Integer recurrenceID = resultSet.getInt(9);
    //                     if (resultSet.wasNull()) {
    //                         recurrenceID = null;
    //                     }
    //                     return new Task(resultSet.getString(1), resultSet.getInt(2), resultSet.getString(3),
    //                             assignedTo, due, resultSet.getDate(6).toLocalDate(),
    //                             resultSet.getInt(7), resultSet.getBoolean(8), recurrenceID);
    //                 }
    //                 throw new SQLEntityNotFoundException("Task not found after creation");
    //             }
    //         }
    //     }
    // }

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
