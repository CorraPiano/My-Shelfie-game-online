package it.polimi.ingsw.client.view.GUI.controllers;

import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.connection.message.ChatMessage;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;


import java.util.Objects;

public class ChatController implements GUIController {
    private GUI gui;

    @FXML
    Button sendButton;
    @FXML
    TextField chatMessage;
    @FXML
    TextField receiver;
    @FXML
    private ListView<String> chatField;


    public void sendMessage(ActionEvent event) {
        String message = chatMessage.getText();
        if(Objects.equals(receiver.getText(), "")){
            gui.sendMessage(message, null);
        } else {
            String receiverName = receiver.getText();
            gui.sendMessage(message, receiverName);
        }
    }

    public void displayMessage(ChatMessage chatMessage, String name){
        if (chatMessage.receiver == null && !Objects.equals(chatMessage.sender, name)){
            chatField.getItems().add("<TO ALL> " + chatMessage.sender + ": " + chatMessage.message);
        } else if (chatMessage.receiver == null && Objects.equals(chatMessage.sender, name)) {
            chatField.getItems().add("<YOU TO ALL> " + chatMessage.message);
        } else if (chatMessage.receiver != null && Objects.equals(chatMessage.sender, name)){
            chatField.getItems().add("<YOU TO " + chatMessage.receiver + "> " + chatMessage.message);
        } else {
            chatField.getItems().add("<TO YOU> " + chatMessage.sender + ": " + chatMessage.message);
        }
    }

    @Override
    public void setGui(GUI gui) { this.gui = gui; }

    @Override
    public GUI getGui() { return this.gui; }
}
