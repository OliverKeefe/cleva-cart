package org.clevacart.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.clevacart.entity.IngredientEntity;
import org.clevacart.entity.RecipeEntity;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RecipeService extends BaseService<RecipeEntity> {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public JsonObject getAll() {
        List<RecipeEntity> recipes = entityManager.createQuery("SELECT a FROM RecipeEntity a", RecipeEntity.class)
                .getResultList();

        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (RecipeEntity recipe : recipes) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder()
                    .add("id", recipe.getId())
                    .add("name", recipe.getName() != null ? recipe.getName() : "Unknown");
            jsonArrayBuilder.add(jsonObjectBuilder);
        }

        return Json.createObjectBuilder()
                .add("recipes", jsonArrayBuilder)
                .build();
    }

    @Override
    public JsonObject getById(int id) {
        RecipeEntity recipe = findEntityById(RecipeEntity.class, id);
        if (recipe != null) {
            return createJson(
                    Json.createObjectBuilder()
                            .add("id", recipe.getId())
                            .add("name", recipe.getName())
                            .add("cooking_instructions", recipe.getCookingInstructions().toString())
                            .add("ingredients", recipe.getIngredients().toString())
            );
        } else {
            return createJsonError("Recipe not found").build();
        }
    }

    @Override
    public JsonObject getByName(String name) {
        Optional<RecipeEntity> recipeOpt = findEntityByField(RecipeEntity.class, "name", name);
        if (recipeOpt.isPresent()) {
            RecipeEntity recipe = recipeOpt.get();

            return createJson(
                    Json.createObjectBuilder()
                            .add("id", recipe.getId())
                            .add("name", recipe.getName())
                            .add("cooking_instructions", recipe.getIngredients().toString())
                            .add("ingredients", recipe.getIngredients().toString())
            );
        } else {
            return createJsonError("Recipe not found").build();
        }
    }

    public JsonObject getByIngredients(int ingredientId) {
        Optional<RecipeEntity> recipeOpt = findEntityByField(RecipeEntity.class, "ingredients", ingredientId);
        if (recipeOpt.isPresent()) {
            RecipeEntity recipe = recipeOpt.get();

            return createJson(
                    Json.createObjectBuilder()
                            .add("id", recipe.getId())
                            .add("name", recipe.getName())
                            .add("ingredients", recipe.getIngredients().toString())
            );
        } else {
            return createJsonError("Recipe not found").build();
        }
    }

    @Transactional
    public JsonObject addRecipe(String name, String cookingInstructions) {
        RecipeEntity recipe = new RecipeEntity();
        recipe.setName(name);
        recipe.setCookingInstructions(cookingInstructions);

        entityManager.persist(recipe);

        return Json.createObjectBuilder()
                .add("id", recipe.getId())
                .add("name", recipe.getName())
                .add("cooking_instructions", recipe.getCookingInstructions())
                .build();
    }

}
