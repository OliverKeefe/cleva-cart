package org.clevacart.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.clevacart.entity.AllergenEntity;

import java.util.List;

@ApplicationScoped
public class AllergenService extends BaseService<AllergenEntity> {

    @Override
    public JsonObject getAll() {
        List<AllergenEntity> allergens = entityManager.createQuery("SELECT a FROM AllergenEntity a", AllergenEntity.class)
                .getResultList();

        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (AllergenEntity allergen : allergens) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder()
                    .add("id", allergen.getId())
                    .add("name", allergen.getName() != null ? allergen.getName() : "Unknown");
            jsonArrayBuilder.add(jsonObjectBuilder);
        }

        return Json.createObjectBuilder()
                .add("allergens", jsonArrayBuilder)
                .build();
    }

    @Override
    public JsonObject getById(int id) {
        AllergenEntity allergen = findEntityById(AllergenEntity.class, id);
        if (allergen != null) {
            return createJson(
                    Json.createObjectBuilder()
                            .add("id", allergen.getId())
                            .add("name", allergen.getName())
            );
        } else {
            return createJsonError("Allergen not found").build();
        }
    }

    @Override
    public JsonObject getByName(String name) {
        try {
            AllergenEntity allergen = findEntityByField(AllergenEntity.class, "name", name);
            return createJson(
                    Json.createObjectBuilder()
                            .add("id", allergen.getId())
                            .add("name", allergen.getName())
            );
        } catch (NoResultException e) {
            return createJsonError("Allergen not found").build();
        }
    }
}
