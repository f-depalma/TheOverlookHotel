package com.toh.database.repository;

import com.toh.database.entity.Facility;
import com.toh.database.entity.Guest;


public class FacilityRepository extends Repository<Facility>{
    public FacilityRepository() {
        super(Facility.class, "facility.json");
    }
}

