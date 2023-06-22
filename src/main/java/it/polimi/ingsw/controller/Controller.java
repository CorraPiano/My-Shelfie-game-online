package it.polimi.ingsw.controller;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.localModel.LocalGame;
import it.polimi.ingsw.connection.Connection;
import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Controller extends UnicastRemoteObject implements ControllerSkeleton {

    private final GameplaysHandler gameplaysHandler;
    public Controller() throws RemoteException{
        gameplaysHandler = new GameplaysHandler();
    }

    public synchronized ArrayList<LocalGame> getGameList() throws RemoteException{
        return gameplaysHandler.getGameplayList();
    }

    public synchronized String addFirstPlayer(String name,GameMode gameMode, int maxPlayers, ClientSkeleton cc) throws NumPlayersException, GameModeException, GameFullException, NameAlreadyExistentException, RemoteException {
        int gameID = gameplaysHandler.nextID();
        Gameplay gameplay = new Gameplay(gameMode, maxPlayers, gameID);
        System.out.println("SERVER:: model pronto per " + maxPlayers +" giocatori in modalita' "+gameMode);
        gameplaysHandler.addGameplay(gameplay,gameID);
        Player player = gameplay.addPlayer(name);
        String id = player.getID();
        //gameplaysHandler.bind(id,gameID);
        ListenerRMI listener = new ListenerRMI(cc,this,gameplay.getEventKeeper(),id,name);
        gameplaysHandler.bind(id,gameID);
        new Thread(listener).start();
        System.out.println("SERVER:: giocatore connesso con nome " + name);
        return id;
    }

    public synchronized String addFirstPlayer(String name,GameMode gameMode, int maxPlayers, Connection conn) throws NumPlayersException, GameModeException, GameFullException, NameAlreadyExistentException, RemoteException {
        int gameID = gameplaysHandler.nextID();
        Gameplay gameplay = new Gameplay(gameMode, maxPlayers,gameID);
        System.out.println("SERVER:: model pronto per " + maxPlayers +" giocatori in modalita' "+gameMode);
        Player player = gameplay.addPlayer(name);
        String id = player.getID();
        gameplaysHandler.addGameplay(gameplay,gameID);
        ListenerTCP listener = new ListenerTCP(conn,this,gameplay.getEventKeeper(),id,name);
        Thread t = new Thread(listener);
        gameplaysHandler.bind(id,gameID);
        t.start();
        System.out.println("SERVER:: giocatore connesso con nome " + name);
        return id;
    }

    public synchronized String addPlayer(String name, int gameID, ClientSkeleton cc) throws GameFullException, NameAlreadyExistentException, InvalidGameIdException, RemoteException {
        Gameplay gameplay = gameplaysHandler.getGameplay(gameID);
        if(!gameplay.getGameState().equals(GameState.WAIT))
            throw new GameFullException();
        Player player = gameplay.addPlayer(name);
        String id = player.getID();
        ListenerRMI listener = new ListenerRMI(cc,this,gameplay.getEventKeeper(),id,name);
        Thread t = new Thread(listener);
        gameplaysHandler.bind(id,gameID);
        t.start();
        System.out.println("SERVER:: giocatore connesso con nome " + name);
        if(gameplay.isReady())
            gameplay.startGame();
        return id;
    }

    //for TCP - to fix
    public synchronized String addPlayer(String name, int gameID, Connection conn) throws GameFullException, NameAlreadyExistentException, InvalidGameIdException, RemoteException {
        Gameplay gameplay = gameplaysHandler.getGameplay(gameID);
        if(!gameplay.getGameState().equals(GameState.WAIT))
            throw new GameFullException();
        Player player = gameplay.addPlayer(name);
        String id = player.getID();
        ListenerTCP listener = new ListenerTCP(conn,this,gameplay.getEventKeeper(),id,name);
        gameplaysHandler.bind(id,gameID);
        new Thread(listener).start();
        System.out.println("SERVER:: giocatore connesso con nome " + name);
        if(gameplay.isReady())
            gameplay.startGame();
        return id;
    }

    private void validateCommand(Gameplay gameplay, String id) throws NotInGameException, WrongTurnException, RemoteException {
        if(!gameplay.getGameState().equals(GameState.GAME))
            throw new NotInGameException();
        if(!gameplay.getCurrentPlayerID().equals(id))
            throw new WrongTurnException();
    }

    public synchronized void pickItem(Coordinates coordinates, String id) throws InvalidIdException, NotInGameException, WrongTurnException, NotLinearPickException, LimitReachedPickException, NotCatchablePickException, EmptySlotPickException, OutOfBoardPickException, RemoteException {
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        validateCommand(gameplay,id);
        gameplay.pickItem(coordinates);
        System.out.println("GAME:: prelevata la pedina <" + coordinates.getRow()+ ", "+coordinates.getColumn()+ ">");
    }

    public synchronized void undoPick(String id) throws NotInGameException, WrongTurnException, InvalidIdException, RemoteException {
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        validateCommand(gameplay,id);
        gameplay.releaseHand();
        System.out.println("GAME:: mano svuotata ");
    }

    public synchronized void selectInsertOrder(ArrayList<Integer> order, String id) throws WrongLengthOrderException, WrongContentOrderException, NotInGameException, WrongTurnException, InvalidIdException, RemoteException {
        //not sortable exception
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        validateCommand(gameplay,id);
        gameplay.selectOrderHand(order);
        System.out.println("GAME:: mano ordinata con ordine: ");
        order.forEach(x->System.out.print(x+", ")); System.out.print("\n");
    }

    public synchronized void putItemList(int column, String id) throws EmptyHandException, NotInGameException, WrongTurnException, InvalidIdException, InvalidColumnPutException, NotEnoughSpacePutException, RemoteException {
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        validateCommand(gameplay,id);
        gameplay.putItemList(column);
        System.out.println("GAME:: mano inserita nel tabellone");
        if(gameplay.isFinished())
        {
            gameplay.endGame();
            gameplaysHandler.removeGame(gameplay.getGameID());
        }
        else if(gameplay.getNumPlayersConnected()<2 && !gameplay.currentPlayerIsConnected())
        {
            new Thread(new Timer(this,gameplay)).start();
        }
    }

    public synchronized void addChatMessage(ChatMessage chatMessage, String id) throws InvalidIdException, RemoteException, InvalidNameException {
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        gameplay.addChatMessage(chatMessage);
        System.out.println("CHAT:: "+ gameplay.getGameID()+", "+ gameplay.getPlayerNameByID(id));
    }

    public synchronized void leaveGame(String id) throws InvalidIdException, RemoteException {
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        gameplaysHandler.remove(id);
        System.out.println(gameplay.getPlayerNameByID(id)+" ha lasciato il gioco");
        gameplay.leave(id);
        if(gameplay.isFinished())
        {
            gameplay.endGame();
            gameplaysHandler.removeGame(gameplay.getGameID());
        }
        else if(gameplay.getNumPlayersConnected()<2 && !gameplay.currentPlayerIsConnected())
        {
            new Thread(new Timer(this,gameplay)).start();
        }
    }

    public synchronized void disconnect(String id) throws InvalidIdException {
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        System.out.println(gameplay.getPlayerNameByID(id)+" si è disconnesso");
        gameplay.disconnect(id);

        if(gameplay.isFinished())
        {
            gameplay.endGame();
            gameplaysHandler.removeGame(gameplay.getGameID());
        }
        else if(gameplay.getNumPlayersConnected()<2 && !gameplay.currentPlayerIsConnected())
        {
            new Thread(new Timer(this,gameplay)).start();
        }
    }

    public synchronized void endgame(Gameplay gameplay){
        gameplay.endGame();
        gameplaysHandler.removeGame(gameplay.getGameID());
    }

    public synchronized String reconnect(String id, ClientSkeleton cc) throws InvalidIdException, RemoteException, GameFinishedException, AlreadyConnectedException, GameLeftException {
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        gameplay.reconnect(id);
        String name = gameplay.getPlayerNameByID(id);
        System.out.println(gameplay.getPlayerNameByID(id)+" si è riconnesso");
        ListenerRMI listener = new ListenerRMI(cc,this,gameplay.getEventKeeper(),id,name);
        Thread t = new Thread(listener);
        //gameplaysHandler.rebind(id,listener);
        t.start();
        this.notifyAll();
        return name;
        //gameplay.leave(id);
    }
    public synchronized String reconnect(String id,Connection conn) throws InvalidIdException, GameFinishedException, AlreadyConnectedException, GameLeftException {
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        gameplay.reconnect(id);
        String name = gameplay.getPlayerNameByID(id);
        System.out.println(gameplay.getPlayerNameByID(id)+" si è riconnesso");
        ListenerTCP listener = new ListenerTCP(conn,this,gameplay.getEventKeeper(),id,name);
        Thread t = new Thread(listener);
        //gameplaysHandler.rebind(id,listener);
        t.start();
        return name;
        //gameplay.leave(id);
    }

    public synchronized void ping(int n) throws RemoteException{

    }

}
