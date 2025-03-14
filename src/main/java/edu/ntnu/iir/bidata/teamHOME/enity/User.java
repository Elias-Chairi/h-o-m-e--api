package edu.ntnu.iir.bidata.teamHOME.enity;

/**
 * Represents a user.
 */
public class User {
    private int userID;
    private String userName;

    public User(int userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }
}
