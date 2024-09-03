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

import java.util.List;

@ApplicationScoped
public class NutrientService {

    @Inject
    EntityManager entityManager;

    public JsonObject getAllNutrients() {
        TypedQuery<NutrientEntity> query = entityManager.createQuery("SELECT a FROM NutrientEntity a", NutrientEntity.class);
        List<NutrientEntity> nutrients = query.getResultList();

        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (NutrientEntity nutrient : nutrients) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder()
                    .add("id", nutrient.getId())
                    .add("name", nutrient.getName());
            jsonArrayBuilder.add(jsonObjectBuilder);
        }

        return Json.createObjectBuilder()
                .add("nutrients", jsonArrayBuilder)
                .build();
    }

    public JsonObject getNutrientById(int id) {
        NutrientEntity nutrient = entityManager.find(NutrientEntity.class, id);
        if (nutrient != null) {
            return Json.createObjectBuilder()
                    .add("id", nutrient.getId())
                    .add("name", nutrient.getName())
                    .build();
        } else {
            return Json.createObjectBuilder()
                    .add("error", "Allergen not found")
                    .build();
        }
    }

    public JsonObject getNutrientByName(String name) {
        TypedQuery<NutrientEntity> query = entityManager.createQuery(
                "SELECT a FROM NutrientEntity a WHERE a.name = :name", NutrientEntity.class);
        query.setParameter("name", name);

        try {
            NutrientEntity nutrient = query.getSingleResult();
            return Json.createObjectBuilder()
                    .add("id", nutrient.getId())
                    .add("name", nutrient.getName())
                    .build();
        } catch (NoResultException e) {
            return Json.createObjectBuilder()
                    .add("error", "Nutrient not found")
                    .build();
        }
    }
}
