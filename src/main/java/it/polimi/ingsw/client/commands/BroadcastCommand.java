package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientTUI;
import it.polimi.ingsw.client.connection.Sender;

import java.util.Arrays;
import java.util.Scanner;

/**
 * The `BroadcastCommand` class represents a command to broadcast a message to all participants.
 * It provides two `execute` methods to perform the broadcasting functionality.
 */
public class BroadcastCommand {

    /**
     * Executes the command to broadcast a message.
     *
     * @param sender The sender object responsible for executing the command.
     * @param stdin  The `Scanner` object used for reading user input.
     * @param client The `Client` object representing the client application.
     */
    public void execute(Sender sender, Scanner stdin, ClientTUI client) {
        String message;
        try{
            message = stdin.nextLine();
        }
        catch(Exception e){
            client.getOutputHandler().showError("invalid input!");
            return;
        }

        sender.addChatMessage(message);
    }

    /**
     * Executes the command to broadcast a message with a specified prefix.
     *
     * @param sender The sender object responsible for executing the command.
     * @param stdin  The `Scanner` object used for reading user input.
     * @param line   The prefix to prepend to the broadcasted message.
     */
    public void execute(Sender sender, Scanner stdin, String line, ClientTUI client) {
        String message;
        try{
            message = stdin.nextLine();
        }
        catch(Exception e){
            client.getOutputHandler().showError("invalid input!");
            return;
        }
        sender.addChatMessage(line + message);
    }
}