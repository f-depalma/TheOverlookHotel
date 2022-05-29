package com.toh.gui;

import com.toh.database.core.Exceptions.EntityNotValidException;
import com.toh.database.core.field.Date;
import com.toh.database.entity.Booking;
import com.toh.database.entity.Guest;
import com.toh.database.entity.Room;
import com.toh.database.entity.RoomType;
import com.toh.database.repository.BookingRepository;
import com.toh.database.repository.GuestRepository;
import com.toh.database.repository.RoomRepository;
import com.toh.database.repository.RoomTypeRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        try {
            RoomType t = new RoomType("tipo");
            RoomTypeRepository.execute().saveAndFlush(t);
            Room r = new Room("K219", t, 3, 200.99);
            RoomRepository.execute().saveAndFlush(r);
            Guest g = new Guest("Francesco", "+4555555");
            GuestRepository.execute().saveAndFlush(g);
            Booking b = new Booking(r, g, new Date(10, 10, 2020), new Date(14, 10, 2020));
            BookingRepository.execute().saveAndFlush(b);

        } catch (EntityNotValidException e) {
            e.printStackTrace();
        }
        launch();
    }
}