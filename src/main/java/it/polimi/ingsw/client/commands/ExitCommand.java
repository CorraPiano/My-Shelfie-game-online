package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.Sender;

import java.util.Scanner;

import static it.polimi.ingsw.util.Constants.ANSI_RESET;
import static it.polimi.ingsw.util.Constants.ANSI_YELLOW;

public class ExitCommand {
    public void execute(Sender sender, Scanner stdin, Client client) {
        System.out.println(ANSI_YELLOW + "❮GOODBYE❯ " + ANSI_RESET + "Thank You for playing!");
        System.out.println(ANSI_YELLOW + "❮GOODBYE❯ " + ANSI_RESET + "See you soon!");
        System.exit(0);
    }
}
