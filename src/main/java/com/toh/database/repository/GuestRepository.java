package com.toh.database.repository;

import com.toh.database.core.Repository;
import com.toh.database.entity.Guest;


public class GuestRepository extends Repository<Guest> {
    private static GuestRepository instance;

    private GuestRepository() {
        super(Guest.class, "guest.json");
    }

    public static GuestRepository execute() {
        if (instance == null) {
            instance = new GuestRepository();
        }
        return instance;
    }
}

