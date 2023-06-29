package it.polimi.ingsw.controller;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.localModel.LocalGame;
import it.polimi.ingsw.connection.Connection;
import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * The Controller class handles the game logic and interactions between the clients and the server.
 * It acts as a remote object and implements the ControllerSkeleton interface for remote communication.
 * The Controller manages gameplays, player actions, and game state transitions.
 * It provides methods for adding players, handling player actions, managing chat messages,
 * and maintaining the overall game flow.
 */
//extends UnicastRemoteObject
public class Controller implements ControllerSkeleton {

    private final GameplaysHandler gameplaysHandler;
    private final ConnectionChecker connectionChecker;

    /**
     * Constructor for the Controller class.
     *
     * @throws RemoteException if a remote communication error occurs
     */
    public Controller() throws RemoteException{
        gameplaysHandler = new GameplaysHandler();
        connectionChecker = new ConnectionChecker(this);
    }

    /**
     * Retrieves the list of local games.
     *
     * @return the list of local games
     * @throws RemoteException if a remote communication error occurs
     */
    public synchronized ArrayList<LocalGame> getGameList() throws RemoteException{
        return gameplaysHandler.getGameplayList();
    }


    private Gameplay createGameplay(GameMode gameMode, int maxPlayers) throws GameModeException, NumPlayersException {
        // creating gameplay
        int gameID = gameplaysHandler.nextID();
        Gameplay gameplay = new Gameplay(gameMode, maxPlayers,gameID);
        gameplaysHandler.addGameplay(gameplay,gameID);
        System.out.println("# game create with id " +gameID+ " for "+maxPlayers+ " people in mode " +gameMode);
        return gameplay;
    }


    /**
     * Adds the first player to a new game.
     *
     * @param name       the name of the player
     * @param gameMode   the game mode
     * @param maxPlayers the maximum number of players
     * @param cc         the client's remote skeleton object (RMI)
     * @return the ID of the player
     * @throws NumPlayersException           if the number of players is invalid for the selected game mode
     * @throws GameModeException             if the game mode is invalid
     * @throws GameFullException             if the game is already full
     * @throws NameAlreadyExistentException  if the player name already exists in the game
     * @throws RemoteException               if a remote communication error occurs
     */
    //ClientSkeleton cc
    public synchronized String addFirstPlayer(String name,GameMode gameMode, int maxPlayers, ClientSkeleton cc) throws NumPlayersException, GameModeException, GameFullException, NameAlreadyExistentException, RemoteException, NotBoundException {
        // creating gameplay
        Gameplay gameplay = createGameplay(gameMode,maxPlayers);
        // add player, start his connection checker, check start game (useless)
        String id = addPlayer(name, gameplay);
        // bind RMI listener
        ListenerRMI listener = new ListenerRMI(cc,this,gameplay.getEventKeeper(),id,name);
        new Thread(listener).start();

        return id;
    }

    /**
     * Adds the first player to a new game.
     *
     * @param name       the name of the player
     * @param gameMode   the game mode
     * @param maxPlayers the maximum number of players
     * @param conn       the client's connection object (TCP)
     * @return the ID of the player
     * @throws NumPlayersException           if the number of players is invalid for the selected game mode
     * @throws GameModeException             if the game mode is invalid
     * @throws GameFullException             if the game is already full
     * @throws NameAlreadyExistentException  if the player name already exists in the game
     * @throws RemoteException               if a remote communication error occurs
     */
    public synchronized String addFirstPlayer(String name,GameMode gameMode, int maxPlayers, Connection conn) throws NumPlayersException, GameModeException, GameFullException, NameAlreadyExistentException, RemoteException {
        // creating gameplay
        Gameplay gameplay = createGameplay(gameMode,maxPlayers);
        // add player, start his connection checker, check start game (useless)
        String id = addPlayer(name, gameplay);
        // bind RMI listener
        ListenerTCP listener = new ListenerTCP(conn,this,gameplay.getEventKeeper(),id,name);
        new Thread(listener).start();
        return id;
    }

    private String addPlayer(String name,Gameplay gameplay) throws GameFullException, NameAlreadyExistentException {
        // check if game is in wait
        if(!gameplay.getGameState().equals(GameState.WAIT))
            throw new GameFullException();
        // adding player
        Player player = gameplay.addPlayer(name);
        String id = player.getID();
        gameplaysHandler.bind(id,gameplay.getGameID());
        System.out.println("# " +name+ "join the gane with id " +id);
        // starting connection checker
        new Thread(()->connectionChecker.run(id)).start();
        // checking start game
        if(gameplay.isReady())
            gameplay.startGame();

        return id;
    }

    /**
     * Adds a player to an existing game using RMI connection.
     *
     * @param name    the name of the player
     * @param gameID  the ID of the game
     * @param cc      the client's remote skeleton object (RMI)
     * @return the ID of the player
     * @throws GameFullException             if the game is already full
     * @throws NameAlreadyExistentException  if the player name already exists in the game
     * @throws InvalidGameIdException        if the game ID is invalid
     * @throws RemoteException               if a remote communication error occurs
     */
    //ClientSkeleton cc
    public synchronized String addPlayer(String name, int gameID, ClientSkeleton cc) throws GameFullException, NameAlreadyExistentException, InvalidGameIdException, RemoteException, NotBoundException {
        // recover gameplay
        Gameplay gameplay = gameplaysHandler.getGameplay(gameID);
        // add player, start his connection checker, check start game
        String id = addPlayer(name, gameplay);
        // bind RMI listener
        ListenerRMI listener = new ListenerRMI(cc,this,gameplay.getEventKeeper(),id,name);
        new Thread(listener).start();
        return id;
    }

    //for TCP - to fix
    /**
     * Adds a player to an existing game using RMI connection.
     *
     * @param name    the name of the player
     * @param gameID  the ID of the game
     * @param conn    the client's TCP connection
     * @return the ID of the player
     * @throws GameFullException             if the game is already full
     * @throws NameAlreadyExistentException  if the player name already exists in the game
     * @throws InvalidGameIdException        if the game ID is invalid
     * @throws RemoteException               if a remote communication error occurs
     */
    public synchronized String addPlayer(String name, int gameID, Connection conn) throws GameFullException, NameAlreadyExistentException, InvalidGameIdException, RemoteException {
        // recover gameplay
        Gameplay gameplay = gameplaysHandler.getGameplay(gameID);
        // add player, start his connection checker, check start game
        String id = addPlayer(name, gameplay);
        // bind TCP listener
        ListenerTCP listener = new ListenerTCP(conn,this,gameplay.getEventKeeper(),id,name);
        new Thread(listener).start();

        return id;
    }

    /**
     * Validates the command for a player in the game.
     *
     * @param gameplay the gameplay instance
     * @param id       the ID of the player
     * @throws NotInGameException if the player is not currently in a game
     * @throws WrongTurnException if it is not the player's turn
     * @throws RemoteException   if a remote communication error occurs
     */
    private void validateCommand(Gameplay gameplay, String id) throws NotInGameException, WrongTurnException, RemoteException {
        if(!gameplay.getGameState().equals(GameState.GAME))
            throw new NotInGameException();
        if(!gameplay.getCurrentPlayerID().equals(id))
            throw new WrongTurnException();
    }

    /**
     * Picks an item from the board.
     *
     * @param coordinates the coordinates of the item to pick
     * @param id          the ID of the player
     * @throws InvalidIdException      if the player ID is invalid
     * @throws NotInGameException      if the player is not in a game
     * @throws WrongTurnException      if it is not the player's turn
     * @throws NotLinearPickException  if the pick is not linear
     * @throws LimitReachedPickException  if the pick limit is reached
     * @throws NotCatchablePickException  if the item is not catchable
     * @throws EmptySlotPickException  if the slot is empty
     * @throws OutOfBoardPickException  if the coordinates are out of the board
     * @throws RemoteException         if a remote communication error occurs
     */
    public synchronized void pickItem(Coordinates coordinates, String id) throws InvalidIdException, NotInGameException, WrongTurnException, NotLinearPickException, LimitReachedPickException, NotCatchablePickException, EmptySlotPickException, OutOfBoardPickException, RemoteException {
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        validateCommand(gameplay,id);
        gameplay.pickItem(coordinates);
        System.out.println("# pick: <" + coordinates.getRow()+ ", "+coordinates.getColumn()+ ">");
    }

    /**
     * Undoes the pick action for a player.
     *
     * @param id the ID of the player
     * @throws NotInGameException if the player is not currently in a game
     * @throws WrongTurnException if it is not the player's turn
     * @throws InvalidIdException if the player ID is invalid
     * @throws RemoteException   if a remote communication error occurs
     */
    public synchronized void undoPick(String id) throws NotInGameException, WrongTurnException, InvalidIdException, RemoteException {
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        validateCommand(gameplay,id);
        gameplay.releaseHand();
        System.out.println("# undo: ");
    }

    /**
     * Selects the insert order for a player's hand items.
     *
     * @param order the insert order for the player's hand items
     * @param id    the ID of the player
     * @throws WrongLengthOrderException  if the order length is incorrect
     * @throws WrongContentOrderException if the order content is invalid
     * @throws NotInGameException        if the player is not currently in a game
     * @throws WrongTurnException        if it is not the player's turn
     * @throws InvalidIdException        if the player ID is invalid
     * @throws RemoteException          if a remote communication error occurs
     */
    public synchronized void selectInsertOrder(ArrayList<Integer> order, String id) throws WrongLengthOrderException, WrongContentOrderException, NotInGameException, WrongTurnException, InvalidIdException, RemoteException {
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        validateCommand(gameplay,id);
        gameplay.selectOrderHand(order);
        //print
        System.out.print("# order: [");
        order.forEach(x->System.out.print(x+", "));
        System.out.println("]");
    }

    /**
     * Puts the player's item list on the game board.
     *
     * @param column the column to put the item list in
     * @param id     the ID of the player
     * @throws EmptyHandException        if the player's hand is empty
     * @throws NotInGameException        if the player is not in a game
     * @throws WrongTurnException        if it is not the player's turn
     * @throws InvalidIdException        if the player ID is invalid
     * @throws InvalidColumnPutException if the column to put the item list in is invalid
     * @throws NotEnoughSpacePutException if there is not enough space in the column to put the item list
     * @throws RemoteException           if a remote communication error occurs
     */
    public synchronized void putItemList(int column, String id) throws EmptyHandException, NotInGameException, WrongTurnException, InvalidIdException, InvalidColumnPutException, NotEnoughSpacePutException, RemoteException {
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        validateCommand(gameplay,id);
        gameplay.putItemList(column);
        System.out.println("# put: "+column);
        //check finish game
        if(gameplay.isFinished()) {
            gameplay.endGame();
            gameplaysHandler.removeGame(gameplay.getGameID());
        }
    }

    /**
     * Adds a chat message to the game.
     *
     * @param chatMessage the chat message to add
     * @param id          the ID of the player
     * @throws InvalidIdException   if the player ID is invalid
     * @throws RemoteException      if a remote communication error occurs
     * @throws InvalidNameException if the player name is invalid
     */
    public synchronized void addChatMessage(ChatMessage chatMessage, String id) throws InvalidIdException, RemoteException, InvalidNameException, NotInGameException {
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        if(!gameplay.getGameState().equals(GameState.GAME))
            throw new NotInGameException();
        gameplay.addChatMessage(chatMessage);
        //to not fill the console
        //System.out.println("# chat");
    }

    private void checkAfterPlayerLost(Gameplay gameplay){
        if(!gameplay.isReady())
            return;
        if(gameplay.isFinished()) {
            gameplay.endGame();
            gameplaysHandler.removeGame(gameplay.getGameID());
        }
        else if(gameplay.getNumPlayersConnected()<2) {
            new Thread(new Timer(this,gameplay)).start();
        }
    }

    /**
     * Leaves the game.
     *
     * @param id the ID of the player
     * @throws InvalidIdException if the player ID is invalid
     * @throws RemoteException   if a remote communication error occurs
     */
    public synchronized void leaveGame(String id) throws InvalidIdException, RemoteException, GameFinishedException {
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        // check
        if(gameplay.getGameState().equals(GameState.END))
            throw new GameFinishedException();
        // remove player
        gameplay.leave(id);
        gameplaysHandler.remove(id);
        System.out.println("# "+gameplay.getPlayerNameByID(id)+" leave the game");
        // check if end the game or start the timer
        checkAfterPlayerLost(gameplay);
    }

    /**
     * Disconnects the player from the game.
     *
     * @param id the ID of the player
     * @throws InvalidIdException if the player ID is invalid
     */
    public synchronized void disconnect(String id) throws InvalidIdException, GameFinishedException {
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        // check
        if(gameplay.getGameState().equals(GameState.END))
            throw new GameFinishedException();
        // disconnect player
        gameplay.disconnect(id);
        System.out.println("# "+gameplay.getPlayerNameByID(id)+" has lost the connection");
        // check if end the game or start the timer
        checkAfterPlayerLost(gameplay);
    }

    /**
     * Ends the game.
     *
     * @param gameplay the gameplay to end
     */
    public synchronized void endgame(Gameplay gameplay){
        gameplay.endGame();
        gameplaysHandler.removeGame(gameplay.getGameID());
    }

    private String reconnect(String id, Gameplay gameplay) throws GameFinishedException, AlreadyConnectedException, GameLeftException, InvalidIdException {
        //check
        if(gameplay.getGameState().equals(GameState.END))
            throw new GameFinishedException();
        // reconnection
        String name = gameplay.reconnect(id);
        System.out.println("# "+gameplay.getPlayerNameByID(id)+" has reconnected");
        // start connection checker
        new Thread(()->connectionChecker.run(id)).start();
        // check end game
        if(gameplay.isFinished()) {
            gameplay.endGame();
            gameplaysHandler.removeGame(gameplay.getGameID());
        }

        return name;
    }

    /**
     * Reconnects a player to the game using RMI.
     *
     * @param id the ID of the player
     * @param cc the client skeleton for RMI communication
     * @return the name of the reconnected player
     * @throws InvalidIdException       if the player ID is invalid
     * @throws RemoteException          if a remote communication error occurs
     * @throws GameFinishedException    if the game has already finished
     * @throws AlreadyConnectedException if the player is already connected
     * @throws GameLeftException        if the player has left the game
     */
    public synchronized String reconnect(String id, ClientSkeleton cc,boolean reset) throws InvalidIdException, RemoteException, GameFinishedException, AlreadyConnectedException, GameLeftException {
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        String name = reconnect(id,gameplay);
        //start listener
        ListenerRMI listener = new ListenerRMI(cc,this,gameplay.getEventKeeper(),id,name);
        //fix
        listener.reset();
        new Thread(listener).start();
        return name;
    }

    /**
     * Reconnects a player to the game using TCP.
     *
     * @param id   the ID of the player
     * @param conn the connection for TCP communication
     * @return the name of the reconnected player
     * @throws InvalidIdException       if the player ID is invalid
     * @throws GameFinishedException    if the game has already finished
     * @throws AlreadyConnectedException if the player is already connected
     * @throws GameLeftException        if the player has left the game
     */
    public synchronized String reconnect(String id,Connection conn,boolean reset) throws InvalidIdException, GameFinishedException, AlreadyConnectedException, GameLeftException {
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        String name = reconnect(id,gameplay);
        ListenerTCP listener = new ListenerTCP(conn,this,gameplay.getEventKeeper(),id,name);
        //to fix with gui
        if(reset)
            listener.reset();
        new Thread(listener).start();
        return name;
    }

    /**
     * Pings the server.
     *
     * @param n the number of pings
     * @throws RemoteException if a remote communication error occurs
     */
    public synchronized int ping(int n, String id) throws RemoteException{
        if (connectionChecker.setPing(id))
            return 1;
        return 0;
        //to not fill the console
        //System.out.println("# ping");
    }

}
