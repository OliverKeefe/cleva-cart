package org.clevacart.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Ingredient")
public class IngredientEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(mappedBy = "ingredients")
    private List<RecipeEntity> recipes;

    @ManyToMany
    @JoinTable(
            name = "Ingredient_Nutrient",
            joinColumns = @JoinColumn(name = "ingredient_id"),
            inverseJoinColumns = @JoinColumn(name = "nutrient_id")
    )
    private List<NutrientEntity> nutrients;

    @ManyToMany
    @JoinTable(
            name = "Ingredient_Allergen",
            joinColumns = @JoinColumn(name = "ingredient_id"),
            inverseJoinColumns = @JoinColumn(name = "allergen_id")
    )
    private List<AllergenEntity> allergens;

    public IngredientEntity() {
    }

    public IngredientEntity(String name) {
        this.name = name;
    }

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

    @Override
    public String toString() {
        return "Ingredient{id=" + id + ", name='" + name + "'}";
    }
}
