package com.toh.database.entity;

import com.toh.database.core.BaseEntity;
import com.toh.database.core.field.MappedEntity;
import com.toh.database.core.field.MappedList;
import com.toh.database.core.Exceptions.UnsavedEntityException;

import java.util.ArrayList;

public class Checkin extends BaseEntity {
    private final MappedList<Guest> guestList = new MappedList<>(Guest.class);
    private final MappedEntity<Booking> booking = new MappedEntity<>(Booking.class);

    static {
        notNullFields(Checkin.class, "guestList", "booking");
    }

    public Checkin() {}

    public Checkin(ArrayList<Guest> guestList, Booking booking) {
        setGuestList(guestList);
        setBooking(booking);
    }

    public ArrayList<Guest> getGuestList() {
        return guestList.getValue();
    }

    public void setGuestList(ArrayList<Guest> guestList) {
        try {
            this.guestList.setValue(guestList);
        } catch (UnsavedEntityException e) {
            e.printStackTrace();
        }
    }

    public void saveGuest(Guest guest) {
        this.guestList.save(guest);
    }

    public void removeGuest(Integer id) {
        this.guestList.remove(id);
    }

    public Booking getBooking() {
        return booking.getValue();
    }

    public void setBooking(Booking booking) {
        try {
            this.booking.setValue(booking);
        } catch (UnsavedEntityException e) {
            e.printStackTrace();
        }
    }
}
