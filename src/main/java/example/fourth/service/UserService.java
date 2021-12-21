package example.fourth.service;

import example.fourth.model.User;

/**
 * Service class for {@link example.fourth.model.User}
 */
public interface UserService {
    void save(User user);
    User findByUsername(String username);
}
