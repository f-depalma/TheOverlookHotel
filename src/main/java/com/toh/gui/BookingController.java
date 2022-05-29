package com.toh.gui;

import com.toh.model.Booking;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

public class BookingController
{
  @FXML
  public TableView<Booking> tableView;
  @FXML
  public TableColumn<Booking,String> nameView;
  @FXML
  public TableColumn<Booking, Date>checkInView;
  @FXML
  public TableColumn<Booking, Date> checkOutView;
  @FXML
  public TableColumn<Booking, Integer> roomView;
  @FXML
  public TableColumn<Booking, Integer> phoneView;

  @FXML
  public void initialize(){
    nameView.setCellValueFactory(new PropertyValueFactory<Booking, String>("name"));
    checkInView.setCellValueFactory(new PropertyValueFactory<Booking, Date>("checkIn"));
    checkOutView.setCellValueFactory(new PropertyValueFactory<Booking, Date>("checkOut"));
    roomView.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("roomNumber"));
    phoneView.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("phone"));



    System.out.println("first");
    Date d = new Date(2022,5,26);
    Booking b = new Booking("me",991, d, d, 102);
    tableView.getItems().add(b);
  }

  public void newBookingAction(ActionEvent event){
    Parent root;
    try {
      URL url = new File("C:\\Users\\misko\\IdeaProjects\\TheHoverlookHotel\\src\\main\\resources\\com\\toh\\gui\\CreateBooking.fxml").toURI().toURL();
      root = FXMLLoader.load(url);
      Stage stage = new Stage();
      stage.setTitle("Create booking");
      stage.setScene(new Scene(root, 650, 500));
      stage.show();
      // Hide this current window (if this is what you want)
      //            ((Node)(e.getSource())).getScene().getWindow().hide();
    }
    catch (IOException err) {
      err.printStackTrace();
    }
  }
}
