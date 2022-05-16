package com.toh.database.entity;

import java.util.ArrayList;

public class Booking {

    private Room room420;
    private Guest guest;
    private Date arrive;
    private int arriveHour;
    private Date departure;
    private ArrayList<Facility> facilities;

    public int getArriveHour() {
        return arriveHour;
    }

    public void setArriveHour(int arriveHour) {
        this.arriveHour = arriveHour;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Date getArrive() {
        return arrive;
    }

    public void setArrive(Date arrive) {
        this.arrive = arrive;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public ArrayList<Facility> getFacilities() {
        return facilities;
    }

    public void setFacilities(ArrayList<Facility> facilities) {
        this.facilities = facilities;
    }
}
