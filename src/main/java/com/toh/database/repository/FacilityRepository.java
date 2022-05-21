package com.toh.database.repository;

import com.toh.database.core.Repository;
import com.toh.database.entity.Facility;


public class FacilityRepository {
    private static Repository<Facility> instance;

    private FacilityRepository() {};

    public static Repository<Facility> get() {
        if (instance == null) {
            instance = new Repository<>(Facility.class, "facility.json");
        }
        return instance;
    }
}

