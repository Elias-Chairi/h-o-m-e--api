package edu.ntnu.iir.bidata.teamHOME.mysql;

import java.sql.SQLException;

/**
 * Exception thrown when a SQL query is searching for a non-existing entity.
 */
public class SQLEntityNotFoundException extends SQLException {
    public SQLEntityNotFoundException(String message) {
        super(message);
    }
}
