package use_case.view_profile;

public interface ViewProfileOutputBoundary {

    /** Called when the profile is successfully retrieved. */
    void prepareSuccessView(ViewProfileOutputData outputData);

    /** Called when the profile retrieval fails (e.g., user not found). */
    void prepareFailView(String errorMessage);
}
