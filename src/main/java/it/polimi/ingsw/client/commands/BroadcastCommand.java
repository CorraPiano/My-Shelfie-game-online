package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.Sender;

import java.util.Arrays;
import java.util.Scanner;

public class BroadcastCommand implements Command {

    public void execute(Sender sender, Scanner stdin, Client client) {
        String message = stdin.nextLine();
        sender.addChatMessage(message);
    }

    public void execute(Sender sender, Scanner stdin, String line) {
        String message = stdin.nextLine();
        sender.addChatMessage(line+message);
    }
}
