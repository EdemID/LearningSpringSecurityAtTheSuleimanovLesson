package example.fourth.dao;

import example.fourth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username); //возвращает пользователя по имени
}
