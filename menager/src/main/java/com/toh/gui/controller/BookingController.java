package com.toh.gui.controller;

import com.toh.database.core.Exceptions.DateFormatException;
import com.toh.database.core.Exceptions.EntityNotValidException;
import com.toh.database.core.field.Date;
import com.toh.database.entity.Booking;
import com.toh.database.entity.Guest;
import com.toh.database.entity.Room;
import com.toh.database.repository.BookingRepository;
import com.toh.database.repository.FacilityRepository;
import com.toh.database.repository.GuestRepository;
import com.toh.database.repository.RoomRepository;
import com.toh.gui.JavaFXUtils;
import com.toh.gui.dto.FacilityDTO;
import com.toh.gui.dto.RoomDTO;
import com.toh.gui.mapper.FacilityMapper;
import com.toh.gui.mapper.RoomMapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class BookingController implements Initializable {

    @FXML
    private DatePicker arrive, departure, gBirthday;

    @FXML
    private TextField gName, gPhoneNumber, gAddress, gNationality, arriveHour;

    @FXML
    private ListView<FacilityDTO> facilityList;

    @FXML
    private ComboBox<RoomDTO> roomList;

    @FXML
    private ComboBox<FacilityDTO> facilityCombo;

    Alert error = new Alert(Alert.AlertType.WARNING);
    Alert good = new Alert(Alert.AlertType.INFORMATION);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFXUtils.setDatePickerFormat(arrive);
        JavaFXUtils.setDatePickerFormat(departure);
        JavaFXUtils.setDatePickerFormat(gBirthday);

        JavaFXUtils.dateValidation(arrive);
        JavaFXUtils.dateValidation(departure);
        JavaFXUtils.dateValidation(gBirthday);

        facilityList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        setFacilityCombo();
    }

    private void setFacilityCombo() {
        facilityCombo.setItems(
                FXCollections.observableList(
                        FacilityMapper.entityToDTOList(
                                FacilityRepository.execute().getAll())));
    }

    @FXML
    protected void find() {
        try {
            ArrayList<Room> rooms =
                    JavaFXUtils.findAvailableRoom(
                            JavaFXUtils.getDate(arrive),
                            JavaFXUtils.getDate(departure));

            roomList.setItems(FXCollections.observableList(RoomMapper.entityToDTOList(rooms)));
        } catch (DateFormatException e) {
        }
    }

    @FXML
    protected void add() {
        FacilityDTO facilityDTO = facilityCombo.getSelectionModel().getSelectedItem();
        if (facilityDTO != null) {
            facilityList.getItems().add(facilityDTO);
            facilityCombo.getItems().remove(facilityDTO);
            facilityCombo.valueProperty().set(null);
        }
    }

    @FXML
    protected void delete() {
        FacilityDTO facilityDTO = facilityList.getSelectionModel().getSelectedItem();
        facilityCombo.getItems().add(facilityDTO);
        facilityList.getItems().remove(facilityDTO);
    }

    @FXML
    protected void save() {
        Booking b = new Booking();
        RoomDTO roomDTO = roomList.getSelectionModel().getSelectedItem();
        if (roomDTO != null)
            b.setRoom(RoomRepository.execute().findById(roomDTO.getId()));
        Guest g = new Guest(gName.getText(), gPhoneNumber.getText());
        g.setBirthday(gBirthday.getEditor().getText());
        g.setHomeAddress(gAddress.getText());
        g.setNationality(gNationality.getText());

        try {
            GuestRepository.execute().save(g);
            b.setGuest(g);
            b.setArriveHour(Integer.parseInt(arriveHour.getText()));
            b.setArrive(new Date(arrive.getEditor().getText()));
            b.setDeparture(new Date(departure.getEditor().getText()));
            b.setFacilityList(
                    FacilityMapper.DTOToEntityList(
                            facilityList.getItems()
                                    .stream().collect(Collectors.toCollection(ArrayList::new))));
            BookingRepository.execute().saveAndFlush(b);
            GuestRepository.execute().flush();

            good.setContentText("Saved");
            good.show();

            arrive.getEditor().clear();
            departure.getEditor().clear();
            roomList.getItems().removeIf(r -> true);
            gName.clear();
            gPhoneNumber.clear();
            gBirthday.getEditor().clear();
            gAddress.clear();
            gNationality.clear();
            arriveHour.clear();
            facilityList.getItems().removeIf(f -> true);
            setFacilityCombo();
        } catch (EntityNotValidException | NumberFormatException | DateFormatException e) {
            error.setContentText("Error during saving");
            error.show();
            e.printStackTrace();
        }
    }
}
