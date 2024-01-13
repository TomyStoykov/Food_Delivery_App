package Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Database.DatabaseManager;
import Model.Menu;

public class MenuRepository {
    private final DatabaseManager databaseManager;

    public MenuRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public boolean createMenu(Menu menu) {
        String sql = "INSERT INTO restaurant_menus(restaurant_id,menuName) VALUES (?,?)";
        try {
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, menu.getRestaurant_id());
            preparedStatement.setString(2, menu.getMenuName());

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Menu> getMenusByRestaurant(int restaurant_id) {
        List<Menu> menus = new ArrayList<>();
        String sql = "SELECT * FROM restaurant_menus WHERE restaurant_id = ?";
        try {
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, restaurant_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int menuId = resultSet.getInt("menu_id");
                String menuName = resultSet.getString("menuName");
                menus.add(new Menu(menuId, restaurant_id, menuName));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return menus;
    }
}