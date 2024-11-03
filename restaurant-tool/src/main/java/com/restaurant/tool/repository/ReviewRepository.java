package com.restaurant.tool.repository;

import com.restaurant.tool.model.Review;
import com.restaurant.tool.model.RestaurantConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findTop4ByRestaurantOrderByDateDesc(RestaurantConfig restaurant);
}
