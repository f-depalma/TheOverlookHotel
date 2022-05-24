package com.toh.database.entity;

import com.toh.database.core.BaseEntity;
import com.toh.database.core.MappedEntity;

public class Room extends BaseEntity {
    private String number;
    private MappedEntity<RoomType> type = new MappedEntity<>(RoomType.class);
    private Integer beds;
    private Boolean smoking;
    private Double price;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public RoomType getType() {
        return type.getValue();
    }

    public void setType(RoomType type) {
        this.type.setValue(type);
    }

    public Integer getBeds() {
        return beds;
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    public Boolean isSmoking() {
        return smoking;
    }

    public void setSmoking(Boolean smoking) {
        this.smoking = smoking;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
