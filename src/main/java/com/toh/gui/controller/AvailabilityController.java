package com.toh.gui.controller;

import com.toh.database.core.field.Date;
import com.toh.database.entity.Room;
import com.toh.database.repository.BookingRepository;
import com.toh.database.repository.RoomRepository;
import com.toh.gui.dto.RoomDTO;
import com.toh.gui.mapper.RoomMapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AvailabilityController {

    @FXML
    private TableView<RoomDTO> table;

    @FXML
    private TextField dateFrom, dateTo, priceLow, priceHight;

    @FXML
    protected void find() {
        if (!dateFrom.getText().equals("") && !dateTo.getText().equals("")) {
            Date from = new Date(dateFrom.getText());
            Date to = new Date(dateTo.getText());

            ArrayList<Room> rooms = BookingRepository.execute().getAll().stream()
                    .filter(b -> b.getArrive().isAfterThen(from) == b.getArrive().isAfterThen(to) == b.getDeparture().isAfterThen(from) == b.getDeparture().isAfterThen(to))
                    .map(b -> b.getRoom())
                    .collect(Collectors.toCollection(ArrayList::new));


            if (!priceLow.getText().equals("")) {
                rooms = rooms.stream()
                        .filter(r -> r.getPrice().doubleValue() >= Double.parseDouble(priceLow.getText()))
                        .collect(Collectors.toCollection(ArrayList::new));
            }
            if (!priceHight.getText().equals("")) {
                rooms = rooms.stream()
                        .filter(r -> r.getPrice().doubleValue() <= Double.parseDouble(priceHight.getText()))
                        .collect(Collectors.toCollection(ArrayList::new));
            }

            System.out.println(rooms.stream().map(r -> r.getNumber()).collect(Collectors.toList()));
            table.setItems(FXCollections.observableList(RoomMapper.entityToDTOList(rooms)));
        }
    }
}