package org.clevacart.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.clevacart.dto.IngredientDTO;
import org.clevacart.dto.RecipeDTO;
import org.clevacart.dto.RecipeFilterDTO;
import org.clevacart.entity.IngredientEntity;
import org.clevacart.entity.RecipeEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class RecipeService extends BaseService<RecipeEntity> {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public JsonObject getAll() throws NoResultException{
        List<RecipeEntity> recipes = findAllByEntity(RecipeEntity.class);

        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for (RecipeEntity recipe : recipes) {
            JsonArrayBuilder ingredientArrayBuilder = Json.createArrayBuilder();

            // Converts List<IngredientEntity> to a JsonArray
            for (IngredientEntity ingredient : recipe.getIngredients()) {
                JsonObjectBuilder ingredientObjectBuilder = Json.createObjectBuilder();
                ingredientObjectBuilder.add("id", ingredient.getId());
                ingredientObjectBuilder.add("name", ingredient.getName());
                ingredientArrayBuilder.add(ingredientObjectBuilder);
            }

            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder()
                    .add("id", recipe.getId())
                    .add("name", recipe.getName() != null ? recipe.getName() : "Unknown")
                    .add("cooking_instructions", recipe.getCookingInstructions())
                    .add("ingredients", ingredientArrayBuilder.build());

            jsonArrayBuilder.add(jsonObjectBuilder);
        }
        try {
            return createJson(
                    Json.createObjectBuilder()
                            .add("recipes", jsonArrayBuilder)
            );
        } catch (NoResultException e) {
            return createJsonError("No recipes found.");
        }
    }

    @Override
    public JsonObject getById(int id) {
        RecipeEntity recipe = findEntityById(RecipeEntity.class, id);

        if (recipe != null) {
            return createJson(
                    Json.createObjectBuilder()
                            .add("id", recipe.getId())
                            .add("name", recipe.getName())
                            .add("cooking_instructions", recipe.getCookingInstructions())
                            .add("ingredients", recipe.getIngredients().toString())
            );

        } else {
            return createJsonError("Recipe not found.");
        }
    }

    @Override
    public JsonObject getByName(String name) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        List<Optional<RecipeEntity>> recipeOptionalList = findEntityByField(RecipeEntity.class, "name", name);

        for (Optional<RecipeEntity> recipeEntityOptional : recipeOptionalList) {
            if (recipeEntityOptional.isPresent()) {
                RecipeEntity recipe = recipeEntityOptional.get();

                jsonArrayBuilder.add(Json.createObjectBuilder()
                        .add("id", recipe.getId())
                        .add("name", recipe.getName())
                        .add("cooking_instructions", recipe.getCookingInstructions())
                        .add("ingredients", recipe.getIngredients().toString())
                );

            } else {
                return createJsonError("Recipe not found.");
            }
        }

        return createJson(Json.createObjectBuilder()
                .add("recipes", jsonArrayBuilder));
    }



    public JsonObject getByIngredients(int ingredientId) {
        Optional<RecipeEntity> recipeOpt = findSingleEntityByField(RecipeEntity.class, "ingredients", ingredientId);

        if (recipeOpt.isPresent()) {
            RecipeEntity recipe = recipeOpt.get();

            return createJson(
                    Json.createObjectBuilder()
                            .add("id", recipe.getId())
                            .add("name", recipe.getName())
                            .add("ingredients", recipe.getIngredients().toString())
            );
        } else {
            return createJsonError("Recipe not found.");
        }
    }

    @Transactional
    public JsonObject addRecipe(RecipeDTO recipeDTO) {
        RecipeEntity recipe = new RecipeEntity();

        recipe.setName(recipeDTO.getName());

        recipe.setCookingInstructions(recipeDTO.getCookingInstructions());

        boolean ingredientsEmpty = recipeDTO.getIngredients().isEmpty();

        // Handle ingredients from the DTO
        if (recipeDTO.getIngredients() != null && !ingredientsEmpty) {
            for (IngredientDTO ingredientDTO : recipeDTO.getIngredients()) {
                IngredientEntity existingIngredient;
                try {
                    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
                    CriteriaQuery<IngredientEntity> criteriaQuery = criteriaBuilder.createQuery(IngredientEntity.class);
                    Root<IngredientEntity> root = criteriaQuery.from(IngredientEntity.class);

                    criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("name"), ingredientDTO.getName()));

                    TypedQuery<IngredientEntity> query = entityManager.createQuery(criteriaQuery);

                    existingIngredient = query.getSingleResult();

                } catch (NoResultException e) {
                    // If the ingredient doesn't exist in the Ingredient table in DB, then create new entry
                    existingIngredient = new IngredientEntity();

                    existingIngredient.setName(ingredientDTO.getName());

                    entityManager.persist(existingIngredient);
                }

                recipe.getIngredients().add(existingIngredient);
            }
        }

        entityManager.persist(recipe);

        return createJson(Json.createObjectBuilder()
                .add("id", recipe.getId())
                .add("name", recipe.getName())
                .add("cooking_instructions", recipe.getCookingInstructions())
                .add("ingredients", recipe.getIngredients().toString())
        );
    }

    public JsonObject getByFilters(RecipeFilterDTO filter) {
        Map<String, Object> filters = new HashMap<>();

        if (filter.getId() != null) {
            filters.put("id", filter.getId());
        }

        if (filter.getName() != null) {
            filters.put("name", filter.getName());
        }

        if (filter.getAllergenIds() != null && !filter.getAllergenIds().isEmpty()) {
            filters.put("allergenIds", filter.getAllergenIds());
        }
        Optional<List<RecipeEntity>> recipes = findEntity(RecipeEntity.class, filters);


        if (recipes.isPresent() && !recipes.get().isEmpty()) {
            JsonArrayBuilder recipesJsonArrayBuilder = Json.createArrayBuilder();

            for (RecipeEntity recipe : recipes.get()) {
                JsonArrayBuilder ingredientJsonArrayBuilder = Json.createArrayBuilder();

                for (IngredientEntity ingredient : recipe.getIngredients()) {
                    JsonObject ingredientJson = Json.createObjectBuilder()
                            .add("id", ingredient.getId())
                            .add("name", ingredient.getName())
                            .build();
                    ingredientJsonArrayBuilder.add(ingredientJson);
                }

                JsonObject recipeJson = Json.createObjectBuilder()
                        .add("id", recipe.getId())
                        .add("name", recipe.getName())
                        .add("cooking_instructions", recipe.getCookingInstructions())
                        .add("ingredients", ingredientJsonArrayBuilder)
                        .build();

                recipesJsonArrayBuilder.add(recipeJson);
            }
            return createJson(Json.createObjectBuilder()
                    .add("recipes", recipesJsonArrayBuilder));
        } else {
            // Return an error if no recipes were found
            return createJsonError("No recipes found with the specified filters.");
        }
    }
}
