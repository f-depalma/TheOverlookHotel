package com.toh.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.Arrays;

public class MainController {

    @FXML
    public Pane checkin, booking, availability, checkout, ad;

    @FXML
    protected void clickCheckinNew() {
        setVisible(checkin);
    }

    @FXML
    protected void clickBookingNew() {
        setVisible(booking);
    }

    @FXML
    protected void clickRoomsAvailability() {
        setVisible(availability);
    }

    @FXML
    protected void clickCheckoutNew() {
        setVisible(checkout);
    }

    @FXML
    protected void clickRoomAD() {
        setVisible(ad);
    }


    private void setVisible(Pane p) {
        // set p visible and hide the other Pane in the class
        Arrays.stream(MainController.class.getDeclaredFields())
                .filter(f -> f.getType().equals(Pane.class))
                .forEach(f -> {
                    try {
                        Pane pane = (Pane) f.get(this);
                        pane.setVisible(pane.equals(p));
                        pane.setManaged(pane.equals(p));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
    }
}