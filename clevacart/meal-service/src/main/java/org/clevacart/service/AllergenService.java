package org.clevacart.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import org.clevacart.entity.AllergenEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class AllergenService extends BaseService<AllergenEntity> {

    @Override
    public JsonObject getAll() {
        List<AllergenEntity> allergens = findAllByEntity(AllergenEntity.class);

        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (AllergenEntity allergen : allergens) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder()
                    .add("id", allergen.getId())
                    .add("name", allergen.getName() != null ? allergen.getName() : "Unknown");
            jsonArrayBuilder.add(jsonObjectBuilder);
        }

        return createJson(
                Json.createObjectBuilder()
                        .add("allergens", jsonArrayBuilder)
                );
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
            return createJsonError("Allergen not found");
        }
    }

    @Override
    public JsonObject getByName(String name) {
        Optional<AllergenEntity> allergenEntityOptional = findSingleEntityByField(AllergenEntity.class, "name", name);
        if (allergenEntityOptional.isPresent()) {
            AllergenEntity allergen = allergenEntityOptional.get();

            return createJson(
                    Json.createObjectBuilder()
                            .add("id", allergen.getId())
                            .add("name", allergen.getName())
            );
        } else {
            return createJsonError("Allergen not found");
        }
    }

}
