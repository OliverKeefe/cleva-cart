package org.clevacart.domain.model;

public class Nutrient {
    private int id;
    private String name;

    public Nutrient(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Nutrient{id=" + id + ", name='" + name + "'}";
    }
}
