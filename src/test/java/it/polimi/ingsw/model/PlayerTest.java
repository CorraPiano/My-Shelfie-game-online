package it.polimi.ingsw.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
        private Player player;

        @BeforeEach
        public void setUp() {
            player = new Player("Flavio", 1);
        }

        @Test
        public void testGetName() {
            assertEquals("Flavio", player.getName());
        }

        @Test
        public void testGetLibrary() {
            assertNotNull(player.getLibrary());
            assertEquals("Flavio", player.getLibrary().getName());
        }

        @Test
        public void testSetToken1() {
            assertNull(player.getToken1());

            Token token1 = new Token(2);
            player.setToken1(token1);

            assertEquals(token1, player.getToken1());
        }

        @Test
        public void testGetState() {
            assertEquals(PlayerState.ACTIVE, player.getState());
        }

        @Test
        public void testIsConnected() {
            assertTrue(player.isConnected());
        }

        @Test
        public void testIsInactive() {
            assertFalse(player.isInactive());
        }

        @Test
        public void testDisconnect() {
            player.disconnect();
            assertEquals(PlayerState.DISCONNECTED, player.getState());
        }

        @Test
        public void testReconnect() {
            player.disconnect();
            player.reconnect();
            assertEquals(PlayerState.ACTIVE, player.getState());
        }

        @Test
        public void testLeave() {
            player.leave();
            assertEquals(PlayerState.INACTIVE, player.getState());
        }

        @Test
        public void testSetPersonalGoalCard() {
            // Create a sample PersonalGoalCard
            PersonalGoalCard personalCard = new PersonalGoalCard(5);

            // Set the PersonalGoalCard for the player
            player.setPersonalGoalCard(personalCard);

            // Verify the player's personalCard is set correctly
            assertEquals(personalCard, player.getPersonalCard());

            // Verify the PersonalGoalCard's bookshelf is set to player's library
            assertEquals(player.getLibrary(), personalCard.getLibrary());

            // Verify the PersonalGoalCard's ID is set to player's ID
            assertEquals(player.getID(), personalCard.getID());
        }

        @Test
        public void testSetEndGameToken() {
            player.setEndGameToken();

            // Verify that the endGameToken is not null
            assertNotNull(player.getEndGameToken());
        }

        @Test
        public void testHaveToken1() {
            // Initially, token1 should be null
            assertFalse(player.haveToken1());

            // Set token1 for the player
            player.setToken1(new Token(2));

            // Now, the player should have token1
            assertTrue(player.haveToken1());
        }

        @Test
        public void testHaveToken2() {
            // Initially, token2 should be null
            assertFalse(player.haveToken2());

            // Set token2 for the player
            player.setToken2(new Token(3));

            // Now, the player should have token2
            assertTrue(player.haveToken2());
        }

        @Test
        public void testgetToken2(){
            Assertions.assertNull(player.getToken2());
        }

        @Test
        public void updatePoints(){
            player.setPersonalGoalCard(new PersonalGoalCard(5));
            player.updatePoints(true);
        }
}
