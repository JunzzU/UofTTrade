package use_case.messaging;

import entity.User;

public interface MessagingUserDataAccessInterface {
    boolean existByEmail(String email);
    User findById(int id);
    User save(User user);
}
