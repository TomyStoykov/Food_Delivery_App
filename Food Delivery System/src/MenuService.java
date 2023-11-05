import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MenuService {
    private MenuRepository menuRepository;
    private Scanner scanner = new Scanner(System.in);
    private SectionService sectionService;

    public MenuService(DatabaseManager databaseManager,SectionService sectionService){
        this.menuRepository = new MenuRepository(databaseManager);
        this.sectionService = sectionService;
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
