package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.Sender;
import it.polimi.ingsw.model.Coordinates;
import java.util.Scanner;

import static it.polimi.ingsw.util.Constants.ANSI_RESET;
import static it.polimi.ingsw.util.Constants.ANSI_YELLOW;

/**
 * The `PickCommand` class represents a command to pick an item at specific coordinates.
 * It implements the `Command` interface.
 */
public class PickCommand implements Command {

    /**
     * Executes the command to pick an item at specific coordinates.
     *
     * @param sender The sender object responsible for picking the item.
     * @param stdin  The `Scanner` object used for reading user input.
     * @param client The `Client` object representing the client application.
     */
    public void execute(Sender sender, Scanner stdin, Client client) {
        int n1;
        int n2;

        try {
            n1 = stdin.nextInt();
            n2 = stdin.nextInt();
        } catch (Exception e) {
            System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "\n" + "invalid command parameters");
            return;
        }

        if (n1 < 0 || n1 > 9 || n2 < 0 || n2 > 9) {
            System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "The coordinates entered are not valid");
            return;
        }

        Coordinates coord = new Coordinates(n1, n2);
        sender.pickItem(coord);
    }
}
