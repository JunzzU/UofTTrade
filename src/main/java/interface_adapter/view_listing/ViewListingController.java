package interface_adapter.view_listing;

import java.io.IOException;

import use_case.view_listing.ViewListingInputBoundary;
import use_case.view_listing.ViewListingInputData;

public class ViewListingController {

    private final ViewListingInputBoundary viewListingInputBoundary;

    public ViewListingController(ViewListingInputBoundary viewListingInputBoundary) {
        this.viewListingInputBoundary = viewListingInputBoundary;
    }

    /**
     * Executes the view listing use case.
     * @param listingName the name of the listing.
     * @param listingOwner the name of the owner of the listing.
     * @throws IOException thrown incase the function viewListingInputBoundary.execute() fails due to the API failing.
     */
    public void execute(String listingName, String listingOwner) throws IOException {

        final ViewListingInputData viewListingInputData = new ViewListingInputData(listingName, listingOwner);
        viewListingInputBoundary.execute(viewListingInputData);

    }

    /**
     * Switches to the previous view from the detailed listingwhen a button is pressed.
     */
    public void switchToPreviousView() {
        viewListingInputBoundary.switchToPreviousView();
    }

}
