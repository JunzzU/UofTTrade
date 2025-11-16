package use_case.register;

import entity.User;

import java.io.IOException;

public interface RegisterUserDataAccessInterface {

    void save(User user) throws IOException;
    boolean userExists(String userIdentifier);

}
