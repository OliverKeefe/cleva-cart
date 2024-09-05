package org.clevacart.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Table(name = "Meal")
public class RecipeEntity extends BaseEntity {

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IngredientEntity> ingredients;

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public void setName(String name) {

    }

    public void setIngredients(List<IngredientEntity> ingredients) {

    }
}