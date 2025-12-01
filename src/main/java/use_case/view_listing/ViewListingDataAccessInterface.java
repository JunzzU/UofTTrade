package use_case.view_listing;

import java.io.IOException;

import org.json.JSONObject;

public interface ViewListingDataAccessInterface {

    /**
     * Gets all the relevant information about a listing from its owner and name.
     * @param listingName the name of the listing.
     * @param listingOwner the name of the owner of the listing.
     * @return a JSONObject with the listing data as stored in the API.
     * @throws IOException thrown incase a call to the API fails.
     */
    JSONObject getSpecificListingInfo(String listingName, String listingOwner) throws IOException;

}
