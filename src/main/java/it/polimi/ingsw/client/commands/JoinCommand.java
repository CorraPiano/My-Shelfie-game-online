package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientTUI;
import it.polimi.ingsw.client.connection.Sender;
import it.polimi.ingsw.client.view.TUI.OutputHandler;

import java.util.Objects;
import java.util.Scanner;

import static it.polimi.ingsw.util.Constants.ANSI_RESET;
import static it.polimi.ingsw.util.Constants.ANSI_YELLOW;

/**
 * The `JoinCommand` class represents a command to join a game.
 * It implements the `Command` interface.
 */
public class JoinCommand {

    /**
     * Executes the command to join a game.
     *
     * @param sender The sender object responsible for joining the game.
     * @param stdin  The `Scanner` object used for reading user input.
     * @param client The `Client` object representing the client application.
     */
    public void execute(Sender sender, Scanner stdin, ClientTUI client) {
        String name;
        int gameID;
        OutputHandler outputHandler = client.getOutputHandler();

        try {
            name = stdin.next();
            gameID = stdin.nextInt();
        } catch (Exception e) {
            outputHandler.showError("invalid command parameters");
            //System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "\n" + "invalid command parameters");
            return;
        }

        if (Objects.equals(name, "")) {
            outputHandler.showError("The name entered is not valid");
            //System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "The name entered is not valid");
            return;
        }

        if (gameID < 0) {
            outputHandler.showError("The gameID entered is not valid");
            //System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "The gameID entered is not valid");
            return;
        }

        //client.putInWait();
        sender.addPlayer(name, gameID);
    }
}