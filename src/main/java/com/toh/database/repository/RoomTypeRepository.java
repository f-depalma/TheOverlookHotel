package com.toh.database.repository;

import com.toh.database.core.Repository;
import com.toh.database.entity.Booking;
import com.toh.database.entity.RoomType;


public class RoomTypeRepository {
    private static Repository<RoomType> instance;

    private RoomTypeRepository() {};

    public static Repository<RoomType> get() {
        if (instance == null) {
            instance = new Repository<>(RoomType.class, "room_type.json");
        }
        return instance;
    }
}

