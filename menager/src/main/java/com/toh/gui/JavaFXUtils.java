package com.toh.gui;

import com.toh.database.core.Exceptions.DateFormatException;
import com.toh.database.core.field.Date;
import com.toh.database.entity.Room;
import com.toh.database.repository.BookingRepository;
import com.toh.database.repository.RoomRepository;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class JavaFXUtils {
    public static void dateValidation(DatePicker input) {
        input.getEditor().textProperty().addListener((obs, oldText, newText) -> {
            try {
                new Date(newText);
                input.getStyleClass().remove("error");
            } catch (DateFormatException e) {
                if (!input.getStyleClass().contains("error"))
                    input.getStyleClass().add("error");
            }
        });
    }

    public static void setDatePickerFormat(DatePicker dp) {
        dp.setConverter(new StringConverter<LocalDate>() {
            String pattern = "dd/MM/yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                dp.setPromptText(pattern.toLowerCase());
            }

            @Override public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }

    public static ArrayList<Room> findAvailableRoom(Date from, Date to) {
        ArrayList<Integer> roomsId = BookingRepository.execute().getAll().stream()
                .filter(b -> b.getArrive().isBetween(from, to)
                        || b.getDeparture().isBetween(from, to)
                        || b.getArrive().isAfterThen(from) != b.getDeparture().isAfterThen(to))
                .map(b -> b.getRoom().getId())
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Room> rooms = RoomRepository.execute().getAll(true);
        rooms.removeIf(r -> roomsId.contains(r.getId()));

        return rooms;
    }

    public static Date getDate(DatePicker picker) throws DateFormatException {
        return new Date(picker.getEditor().getText());
    }
}
