package com.toh.database.entity;

import com.toh.database.core.BaseEntity;
import com.toh.database.core.MappedEntity;
import com.toh.database.core.MappedList;

import java.util.ArrayList;

public class Checkin extends BaseEntity {
    private MappedList<Guest> guests = new MappedList<>(Guest.class);
    private MappedEntity<Booking> booking = new MappedEntity<>(Booking.class);

    public ArrayList<Guest> getGuests() {
        return guests.getValue();
    }

    public void setGuests(ArrayList<Guest> guests) {
        this.guests.setValue(guests);
    }

    public void saveGuest(Guest guest) {
        this.guests.save(guest);
    }

    public void removeGuest(Integer id) {
        this.guests.remove(id);
    }

    public Booking getBooking() {
        return booking.getValue();
    }

    public void setBooking(Booking booking) {
        this.booking.setValue(booking);
    }
}
