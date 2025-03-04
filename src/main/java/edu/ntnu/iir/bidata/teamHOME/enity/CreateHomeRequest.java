package edu.ntnu.iir.bidata.teamHOME.enity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request object for creating a home.
 * Contains the name of the home and the name of the user that creates the home.
 */
public class CreateHomeRequest {

    @NotBlank
	@Size(min=2, max=30)
    private String homeName;

    @NotBlank
	@Size(min=2, max=30)
    private String userName;

    public CreateHomeRequest(String homeName) {
        setHomeName(homeName);
        setUserName(userName);
    }

    public String getHomeName() {
        return this.homeName;
    }

    private void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getUserName() {
        return this.userName;
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }
}
