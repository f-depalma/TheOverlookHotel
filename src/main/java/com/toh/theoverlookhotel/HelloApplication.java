package com.toh.theoverlookhotel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

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
        launch();
    }
}