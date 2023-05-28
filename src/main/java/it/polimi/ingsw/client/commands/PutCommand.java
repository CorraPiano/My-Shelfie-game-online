package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.Sender;
import java.util.Scanner;

import static it.polimi.ingsw.util.Constants.ANSI_RESET;
import static it.polimi.ingsw.util.Constants.ANSI_YELLOW;

public class PutCommand  implements Command {

    public void execute(Sender sender, Scanner stdin, Client client) {
        int n = stdin.nextInt();
        if (n <0 || n>=5) {
            System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "The column entered are not correct");
            return;
        }

        sender.putItemList(n);
    }
}
