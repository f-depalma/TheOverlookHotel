package com.toh.theoverlookhotel;

import com.toh.database.entity.Booking;
import com.toh.database.entity.Date;
import com.toh.database.entity.Facility;
import com.toh.database.repository.BookingRepository;
import com.toh.database.repository.FacilityRepository;
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
        Booking b = new Booking();
        //b.setArrive(new Date(10, 10 ,10));
        BookingRepository.get().save(b);
        Booking b1 = new Booking();
        b1.setFacilities(FacilityRepository.get().getAll());
        BookingRepository.get().save(b1);
        System.out.println(BookingRepository.get().findById(1).getFacilities().stream().map(f -> f.getName()).collect(Collectors.toList()));
        //launch();
    }
}