package edu.ntnu.iir.bidata.teamHOME.enity;

public class Resident {
    private int id;
    private String name;
    private String homeID;

    public Resident(int id, String name, String homeID) {
        this.id = id;
        this.name = name;
        this.homeID = homeID;
    }

    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public String getHomeID() {
        return homeID;
    }
}
