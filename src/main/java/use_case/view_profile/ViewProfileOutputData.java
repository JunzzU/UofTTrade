package use_case.view_profile;

import entity.Listing;
import entity.User;
import java.util.List;

/**
 * Output data for the View Profile use case.
 * Contains the user's username, listings, and user entity.
 */
public class ViewProfileOutputData {

    private final String username;
    private final List<Listing> listings;
    private final User user;

    /**
     * Constructs the output data for the View Profile use case.
     *
     * @param username the username of the profile being viewed
     * @param listings the listings owned by the user
     * @param user the user entity
     */
    public ViewProfileOutputData(String username,
                                 List<Listing> listings,
                                 User user) {
        this.username = username;
        this.listings = listings;
        this.user = user;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the user's listings
     */
    public List<Listing> getListings() {
        return listings;
    }

    /**
     * @return the user entity
     */
    public User getUser() {
        return user;
    }
}











