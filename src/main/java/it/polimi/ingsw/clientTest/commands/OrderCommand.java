package it.polimi.ingsw.clientTest.commands;

import it.polimi.ingsw.clientTest.Client;

import it.polimi.ingsw.clientTest.ConnectionType;
import it.polimi.ingsw.clientTest.Sender;

import java.util.ArrayList;
import java.util.Scanner;

public class OrderCommand  implements Command{
    @Override
    public void execute(Sender sender, Scanner stdin, Client client) {
        String line = "";
        ArrayList<Integer> list = new ArrayList<>();
        int counter =0;
        while(!line.equals(".") && counter<3){
            if(stdin.hasNext()) {
                line = stdin.next();
                try {
                    int n = Integer.parseInt(line);
                    if (n >= 0) {
                        list.add(n);
                        counter++;
                    }
                    else {
                        System.out.println("CLIENT:: serve una lista di interi");
                        return;
                    }
                } catch (Exception e){
                    break;
                }
            }
        }

        if(counter<2){
            System.out.println("CLIENT:: serve una lista di almeno 2 interi");
            return;
        }

        sender.selectInsertOrder(list);

    }
}
