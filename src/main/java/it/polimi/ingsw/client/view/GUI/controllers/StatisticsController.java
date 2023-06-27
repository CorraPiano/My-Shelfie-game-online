package it.polimi.ingsw.client.view.GUI.controllers;

import it.polimi.ingsw.client.localModel.LocalBookshelf;
import it.polimi.ingsw.client.localModel.LocalPlayer;
import it.polimi.ingsw.client.view.GUI.GUI;

import java.util.ArrayList;
import java.util.Map;


/**
 * Controller class for managing statistics in the GUI.
 */
public class StatisticsController implements GUIController {

    /**
     * Updates the bookshelfs with the given information.
     *
     * @param name                The name of the player.
     * @param localBookshelfMap   A map containing the local bookshelfs of the players.
     * @param localPlayers        An ArrayList of LocalPlayer objects representing the players.
     */
    public void updateBookshelfs(String name, Map<String, LocalBookshelf> localBookshelfMap, ArrayList<LocalPlayer> localPlayers) {
        // TODO: Implement the method logic
    }

    /**
     * Sets the GUI object in the controller.
     *
     * @param gui The GUI object to be set.
     */
    @Override
    public void setGui(GUI gui) {
        // TODO: Implement the method logic
    }

    /**
     * Retrieves the GUI object associated with the controller.
     *
     * @return The GUI object associated with the controller.
     */
    @Override
    public GUI getGui() {
        return null;  // TODO: Implement the method logic
    }

    public void init(){};
}
