package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.localModel.LocalGame;
import it.polimi.ingsw.connection.message.GamesList;
import it.polimi.ingsw.controller.ControllerSkeleton;

import java.util.ArrayList;
import java.util.Scanner;

public class ListCommand  implements Command {
    @Override
    public void execute(ControllerSkeleton controller, Scanner stdin, Client client) {
        try{
            ArrayList<LocalGame> list= controller.getGameList();
            for(LocalGame s: list)
                System.out.println(s);
        } catch(Exception e){
            System.out.println("impossibile recuperare la lista delle partite attive");
        }
    }
}
