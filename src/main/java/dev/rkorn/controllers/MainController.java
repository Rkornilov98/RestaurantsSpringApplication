package dev.rkorn.controllers;

import dev.rkorn.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainController {
    UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String helloPage(Principal principal){
        if (principal==null) return "redirect:/login";

        if (userService.getAuthority(principal.getName()).equals("ROLE_ADMIN")){
            return "redirect:/restaurants";
        }
        else return "redirect:/user";
    }
}
