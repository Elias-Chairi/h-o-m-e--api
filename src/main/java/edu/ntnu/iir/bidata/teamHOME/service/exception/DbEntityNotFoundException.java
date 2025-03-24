package edu.ntnu.iir.bidata.teamhome.service.exception;

import java.sql.SQLException;

/**
 * Exception thrown when a SQL query is searching for a non-existing entity.
 */
public class DbEntityNotFoundException extends SQLException {
  public DbEntityNotFoundException(String message) {
    super(message);
  }
}
