package it.polimi.ingsw.client.view.GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * The `AlertBox` class provides utility methods for displaying alert dialogs in a JavaFX application.
 */
public class AlertBox {

    /**
     * Displays an exit confirmation dialog.
     *
     * @param mainStage    The main stage of the application.
     * @param windowEvent  The window event representing the close request.
     * @param message      The message to display in the dialog.
     * @return The status code indicating the user's choice: 1 for "Yes", 2 for "No".
     */
    public static int exitRequest(Stage mainStage, WindowEvent windowEvent, String message) {
        double WIDTH = 400;
        double HEIGHT = 100;

        AtomicInteger status = new AtomicInteger(0);
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Warning");
        alert.setContentText(message);

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        // calculate position
        double x = mainStage.getX();
        double y = mainStage.getY();
        double center_x = x + mainStage.getScene().getWidth() / 2;
        double center_y = y + mainStage.getScene().getHeight() / 2;

        alert.setX(center_x - WIDTH / 2);
        alert.setY(center_y - HEIGHT / 2);


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

    /**
     * Displays an error dialog with custom title.
     *
     * @param mainStage  The main stage of the application.
     * @param message    The message to display in the dialog.
     * @param title      The title of the dialog.
     * @return The status code indicating the user's choice: 1 for "Ok".
     */
    public static int errorData(Stage mainStage, String message, String title) {
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
        double y = mainStage.getY();
        double center_x = x + mainStage.getScene().getWidth() / 2;
        double center_y = y + mainStage.getScene().getHeight() / 2;

        alert.setX(center_x - WIDTH / 2);
        alert.setY(center_y - HEIGHT / 2);

        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                alert.close();
                status.set(1);
            }
        });
        return status.get();
    }

    public static int forceClosed(Stage mainStage, String title,  String message) {
        double WIDTH = 400;
        double HEIGHT = 100;

        AtomicInteger status = new AtomicInteger(0);
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(title);
        alert.setContentText(message);

        ButtonType buttonTypeYes = new ButtonType("OK");

        // calculate position
        double x = mainStage.getX();
        double y = mainStage.getY();
        double center_x = x + mainStage.getScene().getWidth() / 2;
        double center_y = y + mainStage.getScene().getHeight() / 2;

        alert.setX(center_x - WIDTH / 2);
        alert.setY(center_y - HEIGHT / 2);


        alert.getButtonTypes().setAll(buttonTypeYes);
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