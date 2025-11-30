package interface_adapter.view_listing;

import use_case.view_listing.ViewListingInputBoundary;
import use_case.view_listing.ViewListingInputData;

public class ViewListingController {

    private final ViewListingInputBoundary viewListingInputBoundary;

    public ViewListingController(ViewListingInputBoundary viewListingInputBoundary) {
        this.viewListingInputBoundary = viewListingInputBoundary;
    }

    public void execute(String listingName, String listingOwner) {

        final ViewListingInputData viewListingInputData = new ViewListingInputData(listingName, listingOwner);
        viewListingInputBoundary.execute(viewListingInputData);

    }

    public void switchToPreviousView() {
        viewListingInputBoundary.switchToPreviousView();
    }

}
