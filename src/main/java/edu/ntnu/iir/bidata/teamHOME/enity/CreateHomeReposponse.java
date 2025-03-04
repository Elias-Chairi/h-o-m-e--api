package edu.ntnu.iir.bidata.teamHOME.enity;

/**
 * Response object for creating a home.
 * Contains the homeID for the created home and the userID for the user that created the home.
 */
public class CreateHomeReposponse {
    private String homeID;
    private int userID;

    public CreateHomeReposponse(String homeID, int userID) {
        this.homeID = homeID;
        this.userID = userID;
    }

    public String getHomeID() {
        return homeID;
    }

    public int getUserID() {
        return userID;
    }
}
