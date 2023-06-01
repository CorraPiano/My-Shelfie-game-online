package it.polimi.ingsw.client.view.GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class AlertBox {
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

    public static int errorData(Stage mainStage, String message, String title){
        double WIDTH = 400;
        double HEIGHT = 100;

        AtomicInteger status = new AtomicInteger(0);
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(title);
        alert.setContentText(message);

        ButtonType buttonTypeYes = new ButtonType("Ok");
        alert.getButtonTypes().setAll(buttonTypeYes);

        // calculate position
        double x = mainStage.getX();
        double y =  mainStage.getY();
        double center_x = x +  mainStage.getScene().getWidth()/2;
        double center_y = y + mainStage.getScene().getHeight()/2;

        alert.setX(center_x - WIDTH/2);
        alert.setY(center_y - HEIGHT/2);

        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                alert.close();
                status.set(1);

            }
        });
        return status.get();
    }

}
