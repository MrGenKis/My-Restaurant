package com.restaurant.tool.controller;

import com.restaurant.tool.model.Dish;
import com.restaurant.tool.model.RestaurantConfig;
import com.restaurant.tool.service.DishService;
import com.restaurant.tool.service.RestaurantConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/dishes")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RestaurantConfigService restaurantConfigService;

    @GetMapping
    public String listDishes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        RestaurantConfig restaurant = restaurantConfigService.getRestaurantByAdminUsername(username);

        model.addAttribute("dishes", dishService.getDishesForRestaurant(restaurant));
        return "admin/dishes";
    }

    @GetMapping("/new")
    public String showNewDishForm(Model model) {
        model.addAttribute("dish", new Dish());
        return "admin/new-dish";
    }

    @PostMapping
    public String saveDish(@ModelAttribute("dish") Dish dish) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        RestaurantConfig restaurant = restaurantConfigService.getRestaurantByAdminUsername(username);
        dish.setRestaurant(restaurant);

        dishService.saveDish(dish);
        return "redirect:/admin/dishes";
    }

    @GetMapping("/delete/{id}")
    public String deleteDish(@PathVariable Long id) {
        dishService.deleteDish(id);
        return "redirect:/admin/dishes";
    }
}
