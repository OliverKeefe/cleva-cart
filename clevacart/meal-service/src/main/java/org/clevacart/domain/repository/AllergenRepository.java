package org.clevacart.domain.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.clevacart.config.DatabaseConfig;
import org.clevacart.domain.model.Allergen;
import org.clevacart.service.DatabaseService;

@ApplicationScoped
public class AllergenRepository {

    @Inject
    DatabaseService databaseService;

    @Inject
    DatabaseConfig.DatabaseConnectionProperties properties;

    public List<String> selectAllFromAllergens() {
        List<String> allergens = new ArrayList<>();

        try (ResultSet resultSet = databaseService.executeQuery("SELECT * FROM Allergen;", properties)) {
            while (resultSet.next()) {
                String allergenName = resultSet.getString("name");
                allergens.add(allergenName);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get allergens from the database", e);
        }

        return allergens;
    }

    public Allergen getAllergensById(int id) {
        Allergen allergen = null;

        try (ResultSet resultSet = databaseService.executeQuery("SELECT name FROM Allergen WHERE id = ?;", properties, id)) {
            while (resultSet.next()) {
                //allergen = resultSet.getString("name");
                allergen = new Allergen(id, resultSet.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get allergen by specified id from the database", e);
        }
        return allergen;
    }

    @Override
    public String toString() {
        List<String> allergens = (List<String>) selectAllFromAllergens();
        return String.join(", ", allergens);
    }
}
