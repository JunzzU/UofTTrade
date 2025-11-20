package interface_adapter.view_profile;

import use_case.view_profile.ViewProfileInputBoundary;
import use_case.view_profile.ViewProfileInputData;

/**
 * Controller for viewing a user's profile.
 */
public class ViewProfileController {

    private final ViewProfileInputBoundary interactor;

    /**
     * Creates a controller for the View Profile use case.
     *
     * @param interactor the interactor to run the use case
     */
    public ViewProfileController(ViewProfileInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Runs the View Profile use case when the profile button is clicked.
     */
    public void onProfileButtonClicked() {
        ViewProfileInputData inputData = new ViewProfileInputData();
        interactor.execute(inputData);
    }
}
