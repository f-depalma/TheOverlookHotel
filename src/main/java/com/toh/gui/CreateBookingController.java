package com.toh.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.lang.reflect.Array;

public class CreateBookingController
{
@FXML
private TextField nameInput;

@FXML
private TextField phoneInput;

@FXML
private DatePicker checkInInput;

@FXML
private DatePicker checkOutInput;

@FXML
private ComboBox roomNumberInput;

  @FXML
  public void initialize(){
    System.out.println("first");
    int[] rooms = {100,101 };
    roomNumberInput.getItems().addAll(455,555);
  }

  public void clickSave(ActionEvent event){
    System.out.println("kkkkk");

    System.out.println(nameInput.getText());
    System.out.println(phoneInput.getText());
    System.out.println(checkInInput.getValue());
    System.out.println(checkOutInput.getValue());
    System.out.println(roomNumberInput.getValue());


    ((Node)(event.getSource())).getScene().getWindow().hide();
  }
}

