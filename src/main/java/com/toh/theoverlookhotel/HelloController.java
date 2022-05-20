package com.toh.theoverlookhotel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HelloController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label welcomeText= new Label();

    //Hello Button
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to OverlookHotel booking system !");
    }

    //Booking Button
    @FXML
    protected void ManageBooking(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Booking/Booking.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,600,600);
        stage.setScene(scene);
        stage.show();
    }

    //Room Button
    @FXML
    protected void ManageRoom(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Room/Room.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,600,600);
        stage.setScene(scene);
        stage.show();
    }

    //CheckIn Button
    @FXML
    protected void ManageCheckIn(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("CheckIn/"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,600,600);
        stage.setScene(scene);
        stage.show();
    }

    //CheckOut Button
    @FXML
    protected void ManageCheckOut(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("CheckOut/"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,600,600);
        stage.setScene(scene);
        stage.show();
    }


}