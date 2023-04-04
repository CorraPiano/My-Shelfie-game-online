package it.polimi.ingsw.client;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.Player;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    private static Skeleton skeleton;
    public static void main(String[] args) throws Exception {
        Registry registry= LocateRegistry.getRegistry();
        String[] e = registry.list();
        for (String s : e) System.out.println(s);

        String remoteObjectName = "controller";
        skeleton = (Skeleton) registry.lookup(remoteObjectName);
        try {
            if(skeleton.addPlayer("marco", GameMode.EASY, 3))
                System.out.println("nuovo giocatore connesso");
            else
                System.out.println("impossibile connettere un nuovo giocatore");
        }
        catch(Exception ee){
            System.out.println(ee);
        }
        while(true){
            //skeleton.addChatMessage("ciao");
        }
    }
}
