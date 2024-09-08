package org.clevacart.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.clevacart.entity.IngredientEntity;

import java.util.List;

@ApplicationScoped
public class IngredientService {

    @Inject
    EntityManager entityManager;

    public JsonObject getAllIngredients() {
        TypedQuery<IngredientEntity> query = entityManager.createQuery("SELECT a FROM IngredientEntity a", IngredientEntity.class);
        List<IngredientEntity> ingredients = query.getResultList();

        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (IngredientEntity ingredient : ingredients) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder()
                    .add("id", ingredient.getId())
                    .add("name", ingredient.getName());
            jsonArrayBuilder.add(jsonObjectBuilder);
        }

        return Json.createObjectBuilder()
                .add("allergens", jsonArrayBuilder)
                .build();
    }

    public JsonObject getIngredientById(int id) {
        IngredientEntity ingredient = entityManager.find(IngredientEntity.class, id);
        if (ingredient != null) {
            return Json.createObjectBuilder()
                    .add("id", ingredient.getId())
                    .add("name", ingredient.getName())
                    .build();
        } else {
            return Json.createObjectBuilder()
                    .add("error", "Allergen not found")
                    .build();
        }
    }

    public JsonObject getIngredientByName(String name) {
        TypedQuery<IngredientEntity> query = entityManager.createQuery(
                "SELECT a FROM IngredientEntity a WHERE a.name = :name", IngredientEntity.class);
        query.setParameter("name", name);

        try {
            IngredientEntity ingredient = query.getSingleResult();
            return Json.createObjectBuilder()
                    .add("id", ingredient.getId())
                    .add("name", ingredient.getName())
                    .build();
        } catch (NoResultException e) {
            return Json.createObjectBuilder()
                    .add("error", "Ingredient not found")
                    .build();
        }
    }

    public void addIngredient(String name) {

    }
}
