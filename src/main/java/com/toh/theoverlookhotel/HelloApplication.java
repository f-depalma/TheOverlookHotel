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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

public class HelloApplication extends Application {


    @Override
    //main stage
    public void start(Stage primarystage) {
        try
        {

            //load fxml file
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            //create scene
            Scene scene = new Scene(root,600,600);
            primarystage.setScene(scene);
            primarystage.setTitle("OverlookHotel");
            //icon
            Image icon = new Image("file:src/main/resources/com/toh/theoverlookhotel/Pictures/aint-life-grand-at-the-overlook-1545688581.jpg");
            primarystage.getIcons().add(icon);
            //css
            String css = getClass().getResource("application.css").toExternalForm();
            scene.getStylesheets().add(css);
            //
            primarystage.show();
        }
        catch (IOException e)
        {
            System.out.println("ERROR");
            e.printStackTrace();
        }
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