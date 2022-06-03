package dev.rkorn.controllers;

import dev.rkorn.entities.Restaurant;
import dev.rkorn.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class RestaurantUserController {
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantUserController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping()
    public String index(Model model, Principal principal) {
        model.addAttribute("restaurant", restaurantService.index());
        model.addAttribute("username", principal.getName());
        return "restaurants/user/index";
    }

    @PatchMapping("/{id}/select")
    public String select(@ModelAttribute("restaurant") Restaurant restaurant, @PathVariable int id) {
        //нужна реализация обновления таблицы выбора
        return "redirect:/user";
    }
}
