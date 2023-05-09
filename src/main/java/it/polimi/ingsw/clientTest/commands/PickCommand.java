package it.polimi.ingsw.clientTest.commands;

import it.polimi.ingsw.clientTest.Client;
import it.polimi.ingsw.controller.ControllerSkeleton;
import it.polimi.ingsw.model.Coordinates;

import java.util.Scanner;

public class PickCommand  implements Command{
    @Override
    public void execute(ControllerSkeleton controller, Scanner stdin, Client client) {
        int n1 = stdin.nextInt();
        int n2 = stdin.nextInt();
        if (n1<0 || n1>9 || n2<0 || n2>9) {
            System.out.println("CLIENT:: le coordinate inserite non sono corrette");
            return;
        }

        Coordinates coord = new Coordinates(n1, n2);
        try{
            controller.pickItem(coord,client.getID());
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
