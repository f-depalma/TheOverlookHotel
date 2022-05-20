package com.toh.database.entity;

import com.toh.database.core.BaseEntity;

import java.util.ArrayList;

public class RoomType extends BaseEntity {
    private String name;
    private ArrayList<Facility> facilities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Facility> getFacilities() {
        return facilities;
    }

    public void setFacilities(ArrayList<Facility> facilities) {
        this.facilities = facilities;
    }
}
