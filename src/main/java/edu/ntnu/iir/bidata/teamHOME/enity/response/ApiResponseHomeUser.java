package edu.ntnu.iir.bidata.teamHOME.enity.response;

/**
 * Response object for creating a home.
 */
public class ApiResponseHomeUser {
    private String message;
    private Home Home;
    private User User;

    public ApiResponseHomeUser(String message, Home Home, User User) {
        this.message = message;
        this.Home = Home;
        this.User = User;
    }

    public String getMessage() {
        return message;
    }

    public Home getHome() {
        return Home;
    }

    public User getUser() {
        return User;
    }
}
