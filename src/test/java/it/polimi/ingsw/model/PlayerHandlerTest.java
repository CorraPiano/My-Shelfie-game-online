package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.GameModeException;
import it.polimi.ingsw.exception.NumPlayersException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PlayerHandlerTest {
    private PlayerHandler playerHandler;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp() throws GameModeException, NumPlayersException {
        Gameplay gameplay = new Gameplay(GameMode.EXPERT, 3, 0);
        playerHandler = new PlayerHandler();
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
    @Test
    public void testCurrent() {

        player1 = new Player("Flavio", 1); // Create a player object with a unique name and ID
        player2 = new Player("Nicola", 2); // Create another player object with a unique name and ID
        playerHandler.addPlayer(player1);
        playerHandler.addPlayer(player2);
        assertNull(playerHandler.current()); // Verify that the current player is null initially

        playerHandler.choseFirstPlayer();
        //assertEquals(player1, playerHandler.current()); // Verify that the current player is player1 after choosing the first player

        playerHandler.next();
        //assertEquals(player2, playerHandler.current()); // Verify that the current player is player2 after advancing to the next player

        //playerHandler.next();
        //assertEquals(player1, playerHandler.current()); // Verify that the current player cycles back to player1 after reaching the end of the player list

        // Add more tests if needed
    }
    @Test
    public void testGetPlayerByID_InvalidID() {
        Player player = new Player("Flavio", 1);
        playerHandler.addPlayer(player);
        assertNull(playerHandler.getPlayerByID("2")); // Verify that null is returned for an invalid ID
    }
    @Test
    public void testGetPlayerIDByName_ValidName() {
        Player player = new Player("Flavio", 1);
        playerHandler.addPlayer(player);
        assertEquals("Flavio_1", playerHandler.getPlayerIDByName("Flavio")); // Verify that the correct ID is returned for a valid name

        assertNull(playerHandler.getPlayerIDByName("name"));
    }
    @Test
    public void testPlayerLeave() {
        // Create a PlayerHandler object
        PlayerHandler playerHandler = new PlayerHandler();

        // Create mock Player objects
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);
        when(player1.getID()).thenReturn("1");
        when(player2.getID()).thenReturn("2");
        when(player3.getID()).thenReturn("3");

        // Add players to the player list
        playerHandler.addPlayer(player1);
        playerHandler.addPlayer(player2);
        playerHandler.addPlayer(player3);

        // Call the playerLeave method with different player IDs and game states
        playerHandler.playerLeave("2", GameState.WAIT);
        playerHandler.playerLeave("3", GameState.GAME);

    }


}

