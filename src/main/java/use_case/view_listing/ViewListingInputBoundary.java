package use_case.view_listing;

import java.io.IOException;

public interface ViewListingInputBoundary {

    /**
     * Executes the rendering of a detailed listing upon clicking a listing preview panel.
     * @param viewListingInputData The input data used to get the information to render a detailed listing.
     * @throws IOException Exception thrown if accessing the database fails.
     */
    void execute(ViewListingInputData viewListingInputData) throws IOException;

    /**
     * Switches to the previous view from the detailed listingwhen a button is pressed.
     */
    void switchToPreviousView();

}
