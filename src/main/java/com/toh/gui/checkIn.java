package com.toh.theoverlookhotel;

import javafx.application.Application;
import javax.swing.JOptionPane;
import javafx.stage.Stage;

public class checkIn extends Application
{

  public static void main(String[] args)
  {
    launch(args);
  }

  @Override public void start(Stage primaryStage)
  {
String input = JOptionPane.showInputDialog( null, "Write guest name" , "Check-In", JOptionPane.INFORMATION_MESSAGE);

  }

}
