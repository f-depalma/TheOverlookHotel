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
        stage.setTitle("The Overlook Hotel");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}