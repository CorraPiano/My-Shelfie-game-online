package it.polimi.ingsw.controller;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class Controller {

    private final GameplaysHandler gameplaysHandler;
    public Controller() {
        gameplaysHandler = new GameplaysHandler();
    }

    public synchronized String addFirstPlayer(String name,GameMode gameMode, int maxPlayers) throws NumPlayersException, GameModeException, GameFullException, NameAlreadyExistentException {
        int gameID = gameplaysHandler.nextID();
        Gameplay gameplay = new Gameplay(gameMode, maxPlayers, gameID);
        gameplaysHandler.addGameplay(gameplay);
        System.out.println("SERVER:: model pronto per " + maxPlayers +" giocatori in modalita' "+gameMode);
        Player player = gameplay.addPlayer(name);
        String id = player.getID();
        gameplaysHandler.bind(id,gameID);
        System.out.println("SERVER:: giocatore connesso con nome " + name);
        return id;
    }

    public synchronized String addPlayer(String name, int gameID) throws GameFullException, NameAlreadyExistentException, InvalidGameIdException {
        Gameplay gameplay = gameplaysHandler.getGameplay(gameID);
        if(!gameplay.getGameState().equals(GameState.WAIT))
            throw new GameFullException();
        Player player = gameplay.addPlayer(name);
        String id = player.getID();
        gameplaysHandler.bind(id,gameID);
        System.out.println("SERVER:: giocatore connesso con nome " + name);
        return id;
    }

    private void validateCommand(Gameplay gameplay, String id) throws NotInGameException, WrongTurnException {
        if(!gameplay.getGameState().equals(GameState.GAME))
            throw new NotInGameException();
        if(!gameplay.getCurrentPlayerID().equals(id))
            throw new WrongTurnException();
    }

    public synchronized void pickItem(Coordinates coordinates, String id) throws InvalidIdException, NotInGameException, WrongTurnException, NotLinearPickException, LimitReachedPickException, NotCatchablePickException, EmptySlotPickException, OutOfBoardPickException {
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        validateCommand(gameplay,id);
        gameplay.pickItem(coordinates);
        System.out.println("GAME:: prelevata la pedina <" + coordinates.getRow()+ ", "+coordinates.getColumn()+ ">");
    }

    public synchronized void undoPick(String id) throws NotInGameException, WrongTurnException, InvalidIdException {
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        validateCommand(gameplay,id);
        gameplay.releaseHand();
        System.out.println("GAME:: mano svuotata ");
    }

    public synchronized void selectInsertOrder(ArrayList<Integer> order, String id) throws WrongLengthOrderException, WrongContentOrderException, NotInGameException, WrongTurnException, InvalidIdException {
        //not sortable exception
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        validateCommand(gameplay,id);
        gameplay.selectOrderHand(order);
        System.out.println("GAME:: mano ordinata con ordine: ");
        order.forEach(x->System.out.print(x+", ")); System.out.print("\n");
    }

    public synchronized void putItemList(int column, String id) throws EmptyHandException, NotInGameException, WrongTurnException, InvalidIdException, InvalidColumnPutException, NotEnoughSpacePutException {
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        validateCommand(gameplay,id);
        gameplay.putItemList(column);
        System.out.println("GAME:: mano inserita nel tabellone");
    }

    public synchronized void addChatMessage(String chatMessage,String id) throws InvalidIdException {
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        System.out.println("CHAT:: "+ gameplay.getGameID()+", "+ gameplay.getPlayerNameByID(id) + ">> " + chatMessage);
    }

    public synchronized void leaveGame(String id) throws InvalidIdException {
        Gameplay gameplay = gameplaysHandler.getHisGameplay(id);
        // da implementare
    }
    public synchronized ArrayList<String> getGameList(){
        return gameplaysHandler.getGameplayList();
    }
}
