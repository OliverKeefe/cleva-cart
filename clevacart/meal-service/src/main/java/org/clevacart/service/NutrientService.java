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
import org.clevacart.entity.NutrientEntity;
import org.clevacart.entity.RecipeEntity;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class NutrientService extends BaseService<NutrientEntity> {

    @Inject
    EntityManager entityManager;

    public JsonObject getAll() {
        List<NutrientEntity> nutrients = findAllByEntity(NutrientEntity.class);

        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (NutrientEntity nutrient : nutrients) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder()
                    .add("id", nutrient.getId())
                    .add("name", nutrient.getName());
            jsonArrayBuilder.add(jsonObjectBuilder);
        }

        try {
            return createJson(
                    Json.createObjectBuilder()
                            .add("nutrients", jsonArrayBuilder));
        } catch (NoResultException e) {
            return createJsonError("Nutrient not found.");
        }
    }

    public JsonObject getById(int id) {
        NutrientEntity nutrient = findEntityById(NutrientEntity.class, id);
        if (nutrient != null) {
            return createJson(
                    Json.createObjectBuilder()
                        .add("id", nutrient.getId())
                        .add("name", nutrient.getName())
            );

        } else {
            return createJsonError("Nutrient not found");
        }
    }

    public JsonObject getByName(String name) {
        Optional<NutrientEntity> nutrientEntityOptional = findEntityByField(NutrientEntity.class, "name", name);

        if (nutrientEntityOptional.isPresent()) {
            NutrientEntity nutrient = nutrientEntityOptional.get();

            try {
                return createJson(
                        Json.createObjectBuilder()
                                .add("id", nutrient.getId())
                                .add("name", nutrient.getName())
                );
            } catch (Error e) {
                return createJsonError("Nutrient exists in database but could not get.");
            }
        } else {
                return createJsonError("Nutrient not found");
            }
    }

    public int deleteByName(String name) {
        return deleteByField(NutrientEntity.class, "name", name);
    }
}
