import java.util.List;
import java.util.Scanner;

public class OrderService {
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private MealRepository mealRepository;
    private CartService cartService;
    private Cart cart;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, MealRepository mealRepository, CartService cartService, Cart cart) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.mealRepository = mealRepository;
        this.cartService = cartService;
        this.cart = cart;
    }

    public boolean addItemToOrder(int orderId, int itemId, int quantity) {
        Meal meal = mealRepository.getMealById(itemId);
        if (meal != null) {
            double price = meal.getPrice() * quantity;
            OrderDetail orderDetail = new OrderDetail(orderId, itemId, quantity, price);
            return orderDetailRepository.addOrderDetail(orderDetail);
        }
        return false;
    }

    public void finalizeOrder() {
        Scanner scanner = new Scanner(System.in);
        cartService.displayCart();

        System.out.println("Total: $" + cartService.getCartTotal());

        System.out.println("Do you want to finalize your order? (1 for Yes, 0 for No)");
        int choice = scanner.nextInt();

        if (choice == 1) {
            //saveOderMethod
            cart.clear();
        } else {
            System.out.println("Order was not placed.");
        }
    }

    public List<Order> getPastUserOrders(int userId) {
        return orderRepository.getOrdersByUserId(userId);
    }
}