package edu.ntnu.iir.bidata.teamHOME.mysql;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Exception thrown when a SQL query violates a foreign key constraint.
 */
public class SQLForeignKeyViolationException extends SQLIntegrityConstraintViolationException {
    public SQLForeignKeyViolationException(String message) {
        super(message);
    }
}
