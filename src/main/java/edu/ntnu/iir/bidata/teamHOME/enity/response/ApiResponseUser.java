package edu.ntnu.iir.bidata.teamHOME.enity.response;

/**
 * Response object for a user.
 */
public class ApiResponseUser {
    private String message;
    private User user;
    
    public ApiResponseUser(String message, User user) {
        this.message = message;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
