package com.restaurant.tool.service;

import com.restaurant.tool.model.RestaurantConfig;
import com.restaurant.tool.repository.RestaurantConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantConfigService {

    @Autowired
    private RestaurantConfigRepository configRepository;

    // Méthode pour récupérer le restaurant en fonction de l'utilisateur admin
    public RestaurantConfig getRestaurantByAdminUsername(String username) {
        return configRepository.findByAdminUsername(username);
    }

    // Méthode pour obtenir tous les restaurants
    public List<RestaurantConfig> getAllRestaurants() {
        return configRepository.findAll();
    }

    // Méthode pour obtenir un restaurant par ID
    public RestaurantConfig getRestaurantById(Long id) {
        return configRepository.findById(id).orElse(null);
    }

    // Méthode pour récupérer la configuration actuelle du restaurant
    public RestaurantConfig getCurrentRestaurant() {
        return configRepository.findById(1L)
            .orElseThrow(() -> new IllegalStateException("Configuration du restaurant non trouvée"));
    }

    // Méthode pour obtenir la configuration optionnelle
    public Optional<RestaurantConfig> getConfig() {
        return configRepository.findById(1L);
    }

    // Méthode pour sauvegarder la configuration du restaurant
    public RestaurantConfig saveConfig(RestaurantConfig config) {
        return configRepository.save(config);
    }

    // Méthode pour vérifier si la configuration est disponible
    public boolean isConfigAvailable() {
        return configRepository.existsById(1L);
    }
}
