package org.clevacart.entity;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {

    private String name;

    public abstract int getId();

    public abstract void setId(int id);

    public abstract String getName();

    public abstract void setName(String name);

}
