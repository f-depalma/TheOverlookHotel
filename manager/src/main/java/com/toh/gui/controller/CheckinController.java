package com.toh.gui.controller;

import com.toh.database.core.Exceptions.ConstraintException;
import com.toh.database.core.Exceptions.EntityNotValidException;
import com.toh.database.core.field.Date;
import com.toh.database.entity.Booking;
import com.toh.database.entity.Checkin;
import com.toh.database.entity.Guest;
import com.toh.database.repository.BookingRepository;
import com.toh.database.repository.CheckinRepository;
import com.toh.database.repository.GuestRepository;
import com.toh.gui.JavaFXUtils;
import com.toh.gui.dto.GuestDTO;
import com.toh.gui.dto.RoomDTO;
import com.toh.gui.mapper.GuestMapper;
import com.toh.gui.mapper.RoomMapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CheckinController implements Initializable {

    @FXML
    private TableView<GuestDTO> table;

    @FXML
    private TextField name, address, phoneNumber, nationality, nameInput;

    @FXML
    private DatePicker birthday;

    @FXML
    private ComboBox<RoomDTO> roomComboBox;

    @FXML
    private Button save;

    private ArrayList<Booking> bookings;

    Alert error = new Alert(Alert.AlertType.WARNING);
    Alert good = new Alert(Alert.AlertType.INFORMATION);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        JavaFXUtils.setDatePickerFormat(birthday);
        JavaFXUtils.dateValidation(birthday);
    }

    public void ClickOnAddCheckIn() {
        Guest guest = new Guest();
        guest.setPhoneNumber(phoneNumber.getText());
        guest.setNationality(nationality.getText());
        guest.setBirthday(birthday.getEditor().getText());
        guest.setName(name.getText());
        guest.setHomeAddress(address.getText());

        try {
            GuestRepository.execute().save(guest);
            table.getItems().add(GuestMapper.entityToDTO(guest));
        } catch (EntityNotValidException e) {
            e.printStackTrace();
        }
        name.clear();
        phoneNumber.clear();
        nationality.clear();
        birthday.getEditor().clear();
        address.clear();

    }

    public void clickOnDeleteCheckIn() {
        GuestDTO itemSelected;
        itemSelected = table.getSelectionModel().getSelectedItem();

        try {
            GuestRepository.execute().delete(itemSelected.getId());
        } catch (ConstraintException e) {
        } finally {
            table.getItems().remove(itemSelected);
        }
    }

    @FXML
    public void clickOnSearchButton() {
        bookings = BookingRepository.execute().getAll().stream()
                .filter(b -> b.getGuest().getName().equals(nameInput.getText())
                        && b.getArrive().isToday())
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<RoomDTO> rooms = bookings.stream()
                .map(b -> RoomMapper.entityToDTO(b.getRoom()))
                .collect(Collectors.toCollection(ArrayList::new));

        roomComboBox.setItems(FXCollections.observableList(rooms));

    }

    @FXML
    public void selectBooking() {
        RoomDTO itemSelected = roomComboBox.getSelectionModel().getSelectedItem();
        if(itemSelected != null) {
            table.getItems().add(GuestMapper.entityToDTO(bookings.stream()
                    .filter(b -> b.getRoom().getId().equals(itemSelected.getId()))
                    .findFirst().orElse(null).getGuest()));
            save.setDisable(false);
        }
    }

    @FXML
    public void clickOnSaveButton() {
        Checkin checkin = new Checkin();
        checkin.setBooking(bookings.stream()
                .filter(b -> b.getRoom().getId().equals(roomComboBox.getSelectionModel().getSelectedItem().getId()))
                .findFirst().orElse(null));
        checkin.setGuestList(
                GuestMapper.DTOToEntityList(new ArrayList<>(table.getItems())));
        try {
            CheckinRepository.execute().saveAndFlush(checkin);
            GuestRepository.execute().flush();

            good.setContentText("Saved");
            good.show();

            name.clear();
            roomComboBox.getItems().removeIf(r -> true);
            table.getItems().removeIf(g -> true);
        } catch (EntityNotValidException e) {
            error.setContentText("Error during saving");
            error.show();
            e.printStackTrace();
        }
    }
}
