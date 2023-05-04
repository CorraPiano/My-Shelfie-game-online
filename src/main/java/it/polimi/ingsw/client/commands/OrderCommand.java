package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;

import it.polimi.ingsw.controller.ControllerSkeleton;

import java.util.ArrayList;
import java.util.Scanner;

public class OrderCommand  implements Command{
    @Override
    public void execute(ControllerSkeleton controller, Scanner stdin, Client client) {
        String line = "";
        ArrayList<Integer> list = new ArrayList<>();
        int counter =0;
        while(!line.equals(".") && counter<3){
            if(stdin.hasNext()) {
                line = stdin.next();
                int n = Integer.parseInt(line);
                if(n>=0)
                    list.add(n);
                else {
                    System.out.println("CLIENT:: serve una lista di interi terminata da '.' ");
                    return;
                }
                counter++;
            }
        }

        try{
            controller.selectInsertOrder(list,client.getID());
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
