package org.clevacart.domain.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.clevacart.config.DatabaseConfig;
import org.clevacart.service.DatabaseService;

public class DatabaseTest {
    public static String result;

    public static String selectAllFromAllergens(DatabaseService databaseService, DatabaseConfig.DatabaseConnectionProperties properties) {
        StringBuilder result = new StringBuilder();

        try (Connection connection = databaseService.getConnection(properties);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Allergen;")) {

            while (resultSet.next()) {
                // Assuming there's a column called "name" in your Allergens table
                String allergenName = resultSet.getString("name");
                result.append("Allergen: ").append(allergenName).append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }

        return result.toString();
    }
}
