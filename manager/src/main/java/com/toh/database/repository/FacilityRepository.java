package com.toh.database.repository;

import com.toh.database.core.Repository;
import com.toh.database.entity.Facility;


public class FacilityRepository extends Repository<Facility> {
    private static FacilityRepository instance;

    private FacilityRepository() {
        super(Facility.class, "facility.json");
    }

    public static FacilityRepository execute() {
        if (instance == null) {
            instance = new FacilityRepository();
        }
        return instance;
    }
}

