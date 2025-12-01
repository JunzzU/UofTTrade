package data_access;

import entity.Category;
import entity.Listing;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.create_listing.CreateListingUserDataAccessInterface;
import use_case.view_listing.ViewListingDataAccessInterface;

import java.util.ArrayList;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class CreateListingDAO implements CreateListingUserDataAccessInterface, ViewListingDataAccessInterface {
    /**
     * Saves the listing to the API database.
     * @param listing the listing to save
     */
    @Override
    public void save(Listing listing) throws IOException {

        String listingID = String.valueOf(listing.get_listingId());
        JSONObject listings = getListingData();
        if (listings.has(listingID)) {
            throw new DuplicateListingException(listingID);
        }

        List<String> categoryNames = new ArrayList<>();
        for (Category c : listing.get_categories()) {
            categoryNames.add(c.getName());
        }
        // create a JSON object of the new listing
        JSONObject newListing = new JSONObject();
        newListing.put("Name", listing.get_name());
        newListing.put("Categories", listing.get_categories());
        newListing.put("Owner", listing.get_owner());
        newListing.put("ListingID", listing.get_listingId());
        newListing.put("Description", listing.get_description());

        // map the listing to the ID
        JSONObject updatedListings = new JSONObject();
        updatedListings.put(listingID, newListing);

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, updatedListings.toString());
        Request request = new Request.Builder()
                .url("https://getpantry.cloud/apiv1/pantry/c8a932ca-ce25-4926-a92c-d127ecb78809/basket/LISTINGS")
                .method("PUT", body)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).execute();
    }

    public JSONObject getSpecificListingInfo(String listingName, String listingOwner) throws IOException {
        final JSONObject listingsObject = getListingData();
        for (String key: listingsObject.keySet()) {
            final boolean isListingName = listingsObject.getJSONObject(key).getString("Name").equals(listingName);
            final boolean isOwner = listingsObject.getJSONObject(key).getString("Owner").equals(listingOwner);
            if (isListingName && isOwner) {
                return listingsObject.getJSONObject(key);
            }
        }
        return new JSONObject();
    }

    /**
     * Fetches the listings from the API database.
     * @return JSON Object of the data
     */
    private JSONArray getListingData() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://getpantry.cloud/apiv1/pantry/c8a932ca-ce25-4926-a92c-d127ecb78809/basket/LISTINGS")
                .get()
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        JSONObject listings = new JSONObject(response.body().string());
        return listings;

    }

    public class DuplicateListingException extends RuntimeException {
        public DuplicateListingException(String listingId) {
            super("Listing already exists: " + listingId);
        }
    }
}
