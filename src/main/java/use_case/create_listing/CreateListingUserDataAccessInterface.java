package use_case.create_listing;
public interface CreateListingUserDataAccessInterface {
    /**
     * Checks if a listing with the given name DOESN'T exist.
     * @param name input
     * @return true if name is Null
     */
    boolean nameFieldIsNull(String name);

}
