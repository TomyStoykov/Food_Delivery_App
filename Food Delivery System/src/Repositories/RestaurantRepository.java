package Repositories;

import Database.DatabaseManager;
import Expections.EntityNotFoundException;
import Model.Restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantRepository {
    private final DatabaseManager databaseManager;

    public RestaurantRepository(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }

    public boolean createRestaurant(Restaurant restaurant){
        String sql = "INSERT INTO restaurants(restaurant_name,address,contactNumber) VALUES (?,?,?)";
        try{
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,restaurant.getRestaurantName());
            preparedStatement.setString(2, restaurant.getAddress());
            preparedStatement.setString(3,restaurant.getContactNumber());

            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Restaurant> getRestaurants(){
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurants";
        try {
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int id = resultSet.getInt("restaurant_id");
                String name = resultSet.getString("restaurant_name");
                String address = resultSet.getString("address");
                String contact = resultSet.getString("contactNumber");
                restaurants.add(new Restaurant(id,name,address,contact));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return restaurants;
    }

    public int getRestaurantIdByName(String restaurantName) throws EntityNotFoundException {
        String sql = "SELECT restaurant_id FROM restaurants WHERE restaurant_name = ?";
        try {
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,restaurantName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("restaurant_id");
            }else {
                throw new EntityNotFoundException("Model.Restaurant with name " +restaurantName +" not found");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            throw new RuntimeException("Database error occurred.", e);
        }
    }
}
