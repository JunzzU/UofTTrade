package use_case.view_profile;

import java.util.List;

/**
 * Output data for the View Profile use case.
 * Contains the username and the user's listing names.
 */
public class ViewProfileOutputData {

    private final String username;
    private final List<String> listingNames;

    public ViewProfileOutputData(String username, List<String> listingNames) {
        this.username = username;
        this.listingNames = listingNames;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getListingNames() {
        return listingNames;
    }
}




