package data_access;

import entity.Listing;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.create_listing.CreateListingUserDataAccessInterface;
import use_case.view_listing.ViewListingDataAccessInterface;

import java.io.IOException;

public class CreateListingDAO implements CreateListingUserDataAccessInterface, ViewListingDataAccessInterface {
    /**
     * Saves the lisiting to the API database.
     * @param listing the listing to save
     */
    @Override
    public void save(Listing listing) throws IOException {
        // create a JSON object of the new listing
        JSONObject newListing = new JSONObject();
        newListing.put("Name", listing.get_name());
        newListing.put("Photo", listing.get_img_in_Base64());
        newListing.put("Categories", listing.get_categories());
        newListing.put("Owner", listing.get_owner());
//        newListing.put("ListingID", listing.get_listingId());

        JSONArray listingsArray = new JSONArray();
        listingsArray.put(newListing);

        // create a new JSON object for the listings including the new listing
        JSONObject updatedListings = new JSONObject();
        updatedListings.put("Listings", listingsArray);


        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, updatedListings.toString());
        Request request = new Request.Builder()
                .url("https://getpantry.cloud/apiv1/pantry/c8a932ca-ce25-4926-a92c-d127ecb78809/basket/LISTINGS")
                .method("PUT", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
    }

    public JSONObject getSpecificListingInfo(String listingName, String listingOwner) throws IOException {
        final JSONArray listingsArray = getListingData();
        for (int i = 0; i < listingsArray.length(); i++) {
            final boolean isListingName = listingsArray.getJSONObject(i).getString("Name").equals(listingName);
            final boolean isOwner = listingsArray.getJSONObject(i).getString("Owner").equals(listingOwner);
            if (isListingName && isOwner) {
                return listingsArray.getJSONObject(i);
            }
        }
        return new JSONObject();
    }

    /**
     * Fetches the lisitings from the API database.
     * @return JSON Array of the data
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
        return listings.getJSONArray("Listings");

    }

    //    /**
//     * Checks if the given name exists.
//     *
//     * @param name the name to look for
//     * @return true if a user with the given name exists; false otherwise
//     */
//    public boolean existsByName(String name) throws IOException {
//        JSONArray listings = getListingData();
//
//        for(int i = 0; i < listings.length(); i++) {
//            JSONObject listing = listings.getJSONObject(i);
//            if(listing.getString("Name").equals(name)) {
//                return true;
//            }
//        }
//        return false;
//    }

    //    /**
//     * Checks if the given listingID exists.
//     *
//     * @param ListingID the listingID to look for
//     * @return true if a user with the given listingID exists; false otherwise
//     */
//    @Override
//    public boolean existsByListingID(int ListingID) throws IOException {
//        JSONArray listings = getListingData();
//
//        for(int i = 0; i < listings.length(); i++) {
//            JSONObject listing = listings.getJSONObject(i);
//            if(listing.getInt("ListingID") == ListingID) {
//                return true;
//            }
//        }
//        return false;
//    }
}
