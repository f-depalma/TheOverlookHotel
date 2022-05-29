package com.toh.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.util.Arrays;

public class MainController {

    @FXML
    public Pane checkin, booking, availability;

    @FXML
    protected void clickRoomsFind() {
        setVisible(checkin);
    }

    @FXML
    protected void clickRoomsAvailability() {
        setVisible(booking);
    }

    private void setVisible(Pane p) {
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