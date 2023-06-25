package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.Sender;
import java.util.Scanner;

import static it.polimi.ingsw.util.Constants.ANSI_RESET;
import static it.polimi.ingsw.util.Constants.ANSI_YELLOW;

/**
 * The `PutCommand` class represents a command to put an item in a specific column.
 * It implements the `Command` interface.
 */
public class PutCommand implements Command {

    /**
     * Executes the command to put an item in a specific column.
     *
     * @param sender The sender object responsible for putting the item.
     * @param stdin  The `Scanner` object used for reading user input.
     * @param client The `Client` object representing the client application.
     */
    public void execute(Sender sender, Scanner stdin, Client client) {
        int n;

        try {
            n = stdin.nextInt();
        } catch (Exception e) {
            System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "\n" + "invalid command parameters");
            return;
        }

        if (n < 0 || n >= 5) {
            System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "The column entered is not valid");
            return;
        }

        sender.putItemList(n);
    }
}
