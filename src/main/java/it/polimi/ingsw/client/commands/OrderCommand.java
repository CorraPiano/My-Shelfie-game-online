package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.Sender;
import java.util.ArrayList;
import java.util.Scanner;

import static it.polimi.ingsw.util.Constants.ANSI_RESET;
import static it.polimi.ingsw.util.Constants.ANSI_YELLOW;

/**
 * The `OrderCommand` class represents a command to select the order of items to be inserted.
 * It implements the `Command` interface.
 */
public class OrderCommand implements Command {

    /**
     * Executes the command to select the order of items to be inserted.
     *
     * @param sender The sender object responsible for selecting the order.
     * @param stdin  The `Scanner` object used for reading user input.
     * @param client The `Client` object representing the client application.
     */
    public void execute(Sender sender, Scanner stdin, Client client) {
        int size = client.getHandSize();
        String line = "";
        ArrayList<Integer> list = new ArrayList<>();
        int counter = 0;
        while (counter < size) {
            if (stdin.hasNext()) {
                line = stdin.next();
                try {
                    int n = Integer.parseInt(line);
                    if (n >= 0) {
                        list.add(n);
                        counter++;
                    } else {
                        System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Is required a list of " + size + " positive integers");
                        return;
                    }
                } catch (Exception e) {
                    System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Is required a list of " + size + " positive integers");
                    return;
                }
            }
        }

        if (counter != size)
            System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Is required a list of " + size + " positive integers");
        else
            sender.selectInsertOrder(list);
    }
}