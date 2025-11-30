package use_case.messaging;

import java.io.IOException;

/**
 * Data access boundary for the Messaging use case.
 * It hides where the user data comes from Pantry.
 */
public interface MessagingUserDataAccessInterface {

    /**
     * Given the username as the user identifier.
     * return a valid email address for this user.
     * If no such user exists OR user has no valid email,
     * return null.
     *
     * @param userIdentifier username used to identify the user
     * @return a valid email string or null if not found / invalid
     * @throws IOException if there is an error accessing the data source
     */
    String getValidEmailForUser(String userIdentifier) throws IOException;
    boolean isPlasuibleEmail(String email);

}
