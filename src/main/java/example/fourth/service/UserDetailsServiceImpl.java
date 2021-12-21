package example.fourth.service;

import example.fourth.model.Role;
import example.fourth.model.User;
import example.fourth.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of {@link org.springframework.security.core.userdetails.UserDetailsService}
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**
         * Создать пользователя, которого ищем в бд по имени
         */
        User user = userDao.findByUsername(username);

        /**
         * Разрешение ?????
         */
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        /**
         * Дать пользователю разрешение
         * Добавить в разрешения пользователя все роли ,которые хранятся у него в бд
         */
        for (Role role : user.getRoles()) {
            /**
             * Получить все роли, которые есть и запихать в разрешение
             */
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
