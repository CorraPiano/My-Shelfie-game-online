package it.polimi.ingsw.clientTest.commands;

import it.polimi.ingsw.clientTest.Client;
import it.polimi.ingsw.clientTest.ConnectionType;
import it.polimi.ingsw.clientTest.Sender;

import java.util.ArrayList;
import java.util.Scanner;

public class ListCommand  implements Command{
    @Override
    public void execute(Sender sender, Scanner stdin, Client client) {
        sender.getGameList();
    }
}
