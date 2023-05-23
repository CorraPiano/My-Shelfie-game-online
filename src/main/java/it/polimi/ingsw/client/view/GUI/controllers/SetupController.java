package it.polimi.ingsw.client.view.GUI.controllers;

import it.polimi.ingsw.client.view.GUI.GUI;

public class SetupController implements GUIController {
    private GUI gui;
    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    @Override
    public GUI getGui() { return this.gui; }

}
