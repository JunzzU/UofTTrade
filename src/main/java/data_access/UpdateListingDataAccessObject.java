package data_access;

import okhttp3.*;
import org.json.JSONObject;
import use_case.update_listing.UpdateListingUserDataAccessInterface;

import java.io.IOException;

public class UpdateListingDataAccessObject implements UpdateListingUserDataAccessInterface {

    private static final String URL =
            "https://getpantry.cloud/apiv1/pantry/c8a932ca-ce25-4926-a92c-d127ecb78809/basket/LISTINGS";

    private final OkHttpClient client = new OkHttpClient();
    private final CreateListingDAO createListingDAO = new CreateListingDAO();

    @Override
    public void updateListing(int listingId) {
        try {
            String key = String.valueOf(listingId);

            // 1) Fetch current JSON
            JSONObject listings = createListingDAO.getListingData();

            if (!listings.has(key)) {
                return;
            }

            // 2) Remove listing
            listings.remove(key);

            // 3) PUT back to Pantry
            MediaType mediaType = MediaType.parse("application/json");
            String jsonToSend = listings.toString();

            RequestBody body = RequestBody.create(mediaType, jsonToSend);

            Request request = new Request.Builder()
                    .url(URL)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (!response.isSuccessful()) {
                    throw new IOException("Failed to delete listing: " +
                            response.code() + " " + response.message());
                }
            }

        } catch (IOException e) {
            System.err.println("Failed to delete listing: " + e.getMessage());
        }
    }
}

