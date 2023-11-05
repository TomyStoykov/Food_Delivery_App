import java.util.List;

public class Runner {

    private final DatabaseManager databaseManager;
    private final RestaurantRepository restaurantRepo;
    private final MenuRepository menuRepo;
    private final SectionRepository sectionRepo;
    private final MealRepository mealRepo;

    public Runner(String dbPath) {
        this.databaseManager = new DatabaseManager(dbPath);
        this.restaurantRepo = new RestaurantRepository(databaseManager);
        this.menuRepo = new MenuRepository(databaseManager);
        this.sectionRepo = new SectionRepository(databaseManager);
        this.mealRepo = new MealRepository(databaseManager);
    }

    public static void main(String[] args) {
        String dbPath = "C:\\Users\\user\\Desktop\\msg db\\db.sqlite";

        Runner runner = new Runner(dbPath);

        runner.createBulgarianRestaurant();
        runner.createKoreanRestaurant();
    }
    public void createBulgarianRestaurant() {
        Restaurant bulgarianRestaurant = createRestaurant("Bulgarian", "123 Sofia", "+123456789");
        Menu bulgarianMenu = createMenuForRestaurant(bulgarianRestaurant, "Traditional Dishes");
        Section salads = createSectionForMenu(bulgarianMenu, "Salads");
        addMealToSection(salads, 4.50, "Shopska Salad");
        addMealToSection(salads,5,"Ovcharska Salad");
        Section mainCourse = createSectionForMenu(bulgarianMenu,"Main Course");
        addMealToSection(mainCourse,10,"Chicken");
        addMealToSection(mainCourse,15,"Beef");

    }

    public void createKoreanRestaurant() {
        Restaurant koreanRestaurant = createRestaurant("Korean", "456 Seoul", "+123456789");
        Menu koreanMenu = createMenuForRestaurant(koreanRestaurant, "Korean Dishes");
        Section rice = createSectionForMenu(koreanMenu,"Rice");
        addMealToSection(rice, 7, "White rice");
        addMealToSection(rice, 8,"Brown rice");
        Section mainCourse = createSectionForMenu(koreanMenu,"Main course");
        addMealToSection(mainCourse,20,"Beef");
        Menu sushiMenu = createMenuForRestaurant(koreanRestaurant,"Sushi");
        Section sushi = createSectionForMenu(sushiMenu,"Sushi");
        addMealToSection(sushi,15,"Fish");
        addMealToSection(sushi,20,"Crab");

    }
    private Restaurant createRestaurant(String name, String address, String contact) {
        Restaurant restaurant = new Restaurant(name, address, contact);
        restaurantRepo.createRestaurant(restaurant);
        int restaurant_id = 0;
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

        // Retrieve the list of menus for the restaurant
        List<Menu> menus = menuRepo.getMenusByRestaurant(restaurant.getRestaurant_id());

        // Search for the menu with the specified name to get its ID
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





