package com.restaurant.tool.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
@JoinColumn(name = "menu_id")
private List<Dish> dishes = new ArrayList<>();



    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public RestaurantConfig getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantConfig restaurant) {
        this.restaurant = restaurant;
    }
}
