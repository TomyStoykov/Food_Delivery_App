import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailRepository {
    private DatabaseManager databaseManager;

    public OrderDetailRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public boolean addOrderDetail(OrderDetail orderDetail) {
        String sql = "INSERT INTO order_details(order_id, item_id, quantity, price) VALUES (?, ?, ?, ?)";
        try {
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, orderDetail.getOrder_id());
            preparedStatement.setInt(2, orderDetail.getItem_id());
            preparedStatement.setInt(3, orderDetail.getQuantity());
            preparedStatement.setDouble(4, orderDetail.getPrice());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
