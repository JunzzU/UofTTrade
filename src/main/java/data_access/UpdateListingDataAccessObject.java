package data_access;

import okhttp3.*;
import org.json.JSONObject;
import use_case.update_listing.UpdateListingUserDataAccessInterface;

import java.io.IOException;

public class UpdateListingDataAccessObject implements UpdateListingUserDataAccessInterface {

    private final CreateListingDAO createListingDAO;

    public UpdateListingDataAccessObject() {
        this.createListingDAO = new CreateListingDAO();   // or inject via constructor
    }

    private static final String URL =
            "https://getpantry.cloud/apiv1/pantry/c8a932ca-ce25-4926-a92c-d127ecb78809/basket/LISTINGS";

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public void updateListing(int listingId) {
        try {
            String key = String.valueOf(listingId);

            // 1. Fetch current JSON
            JSONObject listings = createListingDAO.getListingData();

            // 2. Check existence
            if (!listings.has(key)) {
                throw new ListingNotFoundException(key);
            }

            // 3. Remove the listing
            listings.remove(key);

            // 4. PUT updated object
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, listings.toString());

            Request request = new Request.Builder()
                    .url(URL)
                    .put(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Failed to delete listing: "
                            + response.code() + " " + response.message());
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to delete listing: " + e.getMessage());
        }
    }

    public static class ListingNotFoundException extends RuntimeException {
        public ListingNotFoundException(String listingId) {
            super("Listing not found: " + listingId);
        }
    }
}

