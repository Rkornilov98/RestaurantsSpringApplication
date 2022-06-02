package dev.rkorn.controller;

import dev.rkorn.entities.Restaurant;
import dev.rkorn.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/restaurants")
public class RestaurantsAdminController {
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantsAdminController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("restaurant", restaurantService.index());
        return "restaurants/admin/index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("restaurant") Restaurant restaurant) {
        return "restaurants/admin/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("restaurant") @Valid Restaurant restaurant,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "restaurants/admin/new";
        restaurantService.save(restaurant);
        return "redirect:/restaurants";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("restaurant", restaurantService.show(id));
        return "restaurants/admin/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("restaurant") @Valid Restaurant restaurant,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "restaurants/admin/edit";
        restaurantService.update(id, restaurant);
        return "redirect:/restaurants";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        restaurantService.delete(id);
        return "redirect:/restaurants";
    }


}
