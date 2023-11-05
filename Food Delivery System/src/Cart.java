import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addMeal(Meal meal, int quantity) {
        for (CartItem item : items) {
            if (item.getMeal().getItemId() == meal.getItemId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(meal, quantity));
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getMeal().getPrice() * item.getQuantity();
        }
        return total;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
    public void clear() {
        items.clear();
    }
}

class CartItem {
    private Meal meal;
    private int quantity;

    public CartItem(Meal meal, int quantity) {
        this.meal = meal;
        this.quantity = quantity;
    }


    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}


