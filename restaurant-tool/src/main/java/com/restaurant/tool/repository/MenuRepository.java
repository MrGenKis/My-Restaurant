package com.restaurant.tool.repository;

import com.restaurant.tool.model.Menu;
import com.restaurant.tool.model.RestaurantConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    
    List<Menu> findByRestaurant(RestaurantConfig restaurant);
}
