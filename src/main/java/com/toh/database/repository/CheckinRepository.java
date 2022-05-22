package com.toh.database.repository;

import com.toh.database.core.Repository;
import com.toh.database.entity.Checkin;


public class CheckinRepository extends Repository<Checkin> {
    private static CheckinRepository instance;

    private CheckinRepository() {
        super(Checkin.class, "checkin.json");
    }

    public static CheckinRepository get() {
        if (instance == null) {
            instance = new CheckinRepository();
        }
        return instance;
    }
}

