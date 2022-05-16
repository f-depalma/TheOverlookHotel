package com.toh.database.repository;

import com.toh.database.entity.Booking;


public class BookingRepository extends Repository<Booking>{
    public BookingRepository() {
        super(Booking.class, "booking.json");
    }
}

