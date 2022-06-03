package dev.rkorn.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainController {
    @GetMapping("/")
    public String helloPage(Principal principal){

        return "redirect:/restaurants";
    }
}
