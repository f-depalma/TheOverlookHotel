package com.toh.database.repository;

import com.toh.database.core.Repository;
import com.toh.database.entity.Booking;


public class BookingRepository {
    private static Repository<Booking> instance;

    private BookingRepository() {};

    public static Repository<Booking> get() {
        if (instance == null) {
            instance = new Repository<>(Booking.class, "booking.json");
        }
        return instance;
    }
}

