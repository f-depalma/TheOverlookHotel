package com.toh.database.repository;

import com.toh.database.core.Repository;
import com.toh.database.entity.RoomType;


public class RoomTypeRepository extends Repository<RoomType> {
    private static RoomTypeRepository instance;

    private RoomTypeRepository() {
        super(RoomType.class, "room_type.json");
    }

    public static RoomTypeRepository execute() {
        if (instance == null) {
            instance = new RoomTypeRepository();
        }
        return instance;
    }
}

