package com.toh.gui.controller;

import com.toh.database.core.Exceptions.DateFormatException;
import com.toh.database.core.field.Date;
import com.toh.database.entity.Room;
import com.toh.database.repository.BookingRepository;
import com.toh.gui.JavaFXUtils;
import com.toh.gui.dto.RoomDTO;
import com.toh.gui.mapper.RoomMapper;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AvailabilityController implements Initializable {

    @FXML
    private TableView<RoomDTO> table;

    @FXML
    private TextField dateFrom, dateTo, priceMin, priceMax;

    @FXML
    private Button search;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        search.disableProperty().bind(Bindings.isEmpty(dateFrom.textProperty()).or(Bindings.isEmpty(dateTo.textProperty())));

        JavaFXUtils.dateValidation(dateFrom);
        JavaFXUtils.dateValidation(dateTo);
    }

    @FXML
    protected void find() {
        try {
            Date from = new Date(dateFrom.getText());
            Date to = new Date(dateTo.getText());

            ArrayList<Room> rooms = BookingRepository.execute().getAll().stream()
                    .filter(b -> b.getArrive().isAfterThen(from) == b.getArrive().isAfterThen(to) == b.getDeparture().isAfterThen(from) == b.getDeparture().isAfterThen(to))
                    .map(b -> b.getRoom())
                    .collect(Collectors.toCollection(ArrayList::new));


            if (!priceMin.getText().equals("")) {
                rooms = rooms.stream()
                        .filter(r -> r.getPrice().doubleValue() >= Double.parseDouble(priceMin.getText()))
                        .collect(Collectors.toCollection(ArrayList::new));
            }
            if (!priceMax.getText().equals("")) {
                rooms = rooms.stream()
                        .filter(r -> r.getPrice().doubleValue() <= Double.parseDouble(priceMax.getText()))
                        .collect(Collectors.toCollection(ArrayList::new));
            }

            table.setItems(FXCollections.observableList(RoomMapper.entityToDTOList(rooms)));
        } catch (DateFormatException e) {
        }
    }
}