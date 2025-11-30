package use_case.view_listing;

import java.io.IOException;

import org.json.JSONObject;

public interface ViewListingDataAccessInterface {

    JSONObject getSpecificListingInfo(String listingName, String listingOwner) throws IOException;

}
