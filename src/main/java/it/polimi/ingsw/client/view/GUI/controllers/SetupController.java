package it.polimi.ingsw.client.view.GUI.controllers;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.ClientConnection;
import it.polimi.ingsw.client.connection.RMISender;
import it.polimi.ingsw.client.connection.TCPReceiver;
import it.polimi.ingsw.client.connection.TCPSender;
import it.polimi.ingsw.client.localModel.LocalBoard;
import it.polimi.ingsw.client.localModel.LocalCommonCard;
import it.polimi.ingsw.client.localModel.LocalPersonalCard;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.controller.ClientSkeleton;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.ControllerSkeleton;
import it.polimi.ingsw.controller.Settings;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class SetupController implements GUIController {
    private GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    @Override
    public GUI getGui() { return this.gui; }

    @FXML
    public void onTCPButton(){
        try {
            gui.setTCPConnection();
        } catch (IOException e) {
            // Alert box
        }
    }
    @FXML
    public void onRMIButton(){
        try {
            gui.setRMIConnection();
        } catch (RemoteException e) {
            // Alert box
        } catch (NotBoundException e) {
            // Alert box
        }
    }

}
