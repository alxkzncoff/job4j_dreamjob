package ru.job4j.dreamjob.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;

import java.sql.SQLException;
import java.util.Optional;

@ThreadSafe
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String fromRegistration(Model model) {
        return "/registration";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user) throws SQLException {
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с таким именем уже существует!");
            return "redirect:/userRegistrationFail";
        }
        return "redirect:/userRegistrationSuccess";
    }

    @GetMapping("/userRegistrationFail")
    public String userRegistrationFail() {
        return "/userRegistrationFail";
    }

    @GetMapping("/userRegistrationSuccess")
    public String userRegistrationSuccess() {
        return "/userRegistrationSuccess";
    }
}
