package com.toh.database.repository;

import com.toh.database.core.Repository;
import com.toh.database.entity.Guest;


public class GuestRepository {
    private static Repository<Guest> instance;

    private GuestRepository() {};

    public static Repository<Guest> get() {
        if (instance == null) {
            instance = new Repository<>(Guest.class, "guest.json");
        }
        return instance;
    }
}

