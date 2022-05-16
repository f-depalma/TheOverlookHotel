package com.toh.database.entity;

import com.toh.database.repository.FacilityRepository;

import java.util.ArrayList;

public class Booking extends BaseEntity {

    private int room;
    private int guest;
    private String arrive;
    private int arriveHour;
    private String departure;
    private ArrayList<Integer> facilities = new ArrayList<>();

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getGuest() {
        return guest;
    }

    public void setGuest(int guest) {
        this.guest = guest;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public int getArriveHour() {
        return arriveHour;
    }

    public void setArriveHour(int arriveHour) {
        this.arriveHour = arriveHour;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public ArrayList<Integer> getFacilities() {
        return facilities;
    }

    public void setFacilities(ArrayList<Integer> facilities) {
        this.facilities = facilities;
    }
}
