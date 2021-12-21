package example.fourth.service;

/**
 * Service of Security
 */
public interface SecurityService {

    String findLoggedInUsername();
    void autoLogin(String username, String password);
}
