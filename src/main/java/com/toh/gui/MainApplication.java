package com.toh.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    //private Stage stage;
    //private TabPane tabPane;

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(root,700,500);
        stage.setScene(scene);
        stage.show();
        //icon
        //Image icon = new Image("C:\\Users\\misko\\IdeaProjects\\TheHoverlookHotel\\src\\main\\resources\\com\\toh\\Picture\\OverLookHotel.jpg");
        //stage.getIcons().add(icon);
    }

    public static void main(String[] args) {
        launch();
    }
}