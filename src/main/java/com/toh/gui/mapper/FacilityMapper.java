package com.toh.gui.mapper;

import com.toh.database.entity.Facility;
import com.toh.gui.dto.FacilityDTO;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FacilityMapper {

    public static FacilityDTO entityToDTO(Facility facility) {
        FacilityDTO dto = new FacilityDTO();
        dto.setId(facility.getId());
        dto.setName(facility.getName());
        dto.setDescription(facility.getDescription());
        dto.setPrice(facility.getPrice());
        return dto;
    }

    public static ArrayList<FacilityDTO> entityToDTOList(ArrayList<Facility> facilities) {
        return facilities.stream().map(g -> entityToDTO(g))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static Facility DTOToEntity(FacilityDTO dto) {
        Facility entity = new Facility();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        return entity;
    }

    public static ArrayList<Facility> DTOToEntityList(ArrayList<FacilityDTO> facilities) {
        return facilities.stream().map(g -> DTOToEntity(g))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
