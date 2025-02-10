package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientTUI;
import it.polimi.ingsw.client.connection.Sender;
import it.polimi.ingsw.client.view.TUI.OutputHandler;

import java.util.ArrayList;
import java.util.Scanner;

import static it.polimi.ingsw.util.Constants.ANSI_RESET;
import static it.polimi.ingsw.util.Constants.ANSI_YELLOW;

/**
 * The `OrderCommand` class represents a command to select the order of items to be inserted.
 * It implements the `Command` interface.
 */
public class OrderCommand {

    /**
     * Executes the command to select the order of items to be inserted.
     *
     * @param sender The sender object responsible for selecting the order.
     * @param stdin  The `Scanner` object used for reading user input.
     * @param client The `Client` object representing the client application.
     */
    public void execute(Sender sender, Scanner stdin, ClientTUI client) {
        OutputHandler outputHandler = client.getOutputHandler();
        int size = client.getHandSize();
        String line = "";
        ArrayList<Integer> list = new ArrayList<>();
        int counter = 0;
        while (counter < size) {
            try {
                if (stdin.hasNext()) {
                    line = stdin.next();
                    int n = Integer.parseInt(line);
                    if (n >= 0) {
                        list.add(n);
                        counter++;
                    } else {
                        outputHandler.showError("Is required a list of " + size + " positive integers");
                        //System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Is required a list of " + size + " positive integers");
                        return;
                    }
                }
            } catch (Exception e) {
                outputHandler.showError("Is required a list of " + size + " positive integers");
                //System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Is required a list of " + size + " positive integers");
                return;
            }
        }

        if (counter != size)
            outputHandler.showError("Is required a list of " + size + " positive integers");
            //System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Is required a list of " + size + " positive integers");
        else
            sender.selectInsertOrder(list);
    }
}