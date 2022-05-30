package com.toh.database.entity;

import com.toh.database.core.BaseEntity;
import com.toh.database.core.field.MappedList;
import com.toh.database.core.Exceptions.UnsavedEntityException;

import java.util.ArrayList;

public class RoomType extends BaseEntity {
    private String name;
    private String imageName;
    private String description;
    private Double price;
    private final MappedList<Facility> facilityList = new MappedList<>(Facility.class);

    static {
        notNullFields(RoomType.class, "name", "imageName", "price", "description");
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

    public RoomType() {}

    public RoomType(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Facility> getFacilityList() {
        return facilityList.getValue();
    }

    public void setFacilityList(ArrayList<Facility> facilityList) {
        try {
            this.facilityList.setValue(facilityList);
        } catch (UnsavedEntityException e) {
            e.printStackTrace();
        }
    }

    public void saveFacility(Facility facility) {
        this.facilityList.save(facility);
    }

    public void deleteFacility(Integer id) {
        this.facilityList.remove(id);
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
