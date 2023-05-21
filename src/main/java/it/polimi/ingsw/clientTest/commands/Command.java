package it.polimi.ingsw.clientTest.commands;

import it.polimi.ingsw.clientTest.Client;
import it.polimi.ingsw.clientTest.Sender;

import java.util.Scanner;

public interface Command {
   void execute(Sender sender, Scanner stdin, Client client);
}
