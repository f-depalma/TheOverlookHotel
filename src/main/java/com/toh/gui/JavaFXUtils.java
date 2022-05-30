package com.toh.gui;

import com.toh.database.core.Exceptions.DateFormatException;
import com.toh.database.core.field.Date;
import javafx.scene.control.TextField;

public class JavaFXUtils {
    public static void dateValidation(TextField input) {
        input.textProperty().addListener((obs, oldText, newText) -> {
            try {
                new Date(newText);
                input.getStyleClass().remove("error");
            } catch (DateFormatException e) {
                if (!input.getStyleClass().contains("error"))
                    input.getStyleClass().add("error");
            }
        });
    }
}
