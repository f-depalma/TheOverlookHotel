package com.toh.database.entity;

import com.toh.database.core.BaseEntity;
import com.toh.database.core.MappedField;
import com.toh.database.core.MappedList;

import java.util.ArrayList;

public class Booking extends BaseEntity {
    private MappedField<Room> room = new MappedField<>(Room.class);
    private MappedField<Guest> guest = new MappedField<>(Guest.class);
    private Date arrive = new Date();
    private Integer arriveHour;
    private Date departure = new Date();
    private MappedList<Facility> facilities = new MappedList<>(Facility.class);

    public Room getRoom() {
        return room.getValue();
    }

    public void setRoom(Room room) {
        this.room.setValue(room);
    }

    public Guest getGuest() {
        return guest.getValue();
    }

    public void setGuest(Guest guest) {
        this.guest.setValue(guest);
    }

    public Date getArrive() {
        return arrive;
    }

    public void setArrive(Date arrive) {
        this.arrive = arrive;
    }

    public Integer getArriveHour() {
        return arriveHour;
    }

    public void setArriveHour(Integer arriveHour) {
        this.arriveHour = arriveHour;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public ArrayList<Facility> getFacilities() {
        return facilities.getValue();
    }

    public void setFacilities(ArrayList<Facility> facilities) {
        this.facilities.setValue(facilities);
    }
}
