package edu.ntnu.iir.bidata.teamhome.enity;

/**
 * Represents a resident.
 */
public class Resident {
  private int id;
  private String name;
  private String homeId;

  /**
   * Creates a new resident.
   */
  public Resident(int id, String name, String homeId) {
    this.id = id;
    this.name = name;
    this.homeId = homeId;
  }

  /**
   * Creates a new resident.
   */
  public Resident(String name, String homeId) {
    this.name = name;
    this.homeId = homeId;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getHomeId() {
    return homeId;
  }
}
