package com.toh.gui.mapper;

import com.toh.database.entity.Guest;
import com.toh.gui.dto.GuestDTO;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GuestMapper {

    public static GuestDTO entityToDTO(Guest guest) {
        GuestDTO dto = new GuestDTO();
        dto.setName(guest.getName());
        dto.setPhoneNumber(guest.getPhoneNumber());
        dto.setBirthday(guest.getBirthday());
        dto.setNationality(guest.getNationality());
        dto.setHomeAddress(guest.getHomeAddress());
        return dto;
    }

    public static ArrayList<GuestDTO> entityToDTOList(ArrayList<Guest> guests) {
        return guests.stream().map(g -> entityToDTO(g))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
