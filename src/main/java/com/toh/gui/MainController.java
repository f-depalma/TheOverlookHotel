package com.toh.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.Arrays;

public class MainController {

    @FXML
    public Pane checkin, booking;

    @FXML
    protected void clickRoomsFind() {
        setVisible(checkin);
    }

    @FXML
    protected void clickRoomsAvailability() {
        setVisible(booking);
    }

    private void setVisible(Pane p) {
        p.setVisible(true);
        p.setManaged(true);
        Arrays.stream(MainController.class.getDeclaredFields())
                .filter(f -> f.getType().equals(Pane.class))
                .forEach(f -> {
                    try {
                        Pane otherP = (Pane) f.get(this);
                        if (!otherP.equals(p)) {
                            otherP.setVisible(false);
                            otherP.setManaged(false);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
    }
}