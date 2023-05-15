module it.polimi.ingsw.am26 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires jdk.jdi;
    requires java.desktop;
    requires java.rmi;

    opens it.polimi.ingsw to javafx.fxml;
    exports it.polimi.ingsw;
}