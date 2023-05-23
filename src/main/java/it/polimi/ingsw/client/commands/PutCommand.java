package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.Sender;
import java.util.Scanner;

public class PutCommand  implements Command {

    public void execute(Sender sender, Scanner stdin, Client client) {
        int n = stdin.nextInt();
        if (n <0 || n>=5) {
            System.out.println("CLIENT:: la colonna inserita non è corretta");
            return;
        }

        sender.putItemList(n);
    }
}
