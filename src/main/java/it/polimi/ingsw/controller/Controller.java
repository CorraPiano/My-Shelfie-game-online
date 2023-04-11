package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;

import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller extends UnicastRemoteObject implements Skeleton {
    private int numPlayers;
    private int maxPlayers;
    private ControllerState state;
    private PlayerIterator playerIterator;
    private Gameplay gameplay = null;
    private Player current = null;
    private HashMap<String, String> map;

    public Controller() throws RemoteException{
        //UnicastRemoteObject.exportObject(this, 0);
        numPlayers=0;
        maxPlayers=0;
        state=ControllerState.START;
        map = new HashMap<>();
    }

    public synchronized String addFirstPlayer(String name,GameMode gameMode, int maxPlayers) throws Exception{
        if (!state.equals(ControllerState.START))
            throw new Exception();
        if(maxPlayers<2 || maxPlayers>4)
            throw new Exception();
        if(!gameMode.equals(GameMode.EASY) && !gameMode.equals(GameMode.EXPERT))
            throw new Exception();

        // inizializzazione gioco
        System.out.println("... tentativo di creazione partita ...");
        this.maxPlayers = maxPlayers;
        gameplay = new Gameplay(gameMode, maxPlayers);
        state=ControllerState.WAIT;
        System.out.println("SERVER:: model pronto per " + maxPlayers +" giocatori in modalita' "+gameMode);

        // aggiunta giocatore
        System.out.println("... tentativo di addPlayer ...");
        Player player = gameplay.addPlayer(name);
        this.numPlayers++;
        String id = player.getID();
        map.put(id,name);
        // attenzione alle mappe, nel caso si abbiano due ID uguali, assolutamente da non permettere!!!
        System.out.println("SERVER:: giocatore connesso con nome " + name);

        return id;
    }

    public synchronized String addPlayer(String name) throws Exception{
        if (!state.equals(ControllerState.WAIT))
            throw new Exception();

        System.out.println("... tentativo di addPlayer ...");
        Player player = gameplay.addPlayer(name);
        this.numPlayers++;
        String id = player.getID();
        map.put(id,name);
        System.out.println("SERVER:: giocatore connesso con nome " + name);

        if (this.numPlayers == this.maxPlayers) {
            System.out.println("... tentativo di avvio partita ...");
            // non DEVE dare errore o il sistema si blocca <<
            // serve davvero playeriterator al controller, o basta una funzione del gameplay
            playerIterator = gameplay.startGame();
            current = playerIterator.current();
            state=ControllerState.GAME;
            System.out.println("SERVER:: partita avviata" );
        }
        return id;
    }

    // da sistemare con coordinates
    public synchronized void pickItem(int n1, int n2, String id)  throws Exception{
        if(!state.equals(ControllerState.GAME) || !current.getName().equals(id))
            throw new Exception();
        // sistemare le coordinate
        System.out.println("... tentativo di pickItem ...");
        ArrayList l=new ArrayList<>();
        l.add(new Coordinates(n1,n2));
        gameplay.pickItemList(l);
        System.out.println("GAME:: prelevata la pedina <" + n1+ ", "+n2+ ">");
        // cambiare column and row in x e y
    }

    public synchronized void undoPick(String id) throws Exception{
        if(!state.equals(ControllerState.GAME) || !current.getName().equals(id))
            throw new Exception();
        System.out.println("... tentativo di undoPick ...");
        gameplay.releaseHand();
        System.out.println("GAME:: mano svuotata ");
    }

    public synchronized void selectInsertOrder(ArrayList<Integer> order, String id) throws Exception{
        if(!state.equals(ControllerState.GAME) || !current.getName().equals(id))
            throw new Exception();
        System.out.println("... tentativo di selectInsertOrder ...");
        gameplay.selectOrderHand(order);
        System.out.println("GAME:: mano ordinata con ordine: ");
        for(int i: order)
            System.out.printf("%d,",i);
        System.out.print("\n");
    }

    public synchronized void putItemList(int column, String id) throws Exception{
        if(!state.equals(ControllerState.GAME) || !current.getName().equals(id))
            throw new Exception();
        System.out.println("... tentativo di putItemList ...");
        gameplay.putItemList(column);
        gameplay.calcPoints();
        if(playerIterator.isEnd()){
            gameplay.endGame();
            state=ControllerState.END;
        }
        else{
            //teoricamente nessun metodo è invocabile contemporaneamente per synchronized
            playerIterator.next();
            current=playerIterator.current();
        }
        // scegliere se inserire lo stato PUT con UNDO_INSERT
        System.out.println("GAME:: mano inserita nel tabellone");
    }

    public synchronized void addChatMessage(String chatMessage,String id) throws Exception{
        if(map.containsKey(id))
            System.out.println("CHAT:: "+ map.get(id) + ">> " + chatMessage);
        else
            throw new Exception();
    }

    public synchronized void leaveGame() throws Exception{
        // da implementare
    }
}
