package use_case.view_profile;

/**
 * Input boundary for the View Profile use case.
 * The interactor calls this method to start the profile retrieval process.
 */
public interface ViewProfileInputBoundary {
    /**
     * Execute the use case with the given input data.
     *
     * @param inputData the data needed to retrieve the profile
     */
    void execute(ViewProfileInputData inputData);
}
