package edu.ntnu.iir.bidata.teamhome.service.exception;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Exception thrown when a SQL query violates a foreign key constraint.
 */
public class DbForeignKeyViolationException extends SQLIntegrityConstraintViolationException {
  public DbForeignKeyViolationException(String message) {
    super(message);
  }
}
