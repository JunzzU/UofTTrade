package data_access;

import entity.Listing;
import use_case.create_listing.CreateListingUserDataAccessInterface;

public class CreateListingDAO implements CreateListingUserDataAccessInterface {
    /**
     * Checks if the given name exists.
     *
     * @param name the name to look for
     * @return true if a user with the given name exists; false otherwise
     */
    @Override
    public boolean existsByName(String name) {
        //TODOOOO
        return false;
    }

    /**
     * Saves the lisiting.
     * @param listing the listing to save
     */
    @Override
    public void save(Listing listing) {

    }
}
