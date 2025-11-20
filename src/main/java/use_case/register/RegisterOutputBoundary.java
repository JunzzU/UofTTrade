package use_case.register;

public interface RegisterOutputBoundary {

    void prepareSuccessView(RegisterOutputData registerOutputData);
    void prepareFailureView(String message);
    void switchToLoginView();
}
