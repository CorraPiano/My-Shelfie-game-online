package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientTUI;
import it.polimi.ingsw.client.connection.Sender;
import it.polimi.ingsw.client.view.TUI.OutputHandler;

import java.util.Scanner;

import static it.polimi.ingsw.util.Constants.ANSI_RESET;
import static it.polimi.ingsw.util.Constants.ANSI_YELLOW;

/**
 * The `PutCommand` class represents a command to put an item in a specific column.
 * It implements the `Command` interface.
 */
public class PutCommand{

    /**
     * Executes the command to put an item in a specific column.
     *
     * @param sender The sender object responsible for putting the item.
     * @param stdin  The `Scanner` object used for reading user input.
     * @param client The `Client` object representing the client application.
     */
    public void execute(Sender sender, Scanner stdin, ClientTUI client) {
        int n;
        OutputHandler outputHandler = client.getOutputHandler();

        try {
            n = stdin.nextInt();
        } catch (Exception e) {
            outputHandler.showError("invalid command parameters");
            //System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "\n" + "invalid command parameters");
            return;
        }

        if (n < 0 || n >= 5) {
            outputHandler.showError("The column entered is not valid");
            //ystem.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "The column entered is not valid");
            return;
        }

        sender.putItemList(n);
    }
}
