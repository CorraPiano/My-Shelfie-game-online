package it.polimi.ingsw.clientTest.commands;

import it.polimi.ingsw.clientTest.Client;
import it.polimi.ingsw.controller.ControllerSkeleton;

import java.util.ArrayList;
import java.util.Scanner;

public class ListCommand  implements Command{
    @Override
    public void execute(ControllerSkeleton controller, Scanner stdin, Client client) {
        try{
            ArrayList<String> list = controller.getGameList();
            for(String s: list)
                System.out.println(s);
        } catch(Exception e){
            System.out.println("impossibile recuperare la lista delle partite attive");
        }
    }
}
