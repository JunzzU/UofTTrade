package use_case.view_listing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewListingInteractor implements ViewListingInputBoundary {

    private final ViewListingDataAccessInterface viewListingDataAccess;
    private final ViewListingOutputBoundary viewListingOutputBoundary;

    public ViewListingInteractor(ViewListingDataAccessInterface viewListingDataAccess,
                                 ViewListingOutputBoundary viewListingOutputBoundary) {
        this.viewListingDataAccess = viewListingDataAccess;
        this.viewListingOutputBoundary = viewListingOutputBoundary;
    }

    @Override
    public void execute(ViewListingInputData viewListingInputData) throws IOException {

        final String listingName = viewListingInputData.getListingName();
        final String listingOwner = viewListingInputData.getListingOwner();
        final JSONObject listing = viewListingDataAccess.getSpecificListingInfo(listingName, listingOwner);

        final JSONArray listingCategoriesJSON = listing.getJSONArray("Categories");
        final List<String> listingCategories = new ArrayList<>();
        for (int i = 0; i < listingCategoriesJSON.length(); i++) {
            listingCategories.add(listingCategoriesJSON.getString(i));
        }

        final ViewListingOutputData viewListingOutputData = new ViewListingOutputData(listingName, listingOwner,
                listingCategories, (String) listing.get("Description"));
        viewListingOutputBoundary.switchToListingView(viewListingOutputData);

    }

    @Override
    public void switchToPreviousView() {

        viewListingOutputBoundary.switchToPreviousView();

    }

}
