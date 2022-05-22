package com.toh.database.repository;

import com.toh.database.core.Repository;
import com.toh.database.entity.Room;


public class RoomRepository extends Repository<Room> {
    private static RoomRepository instance;

    private RoomRepository() {
        super(Room.class, "room.json");
    }

    public static RoomRepository execute() {
        if (instance == null) {
            instance = new RoomRepository();
        }
        return instance;
    }
}

