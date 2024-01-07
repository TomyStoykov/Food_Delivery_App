package Repositories;

import Database.DatabaseManager;
import Model.Meal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MealRepository {
    private final DatabaseManager databaseManager;


    public MealRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;

    }

    public boolean createMeal(Meal meal) {
        String sql = "INSERT INTO menu_items(section_id, price, description) VALUES (?, ?, ?)";
        try {
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, meal.getSectionId());
            preparedStatement.setDouble(2, meal.getPrice());
            preparedStatement.setString(3, meal.getDescription());

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Meal> getMealsBySectionId(int sectionId) {
        List<Meal> meals = new ArrayList<>();
        String sql = "SELECT * FROM menu_items WHERE section_id = ?";
        try {
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, sectionId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int itemId = resultSet.getInt("item_id");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");
                meals.add(new Meal(itemId, sectionId, price, description));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return meals;
    }

    public Meal getMealById(int itemId) {
        String sql = "SELECT * FROM menu_items WHERE item_id = ?";
        try {
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, itemId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int sectionId = resultSet.getInt("section_id");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");
                return new Meal(itemId, sectionId, price, description);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean mealExists(int sectionId, String description) {
        String sql = "SELECT COUNT(*) FROM menu_items WHERE section_id = ? AND description = ?";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, sectionId);
            preparedStatement.setString(2, description);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
