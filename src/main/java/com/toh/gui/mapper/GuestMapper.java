package com.toh.gui.mapper;

import com.toh.database.entity.Guest;
import com.toh.gui.dto.GuestDTO;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GuestMapper {
    public static GuestDTO map(Guest guest) {
        GuestDTO dto = new GuestDTO();
        dto.setId(guest.getId());
        dto.setName(guest.getName());
        dto.setPhoneNumber(guest.getPhoneNumber());
        dto.setBirthday(guest.getBirthday());
        dto.setNationality(guest.getNationality());
        dto.setHomeAddress(guest.getHomeAddress());
        return dto;
    }

    public static ArrayList<GuestDTO> mapList(ArrayList<Guest> guests) {
        return guests.stream().map(g -> map(g))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
