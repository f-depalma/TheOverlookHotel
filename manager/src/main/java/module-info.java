module com.toh {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.toh.gui to javafx.fxml;
    exports com.toh.gui;
    exports com.toh.gui.controller;
    opens com.toh.gui.controller to javafx.fxml;
    exports com.toh.gui.dto;
    opens com.toh.gui.dto to javafx.fxml;
}