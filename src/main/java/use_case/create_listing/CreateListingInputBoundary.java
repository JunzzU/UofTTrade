package use_case.create_listing;

public interface CreateListingInputBoundary {
    void execute(CreateListingInputData createListingInputData);

    /**
     * Executes the switch to profile view use case.
     */
    void switchToProfileView();
}
