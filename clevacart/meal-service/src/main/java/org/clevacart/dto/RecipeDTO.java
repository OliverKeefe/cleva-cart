package org.clevacart.dto;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.clevacart.entity.IngredientEntity;

import java.util.List;

@ApplicationScoped
public class RecipeDTO {
    private String name;
    private String cookingInstructions;
    private List<IngredientDTO> ingredients;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCookingInstructions() {
        return cookingInstructions;
    }

    public void setCookingInstructions(String cookingInstructions) {
        this.cookingInstructions = cookingInstructions;
    }

    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }
}