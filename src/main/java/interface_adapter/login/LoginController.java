package interface_adapter.login;

import use_case.login.LoginInputData;
import use_case.login.LoginInputBoundary;

import java.io.IOException;

/**
 * The controller for the login use case
 */

public class LoginController {

    private final LoginInputBoundary inputBoundary;

    public LoginController(LoginInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    /**
     * Executes the user's login attempt
     * @param userIdentifier: String with the username/email of the user logging in
     * @param password: String with the password of the user logging in
     */

    public void executeLogin(String userIdentifier, String password) throws IOException {

        final LoginInputData  loginInputData = new LoginInputData(userIdentifier, password);
        inputBoundary.execute(loginInputData);

    }

    public void switchToRegisterView() {
        inputBoundary.switchToRegisterView();
    }

}
