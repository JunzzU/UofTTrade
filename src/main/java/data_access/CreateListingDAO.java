package data_access;

import entity.Listing;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.create_listing.CreateListingUserDataAccessInterface;

import java.io.IOException;

public class CreateListingDAO implements CreateListingUserDataAccessInterface {
    /**
     * Checks if the given name exists.
     *
     * @param name the name to look for
     * @return true if a user with the given name exists; false otherwise
     */
    @Override
    public boolean existsByName(String name) throws IOException {
        JSONArray listings = getListingData();

        for(int i = 0; i < listings.length(); i++) {
            JSONObject listing = listings.getJSONObject(i);
            if(listing.getString("name").equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Saves the lisiting.
     * @param listing the listing to save
     */
    @Override
    public void save(Listing listing) throws IOException {
        // create a JSON object of the new listing
        JSONObject newListing = new JSONObject();
        newListing.put("Name", listing.get_name());
        newListing.put("Photo", listing.get_img());
        newListing.put("Categories", listing.get_categories());
        newListing.put("Owner", listing.get_owner());

        // add the new listing JSON object to the existing listings
        JSONArray listings = getListingData();
        listings.put(newListing);

        // create a new JSON object for the listings including the new listing
        JSONObject updatedListings = new JSONObject();
        updatedListings.put("Listings", listings);


        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, updatedListings.toString());
        Request request = new Request.Builder()
                .url("https://getpantry.cloud/apiv1/pantry/c8a932ca-ce25-4926-a92c-d127ecb78809/basket/LISTINGS")
                .put(body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
    }

    /**
     * Returns a JSONArray of the Listing JSON objects
     * @return JSONArray of the Listing JSON objects
     * @throws IOException
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
        JSONObject users = new JSONObject(response.body().string());

        return users.getJSONArray("Listings");

    }
}
