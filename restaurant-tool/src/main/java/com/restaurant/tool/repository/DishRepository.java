package com.restaurant.tool.repository;

import com.restaurant.tool.model.Dish;
import com.restaurant.tool.model.RestaurantConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findByRestaurant(RestaurantConfig restaurant);
}
