package com.restaurant.tool.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Le nom ne peut pas être vide")
    private String name;

    @Email(message = "Email invalide")
    private String email;  

    private LocalDate date;  

    @Min(value = 1, message = "La note doit être d'au moins 1 étoile")
    @Max(value = 5, message = "La note ne peut pas dépasser 5 étoiles")
    private int rating;  

    private String comment;  

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantConfig restaurant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public RestaurantConfig getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantConfig restaurant) {
        this.restaurant = restaurant;
    }
}
