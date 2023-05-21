package it.polimi.ingsw.client.view.GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class ConfirmationBox {
    public static int exitRequest(Stage mainStage, WindowEvent windowEvent, String message){
        AtomicInteger status = new AtomicInteger(0);
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Warning");
        alert.setContentText(message);

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                alert.close();
                mainStage.close();
                status.set(1);

            } else {
                status.set(2);
                alert.close();
            }
        });
        return status.get();
    }

}
