import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class RestaurantService {
    private RestaurantRepository restaurantRepository;
    private Scanner scanner = new Scanner(System.in);

    public RestaurantService(DatabaseManager databaseManager){
        this.restaurantRepository = new RestaurantRepository(databaseManager);
    }

    public List<Restaurant> displayRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.getRestaurants();
        if (restaurants.isEmpty()) {
            System.out.println("No restaurants available at the moment.");
            return Collections.emptyList();
        }

        System.out.println("Available restaurants:");
        for (int i = 0; i < restaurants.size(); i++) {
            System.out.println((i + 1) + ". " + restaurants.get(i).getRestaurantName());
            displayRestaurantDetails(restaurants.get(i));
        }
        return restaurants;
    }

    private void displayRestaurantDetails(Restaurant restaurant) {
        System.out.println("Restaurant Name: " + restaurant.getRestaurantName());
        System.out.println("Address: " + restaurant.getAddress());
        System.out.println("Contact Number: " + restaurant.getContactNumber());
    }
}
