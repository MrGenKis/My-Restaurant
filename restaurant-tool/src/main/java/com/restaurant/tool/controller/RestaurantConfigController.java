package com.restaurant.tool.controller;

import com.restaurant.tool.model.Admin;
import com.restaurant.tool.model.RestaurantConfig;
import com.restaurant.tool.service.AdminService;
import com.restaurant.tool.service.RestaurantConfigService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class RestaurantConfigController {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantConfigController.class);

    @Autowired
    private RestaurantConfigService configService;

    @Autowired
    private AdminService adminService;

    private final String uploadDirectory = "uploads/";

    @GetMapping("/config/redirect")
    public String redirectToConfigPageIfNeeded() {
        logger.info("Redirection vers la page d'accueil ou config...");
        if (!configService.isConfigAvailable()) {
            logger.info("La configuration n'est pas encore disponible. Redirection vers /config");
            return "redirect:/config";
        }
        logger.info("Configuration déjà effectuée. Redirection vers la page d'accueil.");
        return "home";
    }

    @GetMapping("/config")
    public String showConfigPage(Model model) {
        logger.info("Affichage de la page de configuration");
        model.addAttribute("config", new RestaurantConfig());
        return "config";
    }

  @PostMapping("/config")
public String saveConfig(@Valid @ModelAttribute("config") RestaurantConfig config,
                         BindingResult result,
                         @RequestParam("adminUsername") String adminUsername,
                         @RequestParam("adminPassword") String adminPassword) {
    logger.info("Soumission du formulaire avec les données : {}", config);

    if (result.hasErrors()) {
        logger.error("Erreurs de validation : {}", result.getAllErrors());
        return "config";
    }

    MultipartFile bannerImageFile = config.getBannerImageFile();
    if (bannerImageFile != null && !bannerImageFile.isEmpty()) {
        logger.info("Fichier de bannière détecté : {}", bannerImageFile.getOriginalFilename());
        try {
            String fileName = bannerImageFile.getOriginalFilename();
            Path filePath = Paths.get(uploadDirectory + fileName);

            if (Files.exists(filePath)) {
                String newFileName = System.currentTimeMillis() + "_" + fileName;
                filePath = Paths.get(uploadDirectory + newFileName);
                logger.info("Le fichier existe déjà. Nouveau nom : {}", newFileName);
            }

            Files.createDirectories(filePath.getParent());

            Files.copy(bannerImageFile.getInputStream(), filePath);
            logger.info("Image de bannière sauvegardée à : {}", filePath.toString());

            config.setBannerImagePath(filePath.toString());
        } catch (IOException e) {
            logger.error("Erreur lors de l'upload de l'image : ", e);
            result.rejectValue("bannerImageFile", "error.bannerImageFile", "Erreur lors de l'upload de l'image");
            return "config";
        }
    }

    Admin admin = new Admin();
    admin.setUsername(adminUsername);
    admin.setPassword(adminPassword); 
    config.setAdmin(admin); 

    logger.info("Sauvegarde de l'admin et de la configuration du restaurant en base de données");
    adminService.saveAdmin(admin); 
    configService.saveConfig(config); 

    logger.info("Configuration et admin sauvegardés avec succès.");
    return "redirect:/login"; 
}

}
