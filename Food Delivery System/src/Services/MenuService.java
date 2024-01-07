package Services;

import Database.DatabaseManager;
import Model.Menu;
import Repositories.MenuRepository;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MenuService {
    private final MenuRepository menuRepository;

    public MenuService(DatabaseManager databaseManager){
        this.menuRepository = new MenuRepository(databaseManager);
    }

    public List<Menu> displayMenusForRestaurant(int restaurantId) {
        List<Menu> menus = menuRepository.getMenusByRestaurant(restaurantId);
        if (menus.isEmpty()) {
            System.out.println("No menus available for this restaurant at the moment.");
            return Collections.emptyList();
        }

        System.out.println("Available menus:");
        for (int i = 0; i < menus.size(); i++) {
            System.out.println((i + 1) + ". " + menus.get(i).getMenuName());
        }
        return menus;
    }
}
