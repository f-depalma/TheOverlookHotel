package com.toh.theoverlookhotel;

import com.toh.database.entity.*;
import com.toh.database.repository.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Booking b = BookingRepository.get().findById(1);
        BookingRepository.get().save(b);
        Checkin c = new Checkin();
        CheckinRepository.get().save(c);
        Checkout co = new Checkout();
        CheckoutRepository.get().save(co);
        Facility f = new Facility();
        FacilityRepository.get().save(f);
        Guest g = new Guest();
        GuestRepository.get().save(g);
        Room r = new Room();
        RoomRepository.get().save(r);
        RoomType t = new RoomType();
        RoomTypeRepository.get().save(t);
        //launch();
    }
}