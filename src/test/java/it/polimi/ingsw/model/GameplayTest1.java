package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.GameFullException;
import it.polimi.ingsw.exception.GameModeException;
import it.polimi.ingsw.exception.NameAlreadyExistentException;
import it.polimi.ingsw.exception.NumPlayersException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameplayTest1 {

    @Test
    public void testConstructor() {
        try {
            Gameplay gameplay = new Gameplay(GameMode.EASY, 2, 1);
            Assertions.assertNotNull(gameplay);
        } catch (NumPlayersException | GameModeException e) {
            Assertions.fail("Exception not expected");
        }
    }

    @Test
    public void testAddPlayer() {
        try {
            Gameplay gameplay = new Gameplay(GameMode.EASY, 2, 1);
            Player player = gameplay.addPlayer("Player1");
            Assertions.assertNotNull(player);
        } catch (NumPlayersException | GameModeException | GameFullException | NameAlreadyExistentException e) {
            Assertions.fail("Exception not expected");
        }
    }
}