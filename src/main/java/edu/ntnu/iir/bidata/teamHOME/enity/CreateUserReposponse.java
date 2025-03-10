package edu.ntnu.iir.bidata.teamHOME.enity;

/**
 * Response object for creating a user.
 * Contains the userID of the created user.
 */
public class CreateUserReposponse {
    private int userID;

    public CreateUserReposponse(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }
}
