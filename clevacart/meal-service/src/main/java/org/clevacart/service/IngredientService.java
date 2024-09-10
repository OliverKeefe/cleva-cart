package org.clevacart.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.clevacart.entity.IngredientEntity;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class IngredientService extends BaseService<IngredientEntity>{

    @Inject
    EntityManager entityManager;

    public JsonObject getAll() {
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

    public JsonObject getById(int id) {
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

    public JsonObject getByName(String name) {
        Optional<IngredientEntity> ingredientEntityOptional = findSingleEntityByField(IngredientEntity.class, "name", name);

        if (ingredientEntityOptional.isPresent()) {
            IngredientEntity ingredient = ingredientEntityOptional.get();

            try {
                return createJson(
                        Json.createObjectBuilder()
                                .add("id", ingredient.getId())
                                .add("name", ingredient.getName())
                );
            } catch (Error e) {
                return createJsonError(String.valueOf(e));
            }
        } else {
            return createJsonError("Ingredient not found");
        }
    }

    public int deleteByName(String name) {
        return deleteByField(IngredientEntity.class, "name", name);
    }

    @Transactional
    public void addIngredient(String name) {
    }
}
