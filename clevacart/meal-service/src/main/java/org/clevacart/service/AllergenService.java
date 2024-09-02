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
import org.clevacart.entity.AllergenEntity;

import java.util.List;

@ApplicationScoped
public class AllergenService {

    @Inject
    EntityManager entityManager;

    public JsonObject getAllAllergens() {
        TypedQuery<AllergenEntity> query = entityManager.createQuery("SELECT a FROM AllergenEntity a", AllergenEntity.class);
        List<AllergenEntity> allergens = query.getResultList();

        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (AllergenEntity allergen : allergens) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder()
                    .add("id", allergen.getId())
                    .add("name", allergen.getName());
            jsonArrayBuilder.add(jsonObjectBuilder);
        }

        return Json.createObjectBuilder()
                .add("allergens", jsonArrayBuilder)
                .build();
    }

    public JsonObject getAllergenById(int id) {
        AllergenEntity allergen = entityManager.find(AllergenEntity.class, id);
        if (allergen != null) {
            return Json.createObjectBuilder()
                    .add("id", allergen.getId())
                    .add("name", allergen.getName())
                    .build();
        } else {
            return Json.createObjectBuilder()
                    .add("error", "Allergen not found")
                    .build();
        }
    }

    public JsonObject getAllergenByName(String name) {
        TypedQuery<AllergenEntity> query = entityManager.createQuery(
                "SELECT a FROM AllergenEntity a WHERE a.name = :name", AllergenEntity.class);
        query.setParameter("name", name);

        try {
            AllergenEntity allergen = query.getSingleResult();
            return Json.createObjectBuilder()
                    .add("id", allergen.getId())
                    .add("name", allergen.getName())
                    .build();
        } catch (NoResultException e) {
            return Json.createObjectBuilder()
                    .add("error", "Allergen not found")
                    .build();
        }
    }
}
