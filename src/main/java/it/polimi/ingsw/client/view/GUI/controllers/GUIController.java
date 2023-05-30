package it.polimi.ingsw.client.view.GUI.controllers;

import it.polimi.ingsw.client.localModel.LocalBoard;
import it.polimi.ingsw.client.localModel.LocalCommonCard;
import it.polimi.ingsw.client.localModel.LocalPersonalCard;
import it.polimi.ingsw.client.view.GUI.GUI;

import java.util.ArrayList;

public interface GUIController {
    void setGui(GUI gui);
    GUI getGui();
    void init();

}