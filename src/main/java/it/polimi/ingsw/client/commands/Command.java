package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.Sender;

import java.util.Scanner;

public interface Command {
   void execute(Sender controller, Scanner stdin, Client client);
}
