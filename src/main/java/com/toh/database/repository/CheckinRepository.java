package com.toh.database.repository;

import com.toh.database.core.Repository;
import com.toh.database.entity.Booking;
import com.toh.database.entity.Checkin;


public class CheckinRepository {
    private static Repository<Checkin> instance;

    private CheckinRepository() {};

    public static Repository<Checkin> get() {
        if (instance == null) {
            instance = new Repository<>(Checkin.class, "checkin.json");
        }
        return instance;
    }
}

