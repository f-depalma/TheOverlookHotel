package com.toh.database.repository;

import com.toh.database.core.Repository;
import com.toh.database.entity.Booking;


public class BookingRepository extends Repository<Booking> {
    // singleton
    private static BookingRepository instance;

    private BookingRepository() {
        super(Booking.class, "booking.json");
    }

    public static BookingRepository execute() {
        if (instance == null) {
            instance = new BookingRepository();
        }
        return instance;
    }
}

