import Model.Meal;
import Model.Menu;
import Model.Restaurant;
import Model.Section;
import Services.*;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
        private final CartService cartService;
        private final MealService mealService;
        private final RestaurantService restaurantService;
        private final MenuService menuService;
        private final SectionService sectionService;
        private final OrderService orderService;
        private final UserService userService;
        private final Scanner scanner;


        public UserInterface(CartService cartService, MealService mealService, RestaurantService restaurantService, MenuService menuService,
                             SectionService sectionService,OrderService orderService, UserService userService) {
            this.cartService = cartService;
            this.mealService = mealService;
            this.restaurantService = restaurantService;
            this.menuService = menuService;
            this.sectionService = sectionService;
            this.orderService = orderService;
            this.userService = userService;
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
                            finalizeOrder();
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
        private void performRegistration() {
            System.out.println("Enter your username: ");
            String username = scanner.nextLine();
            System.out.println("Enter your password: ");
            String password = scanner.nextLine();
            System.out.println("Enter your email: ");
            String email = scanner.nextLine();
            System.out.println("Enter your address: ");
            String address = scanner.nextLine();
            System.out.println("Enter your phone number: ");
            String phoneNumber = scanner.nextLine();

            boolean success = userService.registerUser(username, password, email, address, phoneNumber);
            if (!success) {
                System.out.println("Registration failed. Please try again.");
            }
        }
        private void performLogin() {
            System.out.println("Enter your username: ");
            String username = scanner.nextLine();
            System.out.println("Enter your password: ");
            String password = scanner.nextLine();

            boolean success = userService.loginUser(username, password);
            if (!success) {
                System.out.println("Login failed. Please try again.");
            }
        }
    private void finalizeOrder() {
        if (userService.getLoggedInUser() == null) {
            System.out.println("Please log in to finalize your order.");
            return;
        }
        cartService.displayCart();
        System.out.println("Do you want to finalize your order? (1 for Yes, 0 for No)");
        int confirmation = scanner.nextInt();
        scanner.nextLine();

        if (confirmation == 1) {
            orderService.finalizeOrder(userService.getLoggedInUserId(), cartService.getCurrentRestaurantId());
        } else {
            System.out.println("Order was not placed.");
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
                    displayMealsForSection(sectionId, menuId,restaurantId);
                } else {
                    System.out.println("Invalid choice. Please select a valid option.");
                }
            }while(true);
        }

        private void displayMealsForSection(int sectionId,int menuId,int restaurantId) {
            List<Meal> meals = mealService.getMealsForSection(sectionId);
            handleMealSelection(meals,menuId,sectionId,restaurantId);
        }


    private void handleMealSelection(List<Meal> meals, int menuId, int sectionId,int restaurantId) {
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
                cartService.addItemToCart(selectedMeal, quantity,restaurantId);
                System.out.println(quantity + " x " + selectedMeal.getDescription() + " has been added to the cart!");
            } else if (choice == -1) {
                displaySectionsForMenu(menuId, sectionId);
                return;
            } else if (choice == 0) {
                finalizeOrder();
                System.out.println("Your order has been finalized.");
                return;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

    }


}