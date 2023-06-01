package it.polimi.ingsw.client.view.GUI.controllers;

import it.polimi.ingsw.client.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;


import java.awt.*;

public class ChatController implements GUIController {
    private GUI gui;

    @FXML
    Button sendButton;
    @FXML
    TextField chatMessage;
    @FXML
    private ListView<String> chatField;
    private MenuButton recipient;


    public void sendMessage(ActionEvent event) {
        String message = chatMessage.getText();
        //String reciever = recipient.getItems().toString(); //non ho la più pallida idea se si faccia così o meno
        String reciever = null;
        gui.sendMessage(message, reciever);
        displayMessage("YOU TO " + reciever + ": " + message);
    }

    public void displayMessage(String message){
        chatField.getItems().add(message);
    }
    public void updateChat(String message, String name){}

    //gestire la scelta di "a chi" inviare il messaggio

    @Override
    public void setGui(GUI gui) { this.gui = gui; }

    @Override
    public GUI getGui() { return this.gui; }

    @Override
    public void init() {}
}
