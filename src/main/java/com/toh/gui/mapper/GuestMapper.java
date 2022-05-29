package com.toh.gui.mapper;

import com.toh.database.entity.Guest;
import com.toh.gui.dto.GuestDTO;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GuestMapper {

    public static GuestDTO entityToDTO(Guest guest) {
        GuestDTO dto = new GuestDTO();
        dto.setId(guest.getId());
        dto.setName(guest.getName());
        dto.setPhoneNumber(guest.getPhoneNumber());
        dto.setBirthday(guest.getBirthday());
        dto.setNationality(guest.getNationality());
        dto.setHomeAddress(guest.getHomeAddress());
        return dto;
    }
    public  static Guest DTOToEntity(GuestDTO dto){
        Guest guest = new Guest();
        guest.setId(dto.getId());
        guest.setBirthday(dto.getBirthday());
        guest.setNationality(dto.getNationality());
        guest.setName(dto.getName());
        guest.setPhoneNumber(dto.getPhoneNumber());
        guest.setHomeAddress(dto.getHomeAddress());
        return guest;
    }

    public static ArrayList<GuestDTO> entityToDTOList(ArrayList<Guest> guests) {
        return guests.stream().map(g -> entityToDTO(g))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public static ArrayList<Guest> DTOToEntityList(ArrayList<GuestDTO> guests) {
        return guests.stream().map(g -> DTOToEntity(g))
            .collect(Collectors.toCollection(ArrayList::new));}
}
