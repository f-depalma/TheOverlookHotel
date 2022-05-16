package com.toh.database.entity;

import java.util.ArrayList;

public class Checkin extends BaseEntity {
    private ArrayList<Guest> guests;
    private Booking booking;

    public ArrayList<Guest> getGuests() {
        return guests;
    }

    public void setGuests(ArrayList<Guest> guests) {
        this.guests = guests;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
