package Setup;

import Database.DatabaseManager;
import Expections.EntityNotFoundException;
import Model.Meal;
import Model.Menu;
import Model.Restaurant;
import Model.Section;
import Repositories.MealRepository;
import Repositories.MenuRepository;
import Repositories.RestaurantRepository;
import Repositories.SectionRepository;

import java.util.List;

public class Runner {

    private final RestaurantRepository restaurantRepo;
    private final MenuRepository menuRepo;
    private final SectionRepository sectionRepo;
    private final MealRepository mealRepo;

    public Runner() {
        DatabaseManager databaseManager = new DatabaseManager();
        this.restaurantRepo = new RestaurantRepository(databaseManager);
        this.menuRepo = new MenuRepository(databaseManager);
        this.sectionRepo = new SectionRepository(databaseManager);
        this.mealRepo = new MealRepository(databaseManager);
    }

    public static void main(String[] args) {

        Runner runner = new Runner();

        runner.createBulgarianRestaurant();
        runner.createKoreanRestaurant();
    }
    public void createBulgarianRestaurant() {
        Restaurant bulgarianRestaurant = createRestaurant("Bulgarian", "123 Sofia", "+123456789");
        Menu bulgarianMenu = createMenuForRestaurant(bulgarianRestaurant, "Traditional Dishes");
        Section salads = createSectionForMenu(bulgarianMenu, "Salads");
        addMealToSection(salads, 4.50, "Shopska Salad");
        addMealToSection(salads,5,"Ovcharska Salad");
        Section mainCourse = createSectionForMenu(bulgarianMenu,"Main.Main Course");
        addMealToSection(mainCourse,10,"Chicken");
        addMealToSection(mainCourse,15,"Beef");

    }

    public void createKoreanRestaurant() {
        Restaurant koreanRestaurant = createRestaurant("Korean", "456 Seoul", "+123456789");
        Menu koreanMenu = createMenuForRestaurant(koreanRestaurant, "Korean Dishes");
        Section rice = createSectionForMenu(koreanMenu,"Rice");
        addMealToSection(rice, 7, "White rice");
        addMealToSection(rice, 8,"Brown rice");
        Section mainCourse = createSectionForMenu(koreanMenu,"Main.Main course");
        addMealToSection(mainCourse,20,"Beef");
        Menu sushiMenu = createMenuForRestaurant(koreanRestaurant,"Sushi");
        Section sushi = createSectionForMenu(sushiMenu,"Sushi");
        addMealToSection(sushi,15,"Fish");
        addMealToSection(sushi,20,"Crab");

    }
    private Restaurant createRestaurant(String name, String address, String contact) {
        Restaurant restaurant = new Restaurant(name, address, contact);
        restaurantRepo.createRestaurant(restaurant);
        int restaurant_id;
        try {
            restaurant_id = restaurantRepo.getRestaurantIdByName(name);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
        restaurant.setRestaurant_id(restaurant_id);
        return restaurant;
    }

    private Menu createMenuForRestaurant(Restaurant restaurant, String menuName) {
        Menu menu = new Menu(restaurant.getRestaurant_id(), menuName);
        menuRepo.createMenu(menu);

        List<Menu> menus = menuRepo.getMenusByRestaurant(restaurant.getRestaurant_id());

        for (Menu m : menus) {
            if (m.getMenuName().equals(menuName)) {
                menu.setMenu_id(m.getMenu_id());
                break;
            }
        }

        return menu;
    }

    private Section createSectionForMenu(Menu menu, String sectionName) {
        Section section = new Section(menu.getMenu_id(), sectionName);
        sectionRepo.createSection(section);

        return section;
    }

    private void addMealToSection(Section section, double price, String description) {
        try {
            int sectionId = sectionRepo.getSectionIdByName(section.getSectionName());
            Meal meal = new Meal.Builder(sectionId).price(price).description(description).build();
            mealRepo.createMeal(meal);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}





