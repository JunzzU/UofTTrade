package interface_adapter.update_listing;

import entity.Listing;
import entity.User;
import use_case.update_listing.UpdateListingInputBoundary;
import use_case.update_listing.UpdateListingInputData;
import use_case.update_listing.UpdateListingOutputData;

public class UpdateListingController {
    private final UpdateListingInputBoundary updateListingUseCaseInteractor;

    public UpdateListingController(UpdateListingInputBoundary updateListingUseCaseInteractor) {
        this.updateListingUseCaseInteractor = updateListingUseCaseInteractor;
    }

    public void execute(boolean isDelete, User user, Listing listing) {
        final UpdateListingInputData updateListingInputData = new UpdateListingInputData(
                isDelete, user, listing);
        updateListingUseCaseInteractor.execute(updateListingInputData);
    }
}
