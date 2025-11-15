package use_case.register;

public interface RegisterInputBoundary {

    void execute(RegisterInputData registerInputData);
    void switchToLoginView();

}
