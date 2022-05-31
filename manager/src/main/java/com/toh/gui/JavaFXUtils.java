package com.toh.gui;

import com.toh.database.core.Exceptions.DateFormatException;
import com.toh.database.core.field.Date;
import com.toh.database.entity.Room;
import com.toh.database.repository.BookingRepository;
import com.toh.database.repository.RoomRepository;
import javafx.scene.control.DatePicker;
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
        dp.setConverter(new StringConverter<>() {
            String pattern = "dd/MM/yyyy";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            {
                dp.setPromptText(pattern.toLowerCase());
            }

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return formatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String date) {
                if (date != null && !date.isEmpty()) {
                    return LocalDate.parse(date, formatter);
                } else {
                    return null;
                }
            }
        });
    }

    public static ArrayList<Room> findAvailableRoom(Date from, Date to) {
        //find all the unavailable room in the interval from - to
        ArrayList<Integer> unavailableRoomsId = BookingRepository.execute().getAll().stream()
                .filter(b -> (!b.getArrive().isBeforeThen(from) && b.getArrive().isBeforeThen(to))
                        || (b.getDeparture().isAfterThen(from) && !b.getDeparture().isAfterThen(to))
                        || (!b.getArrive().isAfterThen(from) && !b.getDeparture().isBeforeThen(to)))
                .map(b -> b.getRoom().getId())
                .collect(Collectors.toCollection(ArrayList::new));

        //remove all the unavailable room
        ArrayList<Room> rooms = RoomRepository.execute().getAll(true);
        rooms.removeIf(r -> unavailableRoomsId.contains(r.getId()));

        return rooms;
    }

    public static Date getDate(DatePicker picker) throws DateFormatException {
        return new Date(picker.getEditor().getText());
    }
}
