package dev.rkorn.controllers;

import dev.rkorn.entities.User;
import dev.rkorn.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class MainController {
    UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String helloPage(Principal principal) {
        if (principal == null) return "redirect:/login";

        if (userService.getAuthority(principal.getName()).equals("ROLE_ADMIN")) {
            return "redirect:/restaurants";
        } else return "redirect:/user";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") User user) {

        return "restaurants/registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "restaurants/registration";
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute("registrationError", "Passwords do not match");
            return "restaurants/registration";
        }
        try {
            userService.save(user);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("registrationError", "User with this name already exists");
            return "restaurants/registration";
        }

    }

}
