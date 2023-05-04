package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.controller.ControllerSkeleton;

import java.util.Scanner;

public class PutCommand  implements Command{
    @Override
    public void execute(ControllerSkeleton controller, Scanner stdin, Client client) {
        int n = stdin.nextInt();
        if (n>=0 && n<5) {
            try{
                controller.putItemList(n,client.getID());
            } catch(Exception e) {
                System.out.println(e);
            }
        }
        else
            System.out.println("CLIENT:: la colonna inserita non è corretta");
    }
}
