package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.Sender;
import java.util.Scanner;


/**
 * The `SendCommand` class represents a command to send a chat message to a specific recipient.
 * It implements the `Command` interface.
 */
public class SendCommand implements Command {

    /**
     * Executes the command to send a chat message.
     *
     * @param sender The sender object responsible for sending the chat message.
     * @param stdin  The `Scanner` object used for reading user input.
     * @param client The `Client` object representing the client application.
     */
    public void execute(Sender sender, Scanner stdin, Client client) {
        // Insert controls if needed
        String receiver = stdin.next();
        String message = stdin.nextLine();
        sender.addChatMessage(message, receiver);
    }
}