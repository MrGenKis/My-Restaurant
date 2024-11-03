package com.restaurant.tool.service;

import com.restaurant.tool.model.Dish;
import com.restaurant.tool.model.RestaurantConfig;
import com.restaurant.tool.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    public List<Dish> getDishesForRestaurant(RestaurantConfig restaurant) {
        return dishRepository.findByRestaurant(restaurant);
    }

    public Dish saveDish(Dish dish) {
        return dishRepository.save(dish);
    }

    public void deleteDish(Long id) {
        dishRepository.deleteById(id);
    }

    public Dish getDishById(Long id) {
        return dishRepository.findById(id).orElseThrow(() -> new IllegalStateException("Plat non trouvé"));
    }
}
