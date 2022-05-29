package com.toh.gui.controller;

import com.toh.database.core.Exceptions.ConstraintException;
import com.toh.database.core.Exceptions.EntityNotValidException;
import com.toh.database.entity.Booking;
import com.toh.database.entity.Checkin;
import com.toh.database.entity.Guest;
import com.toh.database.entity.Room;
import com.toh.database.repository.BookingRepository;
import com.toh.database.repository.CheckinRepository;
import com.toh.database.repository.GuestRepository;
import com.toh.gui.dto.GuestDTO;
import com.toh.gui.mapper.GuestMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CheckinController implements Initializable
{

  @FXML private TableView<GuestDTO> table;

  @FXML private TextField name, address, birthday, phoneNumber, nationality, nameInput;
  @FXML private ComboBox<Room> roomComboBox;

  @Override public void initialize(URL url, ResourceBundle resourceBundle)
  {
    table.setItems(FXCollections.observableList(
        GuestMapper.mapList(GuestRepository.execute().getAll())));
  }

  public void ClickOnAddCheckIn()
  {
    Guest guest = new Guest();
    guest.setPhoneNumber(phoneNumber.getText());
    guest.setNationality(nationality.getText());
    guest.setBirthday(birthday.getText());
    guest.setName(name.getText());
    guest.setHomeAddress(address.getText());

    try
    {
      GuestRepository.execute().saveAndFlush(guest);
      GuestMapper.map(guest);
      table.getItems().add(GuestMapper.map(guest));
    }
    catch (EntityNotValidException e)
    {
      e.printStackTrace();
    }
    name.clear();
    phoneNumber.clear();
    nationality.clear();
    birthday.clear();
    address.clear();

  }

  public void clickOnDeleteCheckIn()
  {

    ObservableList<GuestDTO> itemSelected, allItems;
    allItems = table.getItems();
    itemSelected = table.getSelectionModel().getSelectedItems();

    for (GuestDTO g : itemSelected)
    {
      try
      {
        GuestRepository.execute().delete(g.getId());
        itemSelected.forEach(allItems::remove);
      }
      catch (ConstraintException e)
      {
        e.printStackTrace();
      }
    }
    GuestRepository.execute().flush();
  }

  public void clickOnSearchButton()
  {
    roomComboBox.setItems(FXCollections.observableList(
        BookingRepository.execute().getAll().stream()
            .filter(b -> b.getGuest().getName().equals(nameInput.getText()))
            .map(b -> b.getRoom())
            .collect(Collectors.toCollection(ArrayList::new))));

  }

}
