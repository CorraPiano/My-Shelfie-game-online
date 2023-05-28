module it.polimi.ingsw {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires jdk.jdi;
    requires java.desktop;
    requires java.rmi;
    requires com.google.gson;

    opens it.polimi.ingsw to javafx.fxml;
    exports it.polimi.ingsw;
    exports it.polimi.ingsw.controller;
    exports it.polimi.ingsw.client.view;
    exports it.polimi.ingsw.model;

    opens it.polimi.ingsw.client.view.GUI to javafx.fxml;
    exports it.polimi.ingsw.client.view.GUI;
    opens it.polimi.ingsw.client.view.GUI.controllers to javafx.fxml;

    opens it.polimi.ingsw.client.view to javafx.fxml;
    exports it.polimi.ingsw.connection;

    exports it.polimi.ingsw.connection.message;
    exports it.polimi.ingsw.client.localModel;
    exports it.polimi.ingsw.exception;

    opens it.polimi.ingsw.model;

    opens it.polimi.ingsw.connection.message ;
    opens it.polimi.ingsw.connection;
    exports it.polimi.ingsw.client.view.TUI;
    opens it.polimi.ingsw.client.view.TUI to javafx.fxml;
}