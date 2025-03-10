package edu.ntnu.iir.bidata.teamHOME.enity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request object for creating a user.
 * Contains the name of the user.
 */
public class CreateUserRequest {
    @NotBlank
	@Size(min=2, max=30)
    private String userName;

    public CreateUserRequest(String homeName) {
        setUserName(userName);
    }

    public String getUserName() {
        return this.userName;
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }
}
