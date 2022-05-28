package com.toh.theoverlookhotel.room;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RoomController
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
}
