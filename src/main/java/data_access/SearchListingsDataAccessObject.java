package data_access;

import entity.Category;
import entity.Listing;
import entity.User;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.search.SearchListingsDataAccessInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchListingsDataAccessObject implements SearchListingsDataAccessInterface {

    private static final String API_URL = "https://getpantry.cloud/apiv1/pantry/c8a932ca-ce25-4926-a92c-d127ecb78809/basket/LISTINGS";

    @Override
    public List<Listing> findByKeywordAndCategory(String keyword, String categoryName) {
        List<Listing> allListings = fetchAllListings();
        List<Listing> filtered = new ArrayList<>();

        String normKeyword = (keyword == null) ? "" : keyword.toLowerCase().trim();
        String normCategory = (categoryName == null || categoryName.equals("Select a Category")) ? "" : categoryName;

        for (Listing listing : allListings) {
            // Check Name OR Description
            boolean matchesKeyword = normKeyword.isEmpty() ||
                    listing.get_name().toLowerCase().contains(normKeyword) ||
                    listing.get_description().toLowerCase().contains(normKeyword);

            // Check Category
            boolean matchesCategory = normCategory.isEmpty() ||
                    hasCategory(listing, normCategory);

            if (matchesKeyword && matchesCategory) {
                filtered.add(listing);
            }
        }
        return filtered;
    }

    @Override
    public List<Listing> findByKeyword(String keyword) {
        return findByKeywordAndCategory(keyword, null);
    }

    @Override
    public List<Listing> findByCategory(String categoryName) {
        return findByKeywordAndCategory(null, categoryName);
    }

    @Override
    public List<String> getAllCategories() {
        Set<String> uniqueCategories = new HashSet<>();
        uniqueCategories.add("Select a Category");
        uniqueCategories.add("Technology");
        uniqueCategories.add("Furniture");
        uniqueCategories.add("Sports");
        uniqueCategories.add("Textbooks");
        uniqueCategories.add("Clothing");

        List<Listing> listings = fetchAllListings();

        for (Listing listing : listings) {
            if (listing.get_categories() != null) {
                for (Category category : listing.get_categories()) {
                    if (category.getName() != null && !category.getName().isEmpty()) {
                        uniqueCategories.add(category.getName());
                    }
                }
            }
        }

        List<String> result = new ArrayList<>(uniqueCategories);
        result.remove("Select a Category");
        Collections.sort(result);
        result.add(0, "Select a Category");

        return result;
    }

    private List<Listing> fetchAllListings() {
        List<Listing> listings = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(API_URL)
                .get()
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                String responseBody = response.body().string();
                JSONObject json = new JSONObject(responseBody);

                if (json.has("Listings")) {
                    JSONArray listingsArray = json.getJSONArray("Listings");
                    for (int i = 0; i < listingsArray.length(); i++) {
                        JSONObject listJson = listingsArray.getJSONObject(i);
                        listings.add(mapJsonToListing(listJson));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listings;
    }

    private Listing mapJsonToListing(JSONObject json) {
        String name = json.getString("Name");
        String photoBase64 = json.getString("Photo");
        String description = json.optString("Description", "No description available.");

        List<Category> categories = new ArrayList<>();
        JSONArray catsArray = json.getJSONArray("Categories");
        for (int i = 0; i < catsArray.length(); i++) {
            Object catObj = catsArray.get(i);
            if (catObj instanceof JSONObject) {
                categories.add(new Category(((JSONObject) catObj).optString("name", "Unknown")));
            } else if (catObj instanceof String) {
                categories.add(new Category((String) catObj));
            }
        }

        String ownerName = "Unknown";
        if (json.has("Owner")) {
            Object ownerObj = json.get("Owner");
            if (ownerObj instanceof JSONObject) {
                ownerName = ((JSONObject) ownerObj).optString("name", "Unknown");
            } else {
                ownerName = ownerObj.toString();
            }
        }
        User owner = new User(ownerName, "", "");

        return new Listing(name, description, photoBase64, categories, owner);
    }

    private boolean hasCategory(Listing listing, String categoryName) {
        for (Category c : listing.get_categories()) {
            if (c.getName().equalsIgnoreCase(categoryName)) {
                return true;
            }
        }
        return false;
    }
}