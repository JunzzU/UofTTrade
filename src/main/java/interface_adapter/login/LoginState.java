package interface_adapter.login;

/**
 * The state for the login view
 */

public class LoginState {

    private String userIdentifier = "";
    private String loginError;
    private String password = "";

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public String getLoginError() {
        return loginError;
    }

    public String getPassword() {
        return password;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public void setLoginError(String userIdentifierError) {
        this.loginError = userIdentifierError;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
