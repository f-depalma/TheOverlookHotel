package com.toh.database.entity;

import com.toh.database.core.BaseEntity;
import com.toh.database.core.MappedField;
import com.toh.database.core.MappedList;

import java.util.ArrayList;

public class Checkin extends BaseEntity {
    private MappedList<Guest> guests = new MappedList<>(Guest.class);
    private MappedField<Booking> booking;

    public ArrayList<Guest> getGuests() {
        return guests.getValue();
    }

    public void setGuests(ArrayList<Guest> guests) {
        this.guests.setValue(guests);
    }

    public Booking getBooking() {
        return booking.getValue();
    }

    public void setBooking(Booking booking) {
        this.booking.setValue(booking);
    }
}
