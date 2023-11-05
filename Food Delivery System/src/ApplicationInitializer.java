public class ApplicationInitializer {
    public UserInterface initialize(){
        String dbPath = "C:\\Users\\user\\Desktop\\db\\db.sqlite";
        DatabaseManager databaseManager = new DatabaseManager(dbPath);
        Cart cart = new Cart();

        MealRepository mealRepository = new MealRepository(databaseManager);
        SectionRepository sectionRepository = new SectionRepository(databaseManager);
        MenuRepository menuRepository = new MenuRepository(databaseManager);
        OrderDetailRepository orderDetailRepository = new OrderDetailRepository(databaseManager);
        RestaurantRepository restaurantRepository = new RestaurantRepository(databaseManager);
        OrderRepository orderRepository = new OrderRepository(databaseManager);

        MealService mealService = new MealService(mealRepository, sectionRepository,new CartService());
        RestaurantService restaurantService = new RestaurantService(databaseManager);
        SectionService sectionService = new SectionService(databaseManager,mealService);
        MenuService menuService = new MenuService(databaseManager,sectionService);
        CartService cartService = new CartService();
        OrderService orderService = new OrderService(orderRepository,orderDetailRepository,mealRepository,cartService,cart);

        return new UserInterface(cartService, mealService, restaurantService, menuService, sectionService,orderService);
    }
}
