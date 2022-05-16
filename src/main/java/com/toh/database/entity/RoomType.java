package com.toh.database.entity;

import java.util.ArrayList;

public class RoomType {
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
