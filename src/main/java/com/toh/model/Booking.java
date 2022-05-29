package com.toh.model;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.util.Date;

public class Booking
{
  private String name;

  private int phone;

  private Date checkIn;

  private Date checkOut;

  private int roomNumber;

  public Booking(String name, int phone, Date checkIn, Date checkOut,
      int roomNumber)
  {
    this.name = name;
    this.phone = phone;
    this.checkIn = checkIn;
    this.checkOut = checkOut;
    this.roomNumber = roomNumber;
  }

  public String getName()
  {
    return name;
  }

  public int getPhone()
  {
    return phone;
  }

  public Date getCheckIn()
  {
    return checkIn;
  }

  public Date getCheckOut()
  {
    return checkOut;
  }

  public int getRoomNumber()
  {
    return roomNumber;
  }


}
