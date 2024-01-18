package Services;

import Model.Cart;
import Model.Meal;
import Model.Order;
import Model.OrderDetail;
import Repositories.MealRepository;
import Repositories.OrderDetailRepository;
import Repositories.OrderRepository;

import java.util.List;

public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final MealRepository mealRepository;
    private final CartService cartService;


    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, MealRepository mealRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.mealRepository = mealRepository;
        this.cartService = cartService;

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

    public void finalizeOrder(int userId, int restaurantId) {
        System.out.println("Total: $" + cartService.getCartTotal());
         Cart cart = cartService.getCart();
            int orderId = orderRepository.saveOrder(cart,userId,restaurantId);
            if(orderId != -1){
                System.out.println("Order finalized. Order ID: " + orderId);
                cart.clear();
            }else{
                System.out.println("Order was not placed");
            }
        }

    public List<Order> getPastUserOrders(int userId) {
        return orderRepository.getOrdersByUserId(userId);
    }
}