package Services;

import Model.Cart;
import Model.CartItem;
import Model.Meal;

public class CartService {
    private final Cart cart;
    private int currentRestaurantId;

    public CartService() {
        this.cart = new Cart();
    }

    public void addItemToCart(Meal meal, int quantity,int restaurantId) {
        cart.addMeal(meal, quantity);
        this.currentRestaurantId =  restaurantId;
    }

    public double getCartTotal() {
        return cart.getTotalPrice();
    }

    public Cart getCart() {
        return cart;
    }

    public int getCurrentRestaurantId() {
        return currentRestaurantId;
    }

    public void setCurrentRestaurantId(int currentRestaurantId) {
        this.currentRestaurantId = currentRestaurantId;
    }

    public void displayCart() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        for (CartItem item : cart.getItems()) {
            System.out.println(item.getMeal().getDescription() + " - Quantity: " + item.getQuantity() + " - Price: $" + item.getMeal().getPrice());
        }
        System.out.println("Total Price: $" + getCartTotal());
    }
}
