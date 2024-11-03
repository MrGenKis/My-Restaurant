package com.restaurant.tool.service;

import com.restaurant.tool.model.Review;
import com.restaurant.tool.model.RestaurantConfig;
import com.restaurant.tool.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getLatestReviews(RestaurantConfig restaurant) {
        return reviewRepository.findTop4ByRestaurantOrderByDateDesc(restaurant);
    }

    public Review saveReview(Review review) {
        review.setDate(LocalDate.now());  
        return reviewRepository.save(review);
    }
}
