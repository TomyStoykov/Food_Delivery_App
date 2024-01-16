import Database.DatabaseManager;
import Model.Cart;
import Repositories.*;
import Services.*;

public class ApplicationInitializer {
    public UserInterface initialize(){
        DatabaseManager databaseManager = new DatabaseManager();

        MealRepository mealRepository = new MealRepository(databaseManager);
        SectionRepository sectionRepository = new SectionRepository(databaseManager);
        MenuRepository menuRepository = new MenuRepository(databaseManager);
        OrderDetailRepository orderDetailRepository = new OrderDetailRepository(databaseManager);
        RestaurantRepository restaurantRepository = new RestaurantRepository(databaseManager);
        OrderRepository orderRepository = new OrderRepository(databaseManager);
        UserRepository userRepository = new UserRepository(databaseManager);

        MealService mealService = new MealService(mealRepository);
        RestaurantService restaurantService = new RestaurantService(databaseManager);
        SectionService sectionService = new SectionService(databaseManager);
        MenuService menuService = new MenuService(databaseManager);
        CartService cartService = new CartService();
        OrderService orderService = new OrderService(orderRepository,orderDetailRepository,mealRepository,cartService);
        UserService userService = new UserService(userRepository);

        return new UserInterface(cartService, mealService, restaurantService, menuService, sectionService,orderService,userService);
    }
}
