package it.polimi.ingsw.model;

import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.exception.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameplayTest4Players {

    private Gameplay gameplay;

    @Test
    void GameTest() throws GameModeException, NumPlayersException, GameFullException, NameAlreadyExistentException, NotLinearPickException, LimitReachedPickException, NotCatchablePickException, EmptySlotPickException, OutOfBoardPickException, WrongLengthOrderException, WrongContentOrderException, EmptyHandException, InvalidColumnPutException, NotEnoughSpacePutException, InvalidNameException, AlreadyConnectedException, GameLeftException, InvalidIdException {
        Gameplay gameplay = new Gameplay(GameMode.EXPERT,4,0);


        gameplay.addPlayer("Flavio");
        gameplay.startGame();
        gameplay.isReady();
        gameplay.getGameState();
        gameplay.getPlayerByID("Flavio");
        gameplay.releaseHand();

        ArrayList<Integer> orderList = new ArrayList<>();
        orderList.add(0);
        orderList.add(1);
        orderList.add(2);

        //gameplay.selectOrderHand(orderList);
        gameplay.isFinished();
        //gameplay.putItemList(5);
        gameplay.addChatMessage(new ChatMessage("Flavio","ciao"));
        gameplay.getNumPlayersConnected();
        gameplay.getNumDisconnection();
        gameplay.endGame();
        gameplay.isConnected("Flavio");
        gameplay.checkTimer(10000L, 1);
        gameplay.currentPlayerIsConnected();
        gameplay.getCurrentPlayers();
        gameplay.getEventKeeper();
        gameplay.getCurrentPlayers();
        gameplay.getPlayerNameByID("Flavio");
        gameplay.reconnect("Flavio");
    }



}