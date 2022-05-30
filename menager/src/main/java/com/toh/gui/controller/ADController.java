package com.toh.gui.controller;

import com.toh.database.repository.BookingRepository;
import com.toh.gui.dto.RoomDTO;
import com.toh.gui.mapper.RoomMapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ADController implements Initializable {

    @FXML
    private TableView<RoomDTO> arrive, departure;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        arrive.setItems(FXCollections.observableList(
                BookingRepository.execute().getAll().stream()
                        .filter(b -> b.getArrive().isToday())
                        .map(b -> RoomMapper.entityToDTO(b.getRoom()))
                        .collect(Collectors.toList())));

        departure.setItems(FXCollections.observableList(
                BookingRepository.execute().getAll().stream()
                        .filter(b -> b.getDeparture().isToday())
                        .map(b -> RoomMapper.entityToDTO(b.getRoom()))
                        .collect(Collectors.toList())));
    }
}
