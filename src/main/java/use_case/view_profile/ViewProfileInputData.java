package use_case.view_profile;

/**
 * Input data for the View Profile use case.
 * Contains the username of the user whose profile should be viewed.
 */
public class ViewProfileInputData {

    private final String username;

    /**
     * Creates a new {@code ViewProfileInputData} instance.
     *
     * @param username the username whose profile should be retrieved
     */
    public ViewProfileInputData(String username) {
        this.username = username;
    }

    /**
     * Returns the username for which the profile is requested.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }
}

