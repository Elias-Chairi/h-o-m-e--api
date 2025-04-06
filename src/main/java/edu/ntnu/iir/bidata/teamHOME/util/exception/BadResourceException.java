package edu.ntnu.iir.bidata.teamhome.util.exception;

/**
 * Represents an exception that is thrown when a resource is bad.
 */
public class BadResourceException extends Exception {
  public BadResourceException(String message, Throwable cause) {
    super(message, cause);
  }
  
  public BadResourceException(String message) {
    super(message);
  }
}
