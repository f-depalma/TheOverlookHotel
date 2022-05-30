package com.toh.gui.controller;

import com.toh.database.core.Exceptions.EntityNotValidException;
import com.toh.database.entity.Booking;
import com.toh.database.entity.Checkout;
import com.toh.database.entity.Facility;
import com.toh.database.repository.BookingRepository;
import com.toh.database.repository.CheckoutRepository;
import com.toh.gui.dto.RoomDTO;
import com.toh.gui.mapper.RoomMapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CheckoutController implements Initializable {

    @FXML
    private ComboBox<RoomDTO> rooms;

    @FXML
    private TextField discount;

    @FXML
    private Label price;

    @FXML
    private Button save;

    private Booking booking;

    Alert error = new Alert(Alert.AlertType.WARNING);
    Alert good = new Alert(Alert.AlertType.INFORMATION);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<RoomDTO> roomList =
                BookingRepository.execute().getAll().stream()
                        .filter(b -> b.getDeparture().isToday())
                        .map(b -> RoomMapper.entityToDTO(b.getRoom()))
                        .collect(Collectors.toCollection(ArrayList::new));

        rooms.setItems(FXCollections.observableList(roomList));
    }

    @FXML
    protected void roomSelected() {
        try {
            booking = BookingRepository.execute().getAll().stream()
                    .filter(b -> b.getDeparture().isToday()
                            && b.getRoom().getId().equals(rooms.getSelectionModel().getSelectedItem().getId()))
                    .findFirst().orElseThrow(Exception::new);
            rooms.getStyleClass().remove("error");
        } catch (Exception e) {
            if (!rooms.getStyleClass().contains("error"))
                rooms.getStyleClass().add("error");
        }
        enableSave();
    }

    @FXML
    protected void calculatePrice() {
        if (booking != null) {
            double priceValue = booking.getRoom().getPrice();
            for (Facility f : booking.getFacilityList()) {
                priceValue += f.getPrice();
            }
            try {
                if (!Double.valueOf(discount.getText()).equals(0.0)) {
                    priceValue *= 1 - Double.parseDouble(discount.getText()) / 100;
                }
                discount.getStyleClass().remove("error");
                price.setText(priceValue + "DKK");
                enableSave();
            } catch (NumberFormatException e) {
                if (!discount.getStyleClass().contains("error"))
                    discount.getStyleClass().add("error");
                price.setText("");
            }
        }
    }

    private void enableSave() {
        save.setDisable(rooms.getSelectionModel().getSelectedItem() == null || price.getText().equals(""));
    }

    @FXML
    protected void save() {
        if (!price.getText().equals("")) {
            Checkout c = new Checkout();
            c.setBooking(booking);
            c.setPrice(Double.valueOf(price.getText().replace("DKK", "")));
            c.setDiscount(Double.valueOf(discount.getText()));
            try {
                CheckoutRepository.execute().saveAndFlush(c);
                rooms.getItems().removeIf(r -> true);
                discount.setText("0");
                price.setText("");
                good.setContentText("Saved");
                good.show();
            } catch (EntityNotValidException e) {
                error.setContentText("Error during saving");
                error.show();
            }
        }
    }
}
