package com.toh.gui.controller;

import com.toh.database.core.Exceptions.DateFormatException;
import com.toh.database.core.field.Date;
import com.toh.database.entity.Room;
import com.toh.database.repository.BookingRepository;
import com.toh.database.repository.RoomRepository;
import com.toh.gui.JavaFXUtils;
import com.toh.gui.dto.RoomDTO;
import com.toh.gui.mapper.RoomMapper;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
    private TextField priceMin, priceMax;

    @FXML
    private DatePicker dateFrom, dateTo;

    @FXML
    private Button search;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        search.disableProperty().bind(Bindings.isEmpty(dateFrom.getEditor().textProperty()).or(Bindings.isEmpty(dateTo.getEditor().textProperty())));

        JavaFXUtils.setDatePickerFormat(dateFrom);
        JavaFXUtils.setDatePickerFormat(dateTo);
        JavaFXUtils.dateValidation(dateFrom);
        JavaFXUtils.dateValidation(dateTo);

    }

    @FXML
    protected void find() {
        try {
            ArrayList<Room> rooms =
                    JavaFXUtils.findAvailableRoom(
                            JavaFXUtils.getDate(dateFrom),
                            JavaFXUtils.getDate(dateTo));

            if (!priceMin.getText().equals("")) {
                try {
                    rooms = rooms.stream()
                            .filter(r -> r.getPrice() >= Double.parseDouble(priceMin.getText()))
                            .collect(Collectors.toCollection(ArrayList::new));
                    priceMin.getStyleClass().remove("error");
                } catch (NumberFormatException e) {
                    if (!priceMin.getStyleClass().contains("error"))
                        priceMin.getStyleClass().add("error");
                }
            }
            if (!priceMax.getText().equals("")) {
                try {
                    rooms = rooms.stream()
                            .filter(r -> r.getPrice() <= Double.parseDouble(priceMax.getText()))
                            .collect(Collectors.toCollection(ArrayList::new));
                    priceMax.getStyleClass().remove("error");
                } catch (NumberFormatException e) {
                    if (!priceMax.getStyleClass().contains("error"))
                        priceMax.getStyleClass().add("error");
                }
            }
            table.setItems(FXCollections.observableList(RoomMapper.entityToDTOList(rooms)));
        } catch (DateFormatException e) {
        }
    }
}