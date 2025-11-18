package interface_adapter.register;

import interface_adapter.ViewModel;

public class RegisterViewModel extends ViewModel<RegisterState> {

    public static final String TITLE_LABEL = "UofTTrade Register";
    public static final String EMAIL_LABEL = "Email:";
    public static final String USERNAME_LABEL = "Username:";
    public static final String PASSWORD_LABEL = "Password:";
    public static final String REPEAT_PASSWORD_LABEL = "Confirm Password:";
    public static final String REGISTER_BUTTON_LABEL = "Register";
    public static final String LOGIN_BUTTON_LABEL = "Login";


    public RegisterViewModel() {
        super("register");
        setState(new RegisterState());
    }

}
