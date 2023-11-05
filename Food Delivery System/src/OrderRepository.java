import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private DatabaseManager databaseManager;

    public OrderRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public boolean createOrder(Order order) {
        String sql = "INSERT INTO orders(user_id, restaurant_id, orderTotal) VALUES (?, ?, ?)";
        try {
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, order.getUser_id());
            preparedStatement.setInt(2, order.getRestaurant_id());
            preparedStatement.setDouble(3, order.getOrderTotal());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        try {
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                int restaurantId = resultSet.getInt("restaurant_id");
                Timestamp orderDate = resultSet.getTimestamp("order_date");
                String status = resultSet.getString("orderStatus");
                double total = resultSet.getDouble("orderTotal");

                orders.add(new Order(orderId, userId, restaurantId, orderDate, status, total));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return orders;
    }

    public int saveOrder(Cart cart, int userId, int restaurantId) {
        String orderSql = "INSERT INTO orders (user_id, restaurant_id, orderTotal) VALUES (?, ?, ?)";
        String orderDetailSql = "INSERT INTO order_details (order_id, item_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection connection = databaseManager.getConnection();
             PreparedStatement orderStmt = connection.prepareStatement(orderSql)) {

            orderStmt.setInt(1, userId);
            orderStmt.setInt(2, restaurantId);
            orderStmt.setDouble(3, cart.getTotalPrice());
            orderStmt.executeUpdate();

            ResultSet generatedKeys = orderStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);

                try (PreparedStatement orderDetailStmt = connection.prepareStatement(orderDetailSql)) {
                    for (CartItem item : cart.getItems()) {
                        orderDetailStmt.setInt(1, orderId);
                        orderDetailStmt.setInt(2, item.getMeal().getItemId());
                        orderDetailStmt.setInt(3, item.getQuantity());
                        orderDetailStmt.setDouble(4, item.getMeal().getPrice());
                        orderDetailStmt.executeUpdate();
                    }
                }

                return orderId;
            } else {
                System.out.println("Creating order failed, no ID obtained.");
                return -1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }


}
