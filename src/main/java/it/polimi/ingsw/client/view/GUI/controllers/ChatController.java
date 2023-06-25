package it.polimi.ingsw.client.view.GUI.controllers;

import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.connection.message.ChatMessage;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;


import java.util.Objects;

/**
 * The controller class for managing the chat functionality in the GUI.
 */
public class ChatController implements GUIController {
    private GUI gui;

    @FXML
    private Button sendButton;
    @FXML
    private TextField chatMessage;
    @FXML
    private TextField receiver;
    @FXML
    private ListView<String> chatField;

    /**
     * Sends the chat message to the intended receiver or to all participants.
     *
     * @param event The action event triggered by clicking the send button.
     */
    public void sendMessage(ActionEvent event) {
        String message = chatMessage.getText();
        if (Objects.equals(receiver.getText(), "")) {
            gui.sendMessage(message, null);
        } else {
            String receiverName = receiver.getText();
            gui.sendMessage(message, receiverName);
        }
    }

    /**
     * Displays a chat message in the chat field.
     *
     * @param chatMessage The chat message to be displayed.
     * @param name        The name of the local player.
     */
    public void displayMessage(ChatMessage chatMessage, String name) {
        if (chatMessage.receiver == null && !Objects.equals(chatMessage.sender, name)) {
            chatField.getItems().add("<TO ALL> " + chatMessage.sender + ": " + chatMessage.message);
        } else if (chatMessage.receiver == null && Objects.equals(chatMessage.sender, name)) {
            chatField.getItems().add("<YOU TO ALL> " + chatMessage.message);
        } else if (chatMessage.receiver != null && Objects.equals(chatMessage.sender, name)) {
            chatField.getItems().add("<YOU TO " + chatMessage.receiver + "> " + chatMessage.message);
        } else {
            chatField.getItems().add("<TO YOU> " + chatMessage.sender + ": " + chatMessage.message);
        }
    }

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
}