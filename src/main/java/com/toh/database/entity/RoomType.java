package com.toh.database.entity;

import com.toh.database.core.BaseEntity;
import com.toh.database.core.MappedList;

import java.util.ArrayList;

public class RoomType extends BaseEntity {
    private String name;
    private MappedList<Facility> facilities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Facility> getFacilities() {
        return facilities.getValue();
    }

    public void setFacilities(ArrayList<Facility> facilities) {
        this.facilities.setValue(facilities);
    }
}
