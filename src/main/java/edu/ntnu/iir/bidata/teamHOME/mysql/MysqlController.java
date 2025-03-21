package edu.ntnu.iir.bidata.teamHOME.mysql;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ntnu.iir.bidata.teamHOME.enity.Task;
import edu.ntnu.iir.bidata.teamHOME.enity.User;
import edu.ntnu.iir.bidata.teamHOME.rest.HomeController;

/**
 * Controller for interacting with the MySQL database. Uses the Singleton
 * pattern.
 */
public class MysqlController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private static MysqlController instance = null;
    private String connectionString;

    private MysqlController() {
        // private constructor
        this.connectionString = System.getenv("AZURE_MYSQL_CONNECTIONSTRING");
    }

    private static List<String> getQueriesFromResourceSchema(String filename) throws IOException {
        InputStream in = MysqlController.class.getResourceAsStream(filename);
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
     * @throws SQLException if an error occurs while getting the home name.
     */
    public String getHomeName(String homeID) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.connectionString)) {
            String query = "SELECT name FROM Home WHERE home_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, homeID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString(1);
                    }
                    throw new SQLEntityNotFoundException("Home not found");
                }
            }
        }
    }

    /**
     * Inserts a new home into the database.
     * 
     * @param homeName The name of the home to create.
     * @return The ID of the created home.
     * @throws SQLException if an error occurs while creating the home.
     */
    public String createHome(String homeName) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.connectionString)) {
            String generatedHomeID = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
            String homeQuery = "INSERT INTO Home (home_id, name) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(homeQuery)) {
                statement.setString(1, generatedHomeID);
                statement.setString(2, homeName);
                statement.executeUpdate();
            }
            return generatedHomeID;
        }
    }

    /**
     * Inserts a new resident into the database.
     *
     * @param homeID   The ID of the home the resident belongs to.
     * @param userName The name of the resident to create.
     * @return The ID of the created resident.
     * @throws SQLForeignKeyViolationException if the home is not found.
     * @throws SQLException if an error occurs while creating the resident.
     */
    public int createResident(String homeID, String userName) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.connectionString)) {
            String userQuery = "INSERT INTO Resident (home_id, name) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(userQuery,
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, homeID);
                statement.setString(2, userName);
                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Failed to insert resident.");
                    }
                }
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new SQLForeignKeyViolationException("Home not found");
            }
        }
    }

    /**
     * Gets all users in a home.
     *
     * @param homeID
     * @return A list of users in the home.
     * @throws SQLEntityNotFoundException if no users are found in the home.
     * @throws SQLException if an error occurs while getting the users.
     */
    public List<User> getUsers(String homeID) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.connectionString)) {
            String query = "SELECT resident_id, name FROM Resident WHERE home_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, homeID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<User> users = new ArrayList<>();
                    while (resultSet.next()) {
                        users.add(new User(resultSet.getInt(1), resultSet.getString(2)));
                    }
                    if (users.isEmpty()) {
                        throw new SQLEntityNotFoundException("No users found in home");
                    }
                    return users;
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
    public List<Task> getTasks(String homeID) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.connectionString)) {
            String query = "SELECT task_id, name, description, assignedTo, due, created, createdBy, done, recurrence_id FROM Task WHERE createdBy IN (SELECT resident_id FROM Resident WHERE home_id = ?);";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, homeID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<Task> tasks = List.of();
                    while (resultSet.next()) {
                        tasks.add(new Task(resultSet.getInt(1),
                                resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4),
                                resultSet.getDate(5).toLocalDate(), resultSet.getDate(6).toLocalDate(),
                                resultSet.getInt(7), resultSet.getBoolean(8), resultSet.getInt(9)));
                    }
                    return tasks;
                }
            }
        }
    }

    /**
     * Returns the instance of the MysqlController.
     *
     * @return the instance of the MysqlController
     */
    public static MysqlController getInstance() {
        if (MysqlController.instance == null) {
            MysqlController.instance = new MysqlController();
        }
        return MysqlController.instance;
    }

}
