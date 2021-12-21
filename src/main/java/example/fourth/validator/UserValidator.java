package example.fourth.validator;

import example.fourth.model.User;
import example.fourth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for {@link example.fourth.model.User}
 * Implements {@link Validator}
 */
@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass); // подтверждаем ,что aClass является экземпляром User
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        /**
         * Проверки username
         */
        /**
         * Проврека на обязательность ввода
         */
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"username", "Required");

        /**
         * Проверка на длину имени пользователя
         */
        if(user.getUsername().length() < 8 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userFrom.username");
        }

        /**
         * Проверка на наличие пользователя с таким же именем в бд
         */
        if(userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.username");
        }

        /**
         * Проверки password
         */
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
        if(user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }
        if(!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "Different.userForm.password");
        }
    }
}
