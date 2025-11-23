package use_case.create_listing;

import entity.Listing;

import java.io.IOException;

public interface CreateListingUserDataAccessInterface {
    /**
     * Checks if the given name exists.
     * @param name the name to look for
     * @return true if a user with the given name exists; false otherwise
     */
    boolean existsByName(String name) throws IOException;

    /**
     * Saves the lisiting.
     * @param listing the listing to save
     */
    void save(Listing listing) throws IOException;
}
