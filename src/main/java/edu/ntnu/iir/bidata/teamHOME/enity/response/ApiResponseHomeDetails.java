package edu.ntnu.iir.bidata.teamHOME.enity.response;

/**
 * Response object for a user.
 */
public class ApiResponseHomeDetails {
    private String message;
    private HomeDetails home;
    
    public ApiResponseHomeDetails(String message, HomeDetails home) {
        this.message = message;
        this.home = home;
    }

    public String getMessage() {
        return message;
    }

    public HomeDetails getHome() {
        return home;
    }
}
