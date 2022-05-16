package com.toh.theoverlookhotel;

import com.toh.database.DataBase;
import com.toh.database.entity.Guest;
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
        BookingRepository b = new BookingRepository();
        FacilityRepository f = new FacilityRepository();

        launch();
    }
}