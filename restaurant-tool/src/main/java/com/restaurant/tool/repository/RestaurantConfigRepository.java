package com.restaurant.tool.repository;

import com.restaurant.tool.model.RestaurantConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RestaurantConfigRepository extends JpaRepository<RestaurantConfig, Long> {
    
    @Query("SELECT r FROM RestaurantConfig r WHERE r.admin.username = :username")
    RestaurantConfig findByAdminUsername(String username);
}
