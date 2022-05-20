package com.toh.theoverlookhotel;

import com.toh.database.entity.Booking;
import com.toh.database.entity.Date;
import com.toh.database.entity.Facility;
import com.toh.database.entity.Guest;
import com.toh.database.repository.BookingRepository;
import com.toh.database.repository.FacilityRepository;
import com.toh.database.repository.GuestRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.stream.Collectors;

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
        for (Booking b : BookingRepository.get().getAll()) {
            System.out.println(BookingRepository.get().getConnector().ObjToJson(b));
        }

        for (Facility b : FacilityRepository.get().getAll()) {
            System.out.println(FacilityRepository.get().getConnector().ObjToJson(b));
        }

        for (Guest b : GuestRepository.get().getAll()) {
            System.out.println(GuestRepository.get().getConnector().ObjToJson(b));
        }

        //launch();
    }
}