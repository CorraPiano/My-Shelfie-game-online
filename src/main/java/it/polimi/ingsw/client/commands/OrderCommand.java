package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.Sender;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderCommand  implements Command {

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
        if(counter>3){
            System.out.println("CLIENT:: serve una lista di al massimo 3 interi");
            return;
        }

        sender.selectInsertOrder(list);
    }
}
