package Repositories;

import Database.DatabaseManager;
import Expections.EntityNotFoundException;
import Model.Section;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SectionRepository {
    private final DatabaseManager databaseManager;

    public SectionRepository(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }

    public boolean createSection(Section section){
        String sql = "INSERT INTO menu_sections(menu_id,sectionName) VALUES (?,?)";
        try {
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,section.getMenu_id());
            preparedStatement.setString(2,section.getSectionName());
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public List<Section> getSectionsByMenuId(int menuId) {
        List<Section> sections = new ArrayList<>();
        String sql = "SELECT * FROM menu_sections WHERE menu_id = ?";
        try {
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, menuId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int sectionId = resultSet.getInt("section_id");
                String sectionName = resultSet.getString("sectionName");
                sections.add(new Section(sectionId, menuId, sectionName));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sections;
    }

    public int getSectionIdByName(String sectionName) throws EntityNotFoundException {
        String query = "SELECT section_id FROM menu_sections WHERE sectionName = ?";
        Connection connection = databaseManager.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, sectionName);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("section_id");
            } else {
                throw new EntityNotFoundException("Model.Section with name " + sectionName + " not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
