package org.clevacart.entity;

import jakarta.persistence.*;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Recipe")
public class RecipeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private ArrayList<String> cookingInstructions;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IngredientEntity> ingredients;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getCookingInstructions() {
        return this.cookingInstructions;
    }

    public void setCookingInstructions(ArrayList<String> cookingInstructions) {
        this.cookingInstructions = cookingInstructions;
    }

    public void setIngredients(List<IngredientEntity> ingredients) {

    }
}