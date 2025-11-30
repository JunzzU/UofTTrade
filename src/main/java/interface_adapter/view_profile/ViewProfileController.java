package interface_adapter.view_profile;

import use_case.view_profile.ViewProfileInputBoundary;
import use_case.view_profile.ViewProfileInputData;

/**
 * Controller for the View Profile use case.
 */
public class ViewProfileController {

    private final ViewProfileInputBoundary interactor;
    private String username;

    /**
     * Constructs a controller for the View Profile use case.
     *
     * @param interactor the interactor that performs the use case logic
     */
    public ViewProfileController(ViewProfileInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Sets the username for the profile to view.
     *
     * @param username the username of the profile to load
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Executes the View Profile use case when triggered by the UI.
     */
    public void onProfileButtonClicked() {
        ViewProfileInputData inputData = new ViewProfileInputData(username);
        interactor.execute(inputData);
    }
}

