package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameplayTest4Players {

    private Gameplay gameplay;

    void Game() throws GameModeException, NumPlayersException, GameFullException, NameAlreadyExistentException, NotLinearPickException, LimitReachedPickException, NotCatchablePickException, EmptySlotPickException, OutOfBoardPickException {
        Gameplay gameplay = new Gameplay(GameMode.EXPERT,4,0);

        Player P1 = gameplay.addPlayer("A");
        String A = P1.getID();
        Player P2 = gameplay.addPlayer("B");
        String B = P2.getID();
        Player P3 = gameplay.addPlayer("C");
        String C = P3.getID();
        Player P4 = gameplay.addPlayer("D");
        String D = P4.getID();

        Assertions.assertTrue(gameplay.isReady());
        gameplay.startGame();

        gameplay.pickItem(new Coordinates());
        gameplay.pickItem(new Coordinates());
        gameplay.pickItem(new Coordinates());




    }



}