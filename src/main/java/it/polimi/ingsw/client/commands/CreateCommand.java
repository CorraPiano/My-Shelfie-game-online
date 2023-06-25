package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.Sender;
import it.polimi.ingsw.model.GameMode;
import java.util.Objects;
import java.util.Scanner;

import static it.polimi.ingsw.util.Constants.ANSI_RESET;
import static it.polimi.ingsw.util.Constants.ANSI_YELLOW;

/**
 * The `CreateCommand` class represents a command to create a new game.
 * It implements the `Command` interface.
 */
public class CreateCommand implements Command {

    /**
     * Executes the command to create a new game.
     *
     * @param sender The sender object responsible for creating the game.
     * @param stdin  The `Scanner` object used for reading user input.
     * @param client The `Client` object representing the client application.
     */
    public void execute(Sender sender, Scanner stdin, Client client) {
        String name;
        String gameModeString;
        GameMode gameMode;
        int numPlayers;

        try {
            name = stdin.next();
            gameModeString = stdin.next();
            numPlayers = stdin.nextInt();
        } catch (Exception e) {
            System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "\n" + "invalid command parameters");
            return;
        }

        if (Objects.equals(name, "")) {
            System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "The name should not be empty");
            return;
        }

        if (gameModeString.equalsIgnoreCase("EASY") || gameModeString.equals("0"))
            gameMode = GameMode.EASY;
        else if (gameModeString.equalsIgnoreCase("EXPERT") || gameModeString.equals("1"))
            gameMode = GameMode.EXPERT;
        else {
            System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "The game mode entered is not valid");
            return;
        }

        if (numPlayers < 2 || numPlayers > 4) {
            System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "The number of players must be between 2 and 4");
            return;
        }

        //client.putInWait();
        sender.addFirstPlayer(name, gameMode, numPlayers);
    }
}
