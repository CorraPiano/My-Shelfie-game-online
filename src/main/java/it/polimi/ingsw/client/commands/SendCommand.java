package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientTUI;
import it.polimi.ingsw.client.connection.Sender;
import it.polimi.ingsw.client.view.TUI.OutputHandler;

import java.util.Scanner;


/**
 * The `SendCommand` class represents a command to send a chat message to a specific recipient.
 * It implements the `Command` interface.
 */
public class SendCommand{

    /**
     * Executes the command to send a chat message.
     *
     * @param sender The sender object responsible for sending the chat message.
     * @param stdin  The `Scanner` object used for reading user input.
     * @param client The `Client` object representing the client application.
     */
    public void execute(Sender sender, Scanner stdin, ClientTUI client) {
        OutputHandler outputHandler = client.getOutputHandler();
        // Insert controls if needed
        String receiver;
        String message;
        try {
            receiver = stdin.next();
            message = stdin.nextLine();
        } catch (Exception e){
            outputHandler.showError("invalid input!");
            return;
        }

        sender.addChatMessage(message, receiver);
    }
}