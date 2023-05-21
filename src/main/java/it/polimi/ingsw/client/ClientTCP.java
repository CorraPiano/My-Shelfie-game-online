package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Coordinates;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class ClientTCP{
    private static Socket socket;
    private static Scanner in;
    private static PrintWriter out;
    private static Scanner stdin;
    private static String line;
    private static String id;
    private static String name;
    private static ArrayList<Coordinates> localHand= new ArrayList<>();
    public static void main(String[] args) throws Exception {
        socket = new Socket("172.20.10.3", 8081);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream());
        stdin = new Scanner(System.in);
        while(true) {
            try {
                line = stdin.next();
                System.out.println(line);
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
                    //notifare al controller che il giocatore ha lasciato la partita
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
        String gamemode = stdin.next();

        if(Objects.equals(name, "")){
            System.out.println("CLIENT:: nome errato");
            return false;
        }
        if(num<2 || num>4){
            System.out.println("CLIENT:: parametri errati");
            return false;
        }

        if(gamemode.equalsIgnoreCase("EASY") || gamemode.equals("0"))
            gamemode = "easy";
        else
            gamemode = "expert";

        try {
            out.println("CREATE#"+name+"|"+gamemode+"|"+num);
            out.flush();
            if(in.nextLine().equals("ok")){
                System.out.println("CLIENT: partita creata e giocatore connesso");
                return true;
            }
            System.out.println("ERRORE:: impossibile creare la partita");
            return false;
        } catch (Exception e) {
            System.out.println("CONNECTION_ERROR");
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
            System.out.println(name);
            out.println("JOIN#"+name);
            out.flush();
            if(in.nextLine().equals("ok")) {
                System.out.println("CLIENT: giocatore connesso");
                return true;
            }
            System.out.println("ERRORE:: impossibile connettere il giocatore");
            return false;
        } catch (Exception e) {
            System.out.println("CONNECTION_ERROR");
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
                out.println("PICK#"+n1+"|"+n2);
                out.flush();
                if(!in.nextLine().equals("ok"))
                    System.out.println("ERRORE:: server error in pickItem");
                else
                    localHand.add(coord);
            } catch (Exception e){
                System.out.println("CONNECTION_ERROR");
            }
        }
        else
            System.out.println("CLIENT:: le coordinate inserite non sono corrette");
    }

    public static void undo() throws Exception{
        try{
            out.println("UNDO");
            out.flush();
            if(!in.nextLine().equals("ok"))
                System.out.println("ERRORE:: server error in undoPick");
        } catch (Exception e){
            System.out.println("CONNECTION_ERROR");
        }
    }

    public static void order() throws Exception{
        if(localHand.size()<2){
            System.out.println("CLIENT:: non sono stati prelevati abbastanza item");
            stdin.nextLine();
            return;
        }

        int n;
        String str="";

        while(str.length()/2<localHand.size()){
            n=stdin.nextInt();
            if(n>=0 && n<localHand.size() && !str.contains(String.valueOf(n))) {
                str=str+n+"|";
            }
            else {
                stdin.nextLine();
                break;
            }

        }
        // AGGIUNGERE IL CONTROLLO DEL VETTORE
        if(str.length()/2==localHand.size()) {
            try {
                str = "ORDER#" + str;
                out.println(str);
                out.flush();
                if(!in.nextLine().equals("ok"))
                    System.out.println("ERRORE:: server error selectInsertionOrder");
            } catch (Exception e) {
                System.out.println("CONNECTION_ERROR");
            }
        }
        else {
            System.out.println("CLIENT:: l'ordine inserito di "+str.length()+" interi non è corretto");
        }
    }

    public static void put() throws Exception{
        int n = stdin.nextInt();
        if (n>0 && n<5) {
            try {
                out.println("PUT#"+n);
                out.flush();
                if(!in.nextLine().equals("ok"))
                    System.out.println("ERRORE:: server error in putItem");
            } catch (Exception e) {
                System.out.println("CONNECTION_ERROR");
            }
        }
        else
            System.out.println("CLIENT:: la colonna inserita non è corretta");
    }

    public static void send() throws Exception{
        String message = stdin.nextLine();
        try {
            out.println("CHAT#"+message);
            out.flush();
            if(!in.nextLine().equals("ok"))
                System.out.println("ERRORE:: server error in addChatMessage");
        } catch (Exception e) {
            System.out.println("CONNECTION_ERROR");
        }
    }
}