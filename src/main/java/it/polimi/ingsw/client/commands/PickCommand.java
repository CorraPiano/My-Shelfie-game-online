package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.Sender;
import it.polimi.ingsw.model.Coordinates;
import java.util.Scanner;

import static it.polimi.ingsw.util.Constants.ANSI_RESET;
import static it.polimi.ingsw.util.Constants.ANSI_YELLOW;

public class PickCommand  implements Command {

    public void execute(Sender sender, Scanner stdin, Client client) {
        int n1 = stdin.nextInt();
        int n2 = stdin.nextInt();
        if (n1<0 || n1>9 || n2<0 || n2>9) {
            System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "The coordinates entered are not correct");
            return;
        }

        Coordinates coord = new Coordinates(n1, n2);
        sender.pickItem(coord);
    }
}
