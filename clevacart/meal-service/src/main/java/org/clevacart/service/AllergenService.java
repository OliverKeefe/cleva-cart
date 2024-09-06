package org.clevacart.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.NoResultException;
import org.clevacart.entity.AllergenEntity;
import org.clevacart.entity.RecipeEntity;

import java.util.List;
import java.util.Optional;

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
        Optional<AllergenEntity> allergenOpt = findEntityByField(AllergenEntity.class, "ingredients", name);
        if (allergenOpt.isPresent()) {
            AllergenEntity allergen = allergenOpt.get();

            return createJson(
                    Json.createObjectBuilder()
                            .add("id", allergen.getId())
                            .add("name", allergen.getName())
            );
        } else {
            return createJsonError("Allergen not found").build();
        }
    }
}
