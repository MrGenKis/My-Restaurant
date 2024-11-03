package com.restaurant.tool.controller;

import com.restaurant.tool.model.RestaurantConfig;
import com.restaurant.tool.model.Review;  
import com.restaurant.tool.service.ReviewService;  
import org.springframework.web.bind.annotation.ModelAttribute;  
import org.springframework.web.bind.annotation.PostMapping;  
import com.restaurant.tool.model.Menu;  
import com.restaurant.tool.service.RestaurantConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private ReviewService reviewService;

  @GetMapping("/restaurant/{id}/reviews/new")
public String showReviewForm(@PathVariable("id") Long restaurantId, Model model) {
    RestaurantConfig restaurant = restaurantConfigService.getRestaurantById(restaurantId);
    if (restaurant == null) {
        return "error";  
    }

    model.addAttribute("review", new Review());
    model.addAttribute("restaurant", restaurant);

    return "client/review";  
}


   @PostMapping("/restaurant/{id}/reviews")
public String submitReview(@PathVariable("id") Long restaurantId, 
                           @ModelAttribute("review") Review review) {
    RestaurantConfig restaurant = restaurantConfigService.getRestaurantById(restaurantId);
    if (restaurant == null) {
        return "error";
    }

    review.setRestaurant(restaurant);
    reviewService.saveReview(review);

    return "redirect:/restaurant/" + restaurantId;  
}


    @Autowired
    private RestaurantConfigService restaurantConfigService;

    @GetMapping("/")
    public String showHomePage(Model model) {
        List<RestaurantConfig> restaurants = restaurantConfigService.getAllRestaurants();
        model.addAttribute("restaurants", restaurants);
        return "home";
    }

   @GetMapping("/restaurant/{id}")
public String showRestaurantDetails(@PathVariable Long id, Model model) {
    RestaurantConfig restaurant = restaurantConfigService.getRestaurantById(id);
    if (restaurant == null) {
        return "error";  
    }

    List<Review> reviews = reviewService.getLatestReviews(restaurant);

    
    model.addAttribute("restaurant", restaurant);
    model.addAttribute("menus", restaurant.getMenus());  
    model.addAttribute("reviews", reviews);  

    return "client/home";  
}

}
