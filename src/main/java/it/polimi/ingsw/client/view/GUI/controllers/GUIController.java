package it.polimi.ingsw.client.view.GUI.controllers;

import it.polimi.ingsw.client.localModel.LocalBoard;
import it.polimi.ingsw.client.localModel.LocalCommonCard;
import it.polimi.ingsw.client.localModel.LocalPersonalCard;
import it.polimi.ingsw.client.view.GUI.GUI;

import java.util.ArrayList;

/**
 * The interface for GUI controllers that handle interactions between the GUI and the game logic.
 */
public interface GUIController {

    /**
     * Sets the GUI object in the controller.
     *
     * @param gui The GUI object to be set.
     */
    void setGui(GUI gui);

    /**
     * Retrieves the GUI object associated with the controller.
     *
     * @return The GUI object associated with the controller.
     */
    GUI getGui();
    void init();
}