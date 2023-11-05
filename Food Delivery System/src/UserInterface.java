import java.util.List;
import java.util.Scanner;

public class UserInterface {
        private CartService cartService;
        private MealService mealService;
        private RestaurantService restaurantService;
        private MenuService menuService;
        private SectionService sectionService;
        private OrderService orderService;
        private Scanner scanner;

        public UserInterface(CartService cartService, MealService mealService, RestaurantService restaurantService, MenuService menuService, SectionService sectionService,OrderService orderService) {
            this.cartService = cartService;
            this.mealService = mealService;
            this.restaurantService = restaurantService;
            this.menuService = menuService;
            this.sectionService = sectionService;
            this.orderService = orderService;
            this.scanner = new Scanner(System.in);
        }

        public void startShopping() {
            boolean keepShopping = true;
            while (keepShopping) {
                System.out.println("1. View restaurants");
                System.out.println("2. View cart");
                System.out.println("3. Finalize order");
                System.out.println("4. Exit");

                if(scanner.hasNextInt()){
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                        case 1:
                            displayRestaurants();
                            break;
                        case 2:
                            cartService.displayCart();
                            break;
                        case 3:
                            orderService.finalizeOrder();
                            break;
                        case 4:
                            keepShopping = false;
                            break;
                    }
                }else {
                    System.out.println("Invalid input.Please enter a number from 1 to 4.");
                    scanner.next();
                }
            }
        }

        private void displayRestaurants() {
            List<Restaurant> restaurants = restaurantService.displayRestaurants();
            System.out.println("Enter the number of the restaurant to view details or 0 to go back.");
            int choice;
            do {
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input.Please enter a number.");
                    scanner.next();
                }
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice < 0 || choice > restaurants.size()) {
                    System.out.println("Invalid choice. Please select a valid number.");
                } else if (choice > 0) {
                    int restaurantId = restaurants.get(choice - 1).getRestaurant_id();
                    displayMenusForRestaurant(restaurantId);
                }else
                    startShopping();
            } while (choice != 0);
        }



        private void displayMenusForRestaurant(int restaurantId) {
            List<Menu> menus = menuService.displayMenusForRestaurant(restaurantId);
            System.out.println("\nSelect a menu to view its sections or type 0 to go back.");
            int choice;
            do {
                while(!scanner.hasNextInt()){
                    System.out.println("Invalid input. Please enter a number");
                    scanner.next();
                }
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 0) {
                    displayRestaurants();
                }
                else if (choice > 0 && choice <= menus.size()) {
                    int menuId = menus.get(choice - 1).getMenu_id();
                    displaySectionsForMenu(menuId, restaurantId);
                } else {
                    System.out.println("Invalid choice. Please select a valid option.");
                }
            }while (true);

            }

        private void displaySectionsForMenu(int menuId,int restaurantId) {
            List<Section> sections = sectionService.displaySectionsForMenu(menuId);
            System.out.println("\nSelect a section by number or type 0 to go back.");
            int choice;
            do {
                while(!scanner.hasNextInt()){
                    System.out.println("Invalid input.Please enter a number");
                    scanner.next();
                }

                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 0) {
                    displayMenusForRestaurant(restaurantId);
                }
                if (choice > 0 && choice <= sections.size()) {
                    int sectionId = sections.get(choice - 1).getSection_id();
                    displayMealsForSection(sectionId, menuId);
                } else {
                    System.out.println("Invalid choice. Please select a valid option.");
                }
            }while(true);
        }

        private void displayMealsForSection(int sectionId,int menuId) {
            List<Meal> meals = mealService.getMealsForSection(sectionId);
            handleMealSelection(meals,menuId,sectionId);
        }


    private void handleMealSelection(List<Meal> meals, int menuId, int sectionId) {
        int choice;
        do {
            System.out.println("Enter meal number to add to cart, 0 to finalize or -1 to go back.");
            while(!scanner.hasNextInt()){
                System.out.println("Invalid input.Please enter a number");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice > 0 && choice <= meals.size()) {
                Meal selectedMeal = meals.get(choice - 1);
                int quantity;
                System.out.println("How many quantities of " + selectedMeal.getDescription() + " would you like to add to your cart?");
                while(!scanner.hasNextInt()){
                    System.out.println("Invalid input.Please enter a number");
                    scanner.nextLine();
                }
                quantity = scanner.nextInt();
                scanner.nextLine();
                cartService.addItemToCart(selectedMeal, quantity);
                System.out.println(quantity + " x " + selectedMeal.getDescription() + " has been added to the cart!");
            } else if (choice == -1) {
                displaySectionsForMenu(menuId, sectionId);
                return;
            } else if (choice == 0) {
                orderService.finalizeOrder();
                System.out.println("Your order has been finalized.");
                return;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

    }

}