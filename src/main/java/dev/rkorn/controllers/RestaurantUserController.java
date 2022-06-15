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
    StringBuilder error_text = new StringBuilder("");
    @Autowired
    public RestaurantUserController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping()
    public String index(Model model, Principal principal) {
        model.addAttribute("exception", error_text);
        error_text = new StringBuilder("");
        model.addAttribute("restaurant", restaurantService.index());
        model.addAttribute("username", principal.getName());
        return "restaurants/user/index";
    }

    @GetMapping("/{id}/select")
    public String select(Model model, @PathVariable int id, Principal principal) {
        model.addAttribute("restaurant", restaurantService.show(id));
        return "restaurants/user/select";
    }
    @PatchMapping("/{id}")
    public String select(@ModelAttribute("restaurant") Restaurant restaurant,
                         @PathVariable("id") int id, Principal principal){
        try {
            restaurantService.select(principal.getName(), id);
        } catch (Exception e)
        {
            error_text = new StringBuilder("You cannot change your restaurant selection after 11:00:00!");
        }
        return "redirect:/user";
    }

}
