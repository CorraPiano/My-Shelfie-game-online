package it.polimi.ingsw.client.view.GUI.controllers;

import it.polimi.ingsw.client.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;

import java.awt.*;
import java.awt.event.ActionEvent;

public class ChatController implements GUIController {
    private GUI gui;

    //@FXML
    //private TextField chatMessage;
    @FXML
    private ListView<String> chatField;
    private MenuButton recipient;


    public void sendMessage(ActionEvent event) {
        //String message = chatMessage.getText();
        //String reciever = recipient.getItems().toString(); //non ho la più pallida idea se si faccia così o meno
        //gui.sendMessage(message, reciever);
        //displayMessage(message);

    }


    public void displayMessage(String message){
        chatField.getItems().add(message);
    }

    @Override
    public void setGui(GUI gui) { this.gui = gui; }

    @Override
    public GUI getGui() { return this.gui; }

    @Override
    public void init() {}
}
