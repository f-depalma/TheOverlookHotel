package com.toh.gui.controller;

import com.toh.database.entity.Checkin;
import com.toh.database.entity.Guest;
import com.toh.database.repository.CheckinRepository;
import com.toh.database.repository.GuestRepository;
import com.toh.gui.dto.GuestDTO;
import com.toh.gui.mapper.GuestMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class CheckinController implements Initializable {

    @FXML
    private TableView<GuestDTO> table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table.setItems(FXCollections.observableList(GuestMapper.mapList(GuestRepository.execute().getAll())));
    }
}
