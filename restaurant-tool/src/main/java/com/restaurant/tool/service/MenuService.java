package com.restaurant.tool.service;

import com.restaurant.tool.model.Menu;
import com.restaurant.tool.model.RestaurantConfig;
import com.restaurant.tool.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> getMenusForRestaurant(RestaurantConfig restaurant) {
        return menuRepository.findByRestaurant(restaurant);
    }

    public Menu getMenuById(Long id) {
        return menuRepository.findById(id).orElse(null);
    }

    public Menu saveMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }

  public void updateMenu(Long id, Menu updatedMenu) {
    Menu existingMenu = menuRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("Menu non trouvé"));

    existingMenu.setName(updatedMenu.getName());
    existingMenu.setDescription(updatedMenu.getDescription());

    existingMenu.getDishes().clear();  
    existingMenu.getDishes().addAll(updatedMenu.getDishes());  

    menuRepository.save(existingMenu);
}



}
