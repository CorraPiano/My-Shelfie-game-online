package it.polimi.ingsw.client;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameMode;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class ClientRMI {

    private static Skeleton controller;
    private static Scanner stdin;
    private static String line;
    private static String id;
    private static String name;
    private static ArrayList<Coordinates> localHand= new ArrayList<>();
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry();
        //String[] e = registry.list();
        //for (String s : e) System.out.println(s);
        String remoteObjectName = "controller";
        controller = (Skeleton) registry.lookup(remoteObjectName);
        stdin = new Scanner(System.in);

        while(true) {
            try {
                line = stdin.next();

                if (line.equalsIgnoreCase("CREATE")) {
                    if(create()) break;
                }
                else if (line.equalsIgnoreCase("JOIN")) {
                    if(join()) break;
                }
                else {
                    System.out.println("CLIENT:: comando sconosciuto");
                    line = stdin.nextLine();
                }
            } catch (Exception e) {
                System.out.println("ERRORE:: stdin error");
            }
        }

        while(true){
            try {
                line = stdin.next();
                // scegliere se inviare al server la presa di un oggetto alla volta o un vettore di oggetti
                if(line.equalsIgnoreCase("PICK")){
                    pick();
                }
                else if(line.equalsIgnoreCase("UNDO")){
                    undo();
                }
                else if(line.equalsIgnoreCase("ORDER")){
                    order();
                }
                else if(line.equalsIgnoreCase("PUT")){
                    put();
                }
                else if(line.equalsIgnoreCase("SEND")){
                    send();
                }
                else if(line.equalsIgnoreCase("EXIT")){
                    controller.leaveGame(id);
                    break;
                }
                else{
                    System.out.println("CLIENT:: comando sconosciuto");
                    line = stdin.nextLine();
                }
            } catch(Exception e){
                System.out.println("ERRORE:: stdin error");
            }
            //skeleton.addChatMessage("ciao");
        }
    }

    public static boolean create() throws Exception{
        name = stdin.next();
        int num = stdin.nextInt();
        String gm = stdin.next();
        GameMode gamemode;
        if(Objects.equals(name, "")){
            System.out.println("CLIENT:: nome errato");
            return false;
        }
        if(num<2 || num>4){
            System.out.println("CLIENT:: nome giocatori errato");
            return false;
        }

        if(gm.equalsIgnoreCase("EASY") || gm.equals("0"))
             gamemode = GameMode.EASY;
        else
             gamemode = GameMode.EXPERT;

        try {
            id = controller.addFirstPlayer(name,gamemode, num);
            System.out.println("CLIENT: partita creata e giocatore connesso");
            return true;
        } catch (Exception e) {
            System.out.println("ERRORE:: impossibile creare una partita");
            return false;
        }
    }

    public static boolean join() throws Exception {
        name = stdin.next();
        if(Objects.equals(name, "")){
            System.out.println("CLIENT:: nome errato");
            return false;
        }
        try {
            id = controller.addPlayer(name,0);
            System.out.println("CLIENT: giocatore connesso");
            return true;
        } catch (Exception e) {
            System.out.println("ERRORE:: impossibile connettere il giocatore");
            return false;
        }
    }

    public static void pick() throws Exception{
        //controlli per vedere se la presa va bene (si può creare una apposita classe)
        int n1 = stdin.nextInt();
        int n2 = stdin.nextInt();
        if (n1>=0 && n1<9 && n2>=0 && n2<9) {
            Coordinates coord = new Coordinates(n1, n2);
            try{
                controller.pickItem(new Coordinates(n1,n2),id);
                localHand.add(coord);
            } catch (Exception e){
                System.out.println("ERRORE:: server error in pickItem");
            }
        }
        else
            System.out.println("CLIENT:: le coordinate inserite non sono corrette");
    }

    public static void undo() throws Exception{
        try{
            controller.undoPick(id);
            localHand.clear();
        } catch (Exception e){
            System.out.println("ERRORE:: server error in undoPick");
        }
    }

    public static void order() throws Exception{
        if(localHand.size()<2){
            System.out.println("CLIENT:: non sono stati prelevati abbastanza item");
            stdin.nextLine();
            return;
        }

        int n;
        ArrayList<Integer> list = new ArrayList<>();

        while(list.size()<localHand.size()){
            n=stdin.nextInt();
            if(n>=0 && n<localHand.size() && !list.contains(n)) {
                list.add(n);
            }
            else {
                stdin.nextLine();
                break;
            }

        }
        // AGGIUNGERE IL CONTROLLO DEL VETTORE
        if(list.size()==localHand.size()) {
            try {
                controller.selectInsertOrder(list, id);
            } catch (Exception e) {
                System.out.println("ERRORE:: server error in selectInsertOrder");
            }
        }
        else {
            System.out.println("CLIENT:: l'ordine inserito di "+list.size()+" interi non è corretto");
        }
    }

    public static void put() throws Exception{
        int n = stdin.nextInt();
        if (n>0 && n<5) {
            try {
                controller.putItemList(n,id);
                localHand.clear();
            } catch (Exception e) {
                System.out.println("ERRORE:: server error in putItemList");
            }
        }
        else
            System.out.println("CLIENT:: la colonna inserita non è corretta");
    }

    public static void send() throws Exception{
        String message = stdin.nextLine();
        try {
            controller.addChatMessage(message, id);
        } catch (Exception e) {
            System.out.println("ERRORE:: server error in addChatMessage");
        }
    }

}
