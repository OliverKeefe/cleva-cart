package org.clevacart.domain.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.quarkus.arc.All;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.clevacart.config.DatabaseConfig;
import org.clevacart.domain.model.Allergen;
import org.clevacart.service.DatabaseService;
import org.intellij.lang.annotations.Language;
import org.clevacart.domain.model.Allergen;

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
                String allergenId = resultSet.getString("id");
                allergens.add(allergenId);
                allergens.add(allergenName);
                databaseService.closeConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get allergens from the database", e);
        }

        return allergens;
    }

    public Allergen getAllergensById(int id) throws SQLException {
        Allergen allergen;
        System.out.println("ID parameter: " + id);

        try (ResultSet resultSet = databaseService.executeQuery("SELECT id, name FROM Allergen WHERE id = ?;", properties, id)) {
            if (resultSet.next()) {
                allergen = new Allergen();
                allergen.setName(resultSet.getString("name"));
                allergen.setId(id);
                System.out.println(resultSet.getInt("id"));


            } else {
                allergen = null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get allergen by specified name from the database", e);
        } finally {
            databaseService.closeConnection();
        }
        return allergen;
        }

    public Allergen getAllergensByName(String name) throws SQLException {
        Allergen allergen;

        try (ResultSet resultSet = databaseService.executeQuery("SELECT id, name FROM Allergen WHERE name = ?;", properties, name)) {
            if (resultSet.next()) {
                allergen = new Allergen();
                allergen.setName(resultSet.getString("name"));
                allergen.setId(resultSet.getInt("id"));
                System.out.println(resultSet.getInt("id"));
                databaseService.closeConnection();
            } else {
                allergen = null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get allergen by specified name from the database", e);
        } finally {
            databaseService.closeConnection();
        }
        return allergen;
    }

    public Allergen test() throws SQLException {
        Allergen allergen;

        try (ResultSet resultSet = databaseService.executeQuery("SELECT id, name FROM Allergen WHERE id = ?;", properties, 1)) {
            if (resultSet.next()) {
                allergen = new Allergen();
                allergen.setName(resultSet.getString("name"));
                allergen.setId(resultSet.getInt("id"));
                System.out.println(resultSet.getInt("id"));
                databaseService.closeConnection();
            } else {
                allergen = null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get allergen by specified name from the database", e);
        } finally {
            databaseService.closeConnection();
        }
        return allergen;
    }

    @Override
    public String toString() {
        List<String> allergens = (List<String>) selectAllFromAllergens();
        return String.join(", ", allergens);
    }
}