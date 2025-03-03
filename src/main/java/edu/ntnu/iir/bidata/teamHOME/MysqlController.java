package edu.ntnu.iir.bidata.teamHOME;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * Checks if a home exists in the database.
     *
     * @return true if the home exists, false otherwise
     */
    public boolean isHome(String homeID) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.connectionString)) {
            String query = "SELECT 1 AS 'isHome' FROM Home WHERE home_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, homeID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("isHome") == 1;
                    }
                }
            }
            return false;
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
