package interface_adapter.view_profile;

import entity.Listing;
import java.util.List;
import use_case.view_profile.ViewProfileOutputBoundary;
import use_case.view_profile.ViewProfileOutputData;

/**
 * Presenter for the View Profile use case.
 * Updates the ViewModel's state after success or failure
 * and notifies the view.
 */
public class ViewProfilePresenter implements ViewProfileOutputBoundary {

    private final ViewProfileViewModel viewModel;

    public ViewProfilePresenter(ViewProfileViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ViewProfileOutputData outputData) {
        ViewProfileState state = viewModel.getState();

        state.setUsername(outputData.getUsername());

        // Convert List<Listing> to List<String> of listing names.
        List<String> listingNames = outputData.getListings()
                .stream()
                .map(Listing::get_name)
                .toList();

        state.setListingNames(listingNames);
        state.setUser(outputData.getUser());

        // Dynamic title
        state.setTitleText("Profile: " + outputData.getUsername());

        // Clear error
        state.setErrorMessage("");

        // Show "No listings yet..." when list is empty
        if (listingNames.isEmpty()) {
            state.setNoListingsMessage("No listings yet...");
        } else {
            state.setNoListingsMessage("");
        }

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        ViewProfileState state = viewModel.getState();

        state.setErrorMessage(errorMessage);
        state.setUsername("");
        state.setListingNames(List.of());
        state.setTitleText("");
        state.setNoListingsMessage("");

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}
