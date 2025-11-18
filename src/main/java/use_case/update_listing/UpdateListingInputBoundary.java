package use_case.update_listing;

import entity.Listing;

public interface UpdateListingInputBoundary {
    void execute(UpdateListingInputData updateListingInputData);
}
