public class CartService {
    private Cart cart;

    public CartService() {
        this.cart = new Cart();
    }

    public void addItemToCart(Meal meal, int quantity) {
        cart.addMeal(meal, quantity);
    }

    public double getCartTotal() {
        return cart.getTotalPrice();
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
