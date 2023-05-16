module it.polimi.ingsw {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires jdk.jdi;
    requires java.desktop;
    requires java.rmi;

    opens it.polimi.ingsw to javafx.fxml;
    exports it.polimi.ingsw;
    exports it.polimi.ingsw.client.view;
    opens it.polimi.ingsw.client.view to javafx.fxml;
}