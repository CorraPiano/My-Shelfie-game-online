package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.Sender;
import java.util.Scanner;

public class SendCommand implements Command {

    public void execute(Sender sender, Scanner stdin, Client client) {
        // inserire controlli
        String receiver = stdin.next();
        String message = stdin.nextLine();
        sender.addChatMessage(message,receiver);
    }
}
