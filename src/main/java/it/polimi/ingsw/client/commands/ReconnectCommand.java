package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientTUI;
import it.polimi.ingsw.client.connection.Sender;
import it.polimi.ingsw.client.LocalSave;
import it.polimi.ingsw.client.view.TUI.OutputHandler;

import java.util.Scanner;

import static it.polimi.ingsw.util.Constants.ANSI_RESET;
import static it.polimi.ingsw.util.Constants.ANSI_YELLOW;

/**
 * The `ReconnectCommand` class represents a command to reconnect to a game session.
 * It implements the `Command` interface.
 */
public class ReconnectCommand {

    /**
     * Executes the command to reconnect to a game session.
     *
     * @param sender The sender object responsible for reconnecting to the game session.
     * @param stdin  The `Scanner` object used for reading user input.
     * @param client The `Client` object representing the client application.
     */
    public void execute(Sender sender, Scanner stdin, ClientTUI client) {
        String id;
        OutputHandler outputHandler = client.getOutputHandler();
        /* id = LocalSave.recoverID();
        if(id==null) {
            System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "There isn't a game to reconnect");
            return;
        }*/

        try {
            String name = stdin.next();
            int gameID = stdin.nextInt();
            id = name + "_" + gameID;
        } catch (Exception e) {
            outputHandler.showError("invalid command parameters");
            //System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "\n" + "invalid command parameters");
            return;
        }

        sender.reconnectGame(id,false);
    }
}