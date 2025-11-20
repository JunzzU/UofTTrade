package use_case.login;

/**
 * The input data for a user to login in to the program
 */

public class LoginInputData {

    private final String userIdentifier;
    private final String password;

    public LoginInputData(String userIdentifier, String password) {
        this.userIdentifier = userIdentifier;
        this.password = password;
    }

    String getUserIdentifier() {
        return userIdentifier;
    }

    String getPassword() {
        return password;
    }



}
