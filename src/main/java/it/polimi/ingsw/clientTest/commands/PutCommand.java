package it.polimi.ingsw.clientTest.commands;

import it.polimi.ingsw.clientTest.Client;
import it.polimi.ingsw.clientTest.ConnectionType;
import it.polimi.ingsw.clientTest.Sender;

import java.util.Scanner;

public class PutCommand  implements Command{
    @Override
    public void execute(Sender sender, Scanner stdin, Client client) {
        int n = stdin.nextInt();
        if (n <0 && n>=5) {
            System.out.println("CLIENT:: la colonna inserita non è corretta");
            return;
        }

        sender.putItemList(n);
    }
}
