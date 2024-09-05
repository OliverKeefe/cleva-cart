package org.clevacart.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Allergen")
public class AllergenEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;


    // Default constructor required by JPA
    public AllergenEntity() {
    }

    // Constructor with name parameter
    public AllergenEntity(String name) {
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

    }

    @Override
    public String toString() {
        return "Allergen{id=" + getId() + ", name='" + getName() + "'}";
    }
}
