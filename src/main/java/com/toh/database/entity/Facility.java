package com.toh.database.entity;

import com.toh.database.core.BaseEntity;

public class Facility extends BaseEntity {
    private String name;
    private String description;
    private Double price;

    static {
        notNullFields(Facility.class, "name", "price");
    }

    public Facility() {}

    public Facility(String name, Double price) {
        setName(name);
        setPrice(price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
