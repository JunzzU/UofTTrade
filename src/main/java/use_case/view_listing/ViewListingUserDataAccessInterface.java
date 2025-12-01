package use_case.view_listing;

import entity.User;
import java.io.IOException;

/**
 * Data access interface used by the ViewListing use case
 * to retrieve user-related information.
 *
 * <p>This interface is responsible ONLY for operations
 * related to users, such as obtaining a user's profile
 * or email address. It should not perform any listing
 * retrieval or listing-related logic, which belongs in
 * ViewListingDataAccessInterface.</p>
 *
 * <p>By separating the user-related data access from the
 * listing-related data access, we preserve the Single
 * Responsibility Principle and maintain clean architectural
 * boundaries.</p>
 */
public interface ViewListingUserDataAccessInterface {

    /**
     * Returns the User object corresponding to the given identifier.
     *
     * <p>The identifier may be a username or an email address.
     * The implementation should handle both cases.</p>
     *
     * @param userIdentifier the username or email of the user
     * @return the User object if found; null otherwise
     * @throws IOException if a data access error occurs
     */
    User getUser(String userIdentifier) throws IOException;
}
