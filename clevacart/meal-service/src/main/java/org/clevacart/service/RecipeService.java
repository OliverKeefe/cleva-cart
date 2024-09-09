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
import org.clevacart.entity.IngredientEntity;
import org.clevacart.entity.RecipeEntity;

import java.util.List;
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
            return createJsonError("Recipe not found");
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
            return createJsonError("Recipe not found");
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
            return createJsonError("Recipe not found");
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
}
