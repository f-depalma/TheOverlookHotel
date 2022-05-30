package com.toh.gui.dto;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RoomDTO {
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty number = new SimpleStringProperty();
    private SimpleStringProperty roomType = new SimpleStringProperty();
    private SimpleIntegerProperty beds = new SimpleIntegerProperty();
    private SimpleStringProperty smoking = new SimpleStringProperty();
    private SimpleStringProperty price = new SimpleStringProperty();

    public String getNumber() {
        return number.get();
    }

    public SimpleStringProperty numberProperty() {
        return number;
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    public String getRoomType() {
        return roomType.get();
    }

    public SimpleStringProperty roomTypeProperty() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType.set(roomType);
    }

    public Integer getBeds() {
        return beds.get();
    }

    public SimpleIntegerProperty bedsProperty() {
        return beds;
    }

    public void setBeds(Integer beds) {
        this.beds.set(beds);
    }

    public String getSmoking() {
        return smoking.get();
    }

    public SimpleStringProperty smokingProperty() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking.set(smoking);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public int getId()
    {
        return id.get();
    }

    public SimpleIntegerProperty idProperty()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id.set(id);
    }

    @Override public String toString()
    {
        return this.getNumber();
    }
}
