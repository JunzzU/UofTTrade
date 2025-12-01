package use_case.view_listing;

import java.io.IOException;

public interface ViewListingInputBoundary {

    void execute(ViewListingInputData viewListingInputData) throws IOException;

    void switchToPreviousView();

}
