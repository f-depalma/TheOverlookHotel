package com.toh.database.entity;

import com.toh.database.core.*;
import com.toh.database.core.Exceptions.UnsavedEntityException;
import com.toh.database.core.field.Date;
import com.toh.database.core.field.MappedEntity;
import com.toh.database.core.field.MappedList;

import java.util.ArrayList;

public class Booking extends BaseEntity {
    private final MappedEntity<Room> room = new MappedEntity<>(Room.class);
    private final MappedEntity<Guest> guest = new MappedEntity<>(Guest.class);
    private Date arrive = new Date();
    private Integer arriveHour;
    private Date departure = new Date();
    private final MappedList<Facility> facilityList = new MappedList<>(Facility.class);

    static {
        notNullFields(Booking.class, "orom", "guest", "arrive", "departure");
    }

    public Booking() {}

    public Booking(Room room, Guest guest, Date arrive, Date departure) {
        setRoom(room);
        setGuest(guest);
        setArrive(arrive);
        setDeparture(departure);
    }

    public Room getRoom() {
        return room.getValue();
    }

    public void setRoom(Room room) {
        try {
            this.room.setValue(room);
        } catch (UnsavedEntityException e) {
            e.printStackTrace();
        }
    }

    public Guest getGuest() {
        return guest.getValue();
    }

    public void setGuest(Guest guest) {
        try {
            this.guest.setValue(guest);
        } catch (UnsavedEntityException e) {
            e.printStackTrace();
        }
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

    public ArrayList<Facility> getFacilityList() {
        return facilityList.getValue();
    }

    public void setFacilityList(ArrayList<Facility> facilityList) {
        try {
            this.facilityList.setValue(facilityList);
        } catch (UnsavedEntityException e) {
            e.printStackTrace();
        }
    }

    public void saveFacility(Facility facility) {
        this.facilityList.save(facility);
    }

    public void deleteFacility(Integer id) {
        this.facilityList.remove(id);
    }
}
