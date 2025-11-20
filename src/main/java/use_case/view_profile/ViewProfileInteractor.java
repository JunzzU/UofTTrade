package use_case.view_profile;

import entity.User;
import java.util.List;

/**
 * Interactor for the View Profile use case.
 * Retrieves the logged-in user's profile and sends results to the presenter.
 */
public class ViewProfileInteractor implements ViewProfileInputBoundary {

    private final ViewProfileUserDataAccessInterface userDataAccess;
    private final ViewProfileOutputBoundary presenter;

    public ViewProfileInteractor(ViewProfileUserDataAccessInterface userDataAccess,
                                 ViewProfileOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(ViewProfileInputData inputData) {

        // Step 1: Get the currently logged-in User entity
        User user = userDataAccess.getCurrentLoggedInUser();

        if (user == null) {
            presenter.prepareFailView("No user is currently logged in.");
            return;
        }

        String username = user.get_username();

        // Step 2: Retrieve listings for that user (just names)
        List<String> listings = userDataAccess.getUserListings(username);

        // Step 3: Create output data
        ViewProfileOutputData outputData =
                new ViewProfileOutputData(username, listings);

        // Step 4: Send success to presenter
        presenter.prepareSuccessView(outputData);
    }
}
