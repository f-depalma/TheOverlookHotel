package com.toh.theoverlookhotel.booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BookingController
{
  private Stage stage;
  private Scene scene;
  private Parent root;


  @FXML
  protected void GoBackButton(ActionEvent event) throws Exception{
    Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root,600,600);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  protected void FindButton(ActionEvent event) throws Exception{
    Parent root = FXMLLoader.load(getClass().getResource("Booking/Find.fxml"));
    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root,600,600);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  protected void CreateButton(ActionEvent event) throws Exception{
    Parent root = FXMLLoader.load(getClass().getResource("Booking/Create.fxml"));
    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root,600,600);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  protected void EditButton(ActionEvent event) throws Exception{
    Parent root = FXMLLoader.load(getClass().getResource("Booking/Edit.fxml"));
    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root,600,600);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  protected void DeleteButton(ActionEvent event) throws Exception{
    Parent root = FXMLLoader.load(getClass().getResource("Booking/Delete.fxml"));
    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root,600,600);
    stage.setScene(scene);
    stage.show();
  }

}
