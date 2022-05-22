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
        Booking b = BookingRepository.execute().findById(1);
        b.setArriveHour(0);
        BookingRepository.execute().saveAndFlush(b);
        Checkin c = CheckinRepository.execute().findById(1);
        c.setBooking(b);
        CheckinRepository.execute().saveAndFlush(c);
        Checkout co = CheckoutRepository.execute().findById(1);
        co.setPrice(20.0);
        CheckoutRepository.execute().saveAndFlush(co);
        Facility f = FacilityRepository.execute().findById(1);
        f.setDescription("abc");
        FacilityRepository.execute().saveAndFlush(f);
        Guest g = GuestRepository.execute().findById(1);
        g.setBirthday("10/10/10");
        GuestRepository.execute().saveAndFlush(g);
        Room r = RoomRepository.execute().findById(1);
        r.setBeds(2);
        RoomRepository.execute().saveAndFlush(r);
        RoomType t = RoomTypeRepository.execute().findById(1);
        t.setName("abc");
        RoomTypeRepository.execute().saveAndFlush(t);
        //launch();
    }
}