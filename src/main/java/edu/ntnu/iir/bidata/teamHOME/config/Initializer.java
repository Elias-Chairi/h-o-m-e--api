package edu.ntnu.iir.bidata.teamhome.config;

import edu.ntnu.iir.bidata.teamhome.service.MysqlService;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

/**
 * Responsible for initializing processes that need to be completed before the application can 
 * start.
 */
@Component
public class Initializer implements SmartLifecycle {
  private static final Logger logger = LoggerFactory.getLogger(Initializer.class);
  private volatile boolean isRunning = false;
  private final MysqlService mysqlService;

  @Autowired
  public Initializer(MysqlService mysqlService) {
    this.mysqlService = mysqlService;
  }

  /**
   * Starts the initialization process.
   */
  @Override
  public void start() {
    logger.info("Initializing application");
    // Initialize the MySQL database
    logger.info("Creating tables in MySQL database");
    try {
      mysqlService.createTables();
    } catch (SQLException e) {
      throw new RuntimeException("Failed to initialize MySQL database", e);
    }
    logger.info("Application initialized");
    isRunning = true;
  }

  /**
   * Stops the initialization process.
   */
  @Override
  public void stop() {
    isRunning = false;
  }

  /**
   * Returns whether the initialization process is running.
   * Used by Spring to determine if the application is ready to start.
   *
   * @return true if the initialization process is running, false otherwise
   */
  @Override
  public boolean isRunning() {
    return isRunning;
  }
}
