package use_case.view_profile;

import entity.Listing;
import entity.User;
import java.util.List;

/**
 * Interface for accessing profile-related data.
 * Provides methods to retrieve user information and their listings.
 */
public interface ViewProfileUserDataAccessInterface {

    /**
     * Returns the currently logged-in user.
     *
     * @return the User entity representing the currently logged-in user
     */
    User getCurrentLoggedInUser();

    /**
     * Returns all listings owned by the specified user.
     *
     * @param username the username of the user whose listings are needed
     * @return a list of Listing entities belonging to the user
     */
    List<Listing> getUserListings(String username);
}
