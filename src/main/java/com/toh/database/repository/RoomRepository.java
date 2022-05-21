package com.toh.database.repository;

import com.toh.database.core.Repository;
import com.toh.database.entity.Room;


public class RoomRepository {
    private static Repository<Room> instance;

    private RoomRepository() {};

    public static Repository<Room> get() {
        if (instance == null) {
            instance = new Repository<>(Room.class, "room.json");
        }
        return instance;
    }
}

