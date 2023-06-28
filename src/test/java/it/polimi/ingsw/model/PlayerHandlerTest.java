package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.GameModeException;
import it.polimi.ingsw.exception.NumPlayersException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerHandlerTest {
    private PlayerHandler playerHandler;

    @BeforeEach
    public void setUp() throws GameModeException, NumPlayersException {
        Gameplay gameplay = new Gameplay(GameMode.EXPERT, 3, 0);
        playerHandler = new PlayerHandler(gameplay);
    }

    @Test
    public void testAddPlayer() {
        Player player = new Player("Flavio", 0);
        playerHandler.addPlayer(player);
        assertEquals(1, playerHandler.getNumPlayer()); // Verify that the number of players is updated
        assertTrue(playerHandler.getPlayerList().contains(player)); // Verify that the player is added to the list
    }

    @Test
    public void testCheckName() {
        Player player = new Player("Flavio", 1);
        playerHandler.addPlayer(player);

        assertFalse(playerHandler.checkName("Flavio")); // Verify that the name is not available
        assertTrue(playerHandler.checkName("Davide")); // Verify that a different name is available
    }


}

