package interface_adapter.view_profile;

import entity.Listing;
import java.util.List;
import java.util.stream.Collectors;

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

        state.setUser(outputData.getUser());
        state.setUsername(outputData.getUsername());
        state.setTitleText("Profile: " + outputData.getUsername());

        // Convert List<Listing> → names + descriptions
        List<Listing> listings = outputData.getListings();

        List<String> listingNames = new java.util.ArrayList<>();
        List<String> listingDescriptions = new java.util.ArrayList<>();

        for (Listing l : listings) {
            listingNames.add(l.get_name());
            String desc = l.get_description();
            listingDescriptions.add(desc == null ? "" : desc);
        }

        state.setListingNames(listingNames);
        state.setListingDescriptions(listingDescriptions);

        // "No listings yet..." message
        if (listingNames.isEmpty()) {
            state.setNoListingsMessage("No listings yet...");
        } else {
            state.setNoListingsMessage("");
        }
        state.setErrorMessage("");

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
