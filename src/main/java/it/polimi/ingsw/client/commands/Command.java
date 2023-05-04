package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.controller.ControllerSkeleton;

import java.util.Scanner;

public interface Command {
   void execute(ControllerSkeleton controller, Scanner stdin,Client client);
}
