package it.polimi.ingsw.client.view.GUI.controllers;

import it.polimi.ingsw.client.localModel.LocalPlayer;
import it.polimi.ingsw.client.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Objects;

import static it.polimi.ingsw.util.Constants.ANSI_CYAN;
import static it.polimi.ingsw.util.Constants.ANSI_RESET;

/**
 * The `EndController` class is responsible for managing the end scene of the game.
 * It displays the result (victory or place) of the player and provides methods to handle actions such as showing statistics and quitting the game.
 */
public class EndController implements GUIController {

    @FXML
    Label title;

    /**
     * Updates the label with the result (victory or place) of the player.
     *
     * @param name          The name of the player.
     * @param localPlayers  The list of local players.
     */
    public void updateLabel(String name, ArrayList<LocalPlayer> localPlayers) {
        String result = "";
        for (int i = 0; i < localPlayers.size(); i++) {
            if (Objects.equals(localPlayers.get(i).name, name)) {
                if (i == 0) {
                    result = "Victory";
                    break;
                } else if (i == 1) {
                    result = "2nd place";
                    break;
                } else if (i == 2) {
                    result = "3rd place";
                    break;
                } else {
                    result = "4th place";
                    break;
                }
            }
        }
        title.setText(result);
    }

    /**
     * Handles the action when the statistics button is clicked.
     */
    public void onShowStatistics() {
        // TODO: Implement this method
    }

    /**
     * Handles the action when the quit button is clicked.
     */
    public void onQuit() {
        // TODO: Implement this method
    }

    /**
     * Sets the GUI object in the controller.
     *
     * @param gui The GUI object to be set.
     */
    @Override
    public void setGui(GUI gui) {
        // TODO: Implement this method
    }

    /**
     * Retrieves the GUI object associated with the controller.
     *
     * @return The GUI object associated with the controller.
     */
    @Override
    public GUI getGui() {
        return null; // TODO: Implement this method
    }
}
