package com.toh.gui.mapper;

import com.toh.database.entity.Room;
import com.toh.gui.dto.RoomDTO;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class RoomMapper {

    public static RoomDTO entityToDTO(Room room) {
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setNumber(room.getNumber());
        dto.setRoomType(room.getRoomType().getName());
        dto.setBeds(room.getBeds().toString());
        if (room.isSmoking() != null) {
            dto.setSmoking(room.isSmoking()? "Yes": "No");
        }
        dto.setPrice(room.getPrice().toString());
        return dto;
    }

    public static ArrayList<RoomDTO> entityToDTOList(ArrayList<Room> guests) {
        return guests.stream().map(g -> entityToDTO(g))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
