package com.toh.database;

import com.toh.database.entity.*;

import java.util.ArrayList;

public class DataBase {
    private static ArrayList<Guest> guests;
    private static ArrayList<Booking> bookings;
    private static ArrayList<Checkin> checkins;
    private static ArrayList<Checkout> checkouts;
    private static ArrayList<Facility> facilities;
    private static ArrayList<Room> rooms;
    private static ArrayList<RoomType> roomTypes;

    public static ArrayList<Guest> getGuests() {
        return guests;
    }

    public static ArrayList<Booking> getBookings() {
        return bookings;
    }

    public static ArrayList<Checkin> getCheckins() {
        return checkins;
    }

    public static ArrayList<Checkout> getCheckouts() {
        return checkouts;
    }

    public static ArrayList<Facility> getFacilities() {
        return facilities;
    }

    public static ArrayList<Room> getRooms() {
        return rooms;
    }

    public static ArrayList<RoomType> getRoomTypes() {
        return roomTypes;
    }
}
