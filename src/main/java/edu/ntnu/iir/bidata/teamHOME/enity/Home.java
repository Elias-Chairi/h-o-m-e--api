package edu.ntnu.iir.bidata.teamhome.enity;

/**
 * Represents a home.
 */
public class Home {
  private String id;
  private String name;

  /**
   * Creates a new home.
   */
  public Home(String id, String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * Creates a new home.
   */
  public Home(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
