package example.fourth.controller;

import example.fourth.model.User;
import example.fourth.service.SecurityService;
import example.fourth.service.UserService;
import example.fourth.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for {@link example.fourth.model.User}'s pages.
 */

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    /**
     * @return страница регистрации
     */
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    /**
     * Контроллер для передачи значений при регистрации (при заполнении формы)
     */
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm,
                               BindingResult bindingResult, // связывает результаты (ошибки)
                               Model model
    ) {
        userValidator.validate(userForm, bindingResult); // валидация введенных в форму значений

        if (bindingResult.hasErrors()) {
            return "registration"; // если при регистрации содержатся ошибки, то остаемся на этой же странице
        }

        userService.save(userForm); // если все ок, сохранить пользователя
        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());

        return "redirect:/welcome";
    }

    /**
     * @return страница логина
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if(error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }

        if(logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }
        return "login";
    }

    /**
     * Контроллер для страницы логин и welcome
     * @return страница welcome
     */
    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)  // массив для двух страниц
    public String welcome(Model model) {
        return "welcome";
    }

    /**
     * Контроллер для страницы администрации
     * @return страница admin
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        return "admin";
    }
}
