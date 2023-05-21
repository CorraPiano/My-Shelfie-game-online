module it.polimi.ingsw {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires jdk.jdi;
    requires java.desktop;
    requires java.rmi;

    opens it.polimi.ingsw to javafx.fxml;
    exports it.polimi.ingsw;


    opens it.polimi.ingsw.client.view.GUI to javafx.fxml;
    exports it.polimi.ingsw.client.view.GUI;
    opens it.polimi.ingsw.client.view.GUI.controllers to javafx.fxml;

}