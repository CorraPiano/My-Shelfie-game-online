package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.controller.ControllerSkeleton;

import java.util.Scanner;

public class UndoCommand implements Command{
    @Override
    public void execute(ControllerSkeleton controller, Scanner stdin, Client client) {
        try{
            controller.undoPick(client.getID());
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
