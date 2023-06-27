package it.polimi.ingsw.client.view.GUI.controllers;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.ClientConnection;
import it.polimi.ingsw.client.connection.RMISender;
import it.polimi.ingsw.client.connection.TCPReceiver;
import it.polimi.ingsw.client.connection.TCPSender;
import it.polimi.ingsw.client.localModel.LocalBoard;
import it.polimi.ingsw.client.localModel.LocalCommonCard;
import it.polimi.ingsw.client.localModel.LocalPersonalCard;
import it.polimi.ingsw.client.view.GUI.AlertBox;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.controller.ClientSkeleton;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.ControllerSkeleton;
import it.polimi.ingsw.controller.Settings;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 * Controller class for the setup functionality in the GUI.
 */
public class SetupController implements GUIController {
    private GUI gui;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField ipAddress;

    /**
     * Sets the GUI object in the controller.
     *
     * @param gui The GUI object to be set.
     */
    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * Retrieves the GUI object associated with the controller.
     *
     * @return The GUI object associated with the controller.
     */
    @Override
    public GUI getGui() {
        return this.gui;
    }

    public void init(){}

    /**
     * Event handler for the TCP connection button.
     * Retrieves the IP address entered by the user and attempts to establish a TCP connection.
     * Displays an alert box if an error occurs during the connection.
     */
    @FXML
    public void onTCPButton() {
        String ip = getIpAddress();
        try {
            gui.setTCPConnection(ip);
        } catch (IOException e) {
            int exitStatus = AlertBox.errorData(gui.getPrimaryStage(), "Something goes wrong. Please retry.", "Error");
        } catch (Exception e) {
            int exitStatus = AlertBox.errorData(gui.getPrimaryStage(), "Something goes wrong. Please retry.", "Error");
        }
    }

    /**
     * Event handler for the RMI connection button.
     * Retrieves the IP address entered by the user and attempts to establish an RMI connection.
     * Displays an alert box if an error occurs during the connection.
     */
    @FXML
    public void onRMIButton() {
        String ip = getIpAddress();
        try {
            gui.setRMIConnection(ip);
        } catch (RemoteException e) {
            int exitStatus = AlertBox.errorData(gui.getPrimaryStage(), "Something goes wrong. Please retry.", "Error");
        } catch (NotBoundException e) {
            int exitStatus = AlertBox.errorData(gui.getPrimaryStage(), "Something goes wrong. Please retry.", "Error");
        } catch (Exception e) {
            int exitStatus = AlertBox.errorData(gui.getPrimaryStage(), "Something goes wrong. Please retry.", "Error");
        }
    }

    private String getIpAddress() {
        String ip = ipAddress.getText();
        if (ip.equals("")) {
            return "localhost";
        } else {
            return ip;
        }
    }
}
