module it.polimi.ingsw {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires jdk.jdi;
    requires java.desktop;
    requires java.rmi;
    requires com.google.gson;


    exports it.polimi.ingsw.controller;
    exports it.polimi.ingsw.client.view;
    exports it.polimi.ingsw.model;
    exports it.polimi.ingsw.client.view.GUI;
    exports it.polimi.ingsw.connection;
    exports it.polimi.ingsw.connection.message;
    exports it.polimi.ingsw.client.localModel;
    exports it.polimi.ingsw.exception;
    exports it.polimi.ingsw.client.view.TUI;
    exports it.polimi.ingsw.client;


    opens it.polimi.ingsw.client.view.GUI.controllers to javafx.fxml;
    opens it.polimi.ingsw.client.view.GUI to javafx.fxml;
    opens it.polimi.ingsw.client.view to javafx.fxml;
    opens it.polimi.ingsw.model;
    opens it.polimi.ingsw.connection.message ;
    opens it.polimi.ingsw.connection;
    opens it.polimi.ingsw.client.view.TUI to javafx.fxml;
    opens it.polimi.ingsw.client.localModel to javafx.fxml;
    opens it.polimi.ingsw.client to javafx.fxml;
    opens it.polimi.ingsw.controller to java.rmi;
    opens it.polimi.ingsw.client.connection to java.rmi;
    exports it.polimi.ingsw.util;
    opens it.polimi.ingsw.util to javafx.fxml;
    exports it.polimi.ingsw.client.view.utils;
    opens it.polimi.ingsw.client.view.utils to javafx.fxml;
}