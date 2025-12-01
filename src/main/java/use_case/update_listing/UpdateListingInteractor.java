package use_case.update_listing;
import entity.Listing;
import entity.User;

public class UpdateListingInteractor implements UpdateListingInputBoundary {
    private final UpdateListingUserDataAccessInterface userDataAccessObject;
    private final UpdateListingOutputBoundary updateListingPresenter;

    public  UpdateListingInteractor(UpdateListingUserDataAccessInterface userDataAccessObject,
                                    UpdateListingOutputBoundary updateListingPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.updateListingPresenter = updateListingPresenter;
    }

    public void execute(UpdateListingInputData updateListingInputData) {
        final User user = updateListingInputData.getUser();
        final boolean isDelete = updateListingInputData.getDelete();
        final Listing listing = updateListingInputData.getListing();
        final int listingID = listing.get_listingId();
        if (isDelete) {
            user.delete_listing(listing);
            userDataAccessObject.updateListing(listingID);
            final UpdateListingOutputData updateListingOutputData = new UpdateListingOutputData(user);
            updateListingPresenter.prepareSuccessView(updateListingOutputData);
        }
    }
}
