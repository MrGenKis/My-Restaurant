package com.restaurant.tool.controller;

import com.restaurant.tool.model.Dish;  
import com.restaurant.tool.model.Menu;
import com.restaurant.tool.model.RestaurantConfig;
import com.restaurant.tool.service.MenuService;
import com.restaurant.tool.service.RestaurantConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RestaurantConfigService restaurantConfigService;

    @GetMapping("/menus")
    public String listMenus(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        RestaurantConfig currentRestaurant = restaurantConfigService.getRestaurantByAdminUsername(username);
        
        model.addAttribute("menus", menuService.getMenusForRestaurant(currentRestaurant));
        return "admin/menus";
    }

    @GetMapping
    public String showAdminPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        RestaurantConfig currentRestaurant = restaurantConfigService.getRestaurantByAdminUsername(username);

        model.addAttribute("restaurantName", currentRestaurant.getRestaurantName());

        return "admin/admin";  
    }

    @GetMapping("/menus/new")
    public String showNewMenuForm(Model model) {
        model.addAttribute("menu", new Menu());
        return "admin/new-menu";
    }

    @PostMapping("/menus")
    public String saveMenu(@ModelAttribute("menu") Menu menu) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        RestaurantConfig currentRestaurant = restaurantConfigService.getRestaurantByAdminUsername(username);
        menu.setRestaurant(currentRestaurant);

        for (Dish dish : menu.getDishes()) {
            dish.setRestaurant(currentRestaurant);
        }

        menuService.saveMenu(menu);  
        return "redirect:/admin/menus";
    }

 @GetMapping("/menus/edit/{id}")
    public String showEditMenuForm(@PathVariable Long id, Model model) {
        Menu menu = menuService.getMenuById(id);

        model.addAttribute("menu", menu);
        return "admin/edit-menu";
    }

    @PostMapping("/menus/update/{id}")
    public String updateMenu(@PathVariable Long id, @ModelAttribute("menu") Menu updatedMenu) {
        Menu existingMenu = menuService.getMenuById(id);

        existingMenu.setName(updatedMenu.getName());
        existingMenu.setDescription(updatedMenu.getDescription());

        menuService.saveMenu(existingMenu);

        return "redirect:/admin/menus";
    }



}
