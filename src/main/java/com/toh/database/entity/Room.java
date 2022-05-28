package com.toh.database.entity;

import com.toh.database.core.BaseEntity;
import com.toh.database.core.field.MappedEntity;
import com.toh.database.core.Exceptions.UnsavedEntityException;

public class Room extends BaseEntity {
    private String number;
    private MappedEntity<RoomType> roomType = new MappedEntity<>(RoomType.class);
    private Integer beds;
    private Boolean smoking;
    private Double price;

    static {
        notNullFields(Room.class, "number", "roomType", "beds");
    }

    public Room() {}

    public Room(String number, RoomType roomType, Integer beds) {
        setNumber(number);
        setRoomType(roomType);
        setBeds(beds);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public RoomType getRoomType() {
        return roomType.getValue();
    }

    public void setRoomType(RoomType roomType) {
        try {
            this.roomType.setValue(roomType);
        } catch (UnsavedEntityException e) {
            e.printStackTrace();
        }
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
