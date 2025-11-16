package interface_adapter.register;

import use_case.register.RegisterInputBoundary;
import use_case.register.RegisterInputData;

import java.io.IOException;

public class RegisterController {

    private final RegisterInputBoundary userRegisterUseCaseInteractor;

    public RegisterController(RegisterInputBoundary userRegisterUseCaseInteractor) {
        this.userRegisterUseCaseInteractor = userRegisterUseCaseInteractor;
    }


    public void execute(String username, String email, String password, String confirmPassword) throws IOException {
        final RegisterInputData registerInputData = new RegisterInputData(
                username, email, password, confirmPassword);

        userRegisterUseCaseInteractor.execute(registerInputData);
    }

    /**
     * Executes the "switch to LoginView" Use Case.
     */
    public void switchToLoginView() {
        userRegisterUseCaseInteractor.switchToLoginView();
    }

}
