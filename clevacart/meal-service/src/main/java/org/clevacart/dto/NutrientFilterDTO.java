package org.clevacart.dto;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class NutrientFilterDTO {
    private Integer id;
    private String name;
    private List<Integer> allergenIds;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Integer> getAllergenIds() { return allergenIds; }
    public void setAllergenIds(List<Integer> allergenIds) { this.allergenIds = allergenIds; }

}