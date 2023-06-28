package it.polimi.ingsw.model;

import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.exception.GameModeException;
import it.polimi.ingsw.exception.NumPlayersException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class EventKeeperTest {

        private EventKeeper eventKeeper;

        @BeforeEach
        public void setup() {
            eventKeeper = new EventKeeper();
        }

        @Test
        public void testAddPersonalList() {
            String playerId = "Player1";
            eventKeeper.addPersonalList(playerId);
            assertFalse(eventKeeper.isPresentPersonal(playerId));
        }

        @Test
        public void testRemovePersonalList() {
            String playerId = "Player1";
            eventKeeper.addPersonalList(playerId);

            eventKeeper.removePersonalList(playerId);

            assertFalse(eventKeeper.isPresentPersonal(playerId));
        }

        @Test
        public void testUpdateStatus() {
            String playerId = "Player1";
            eventKeeper.addPersonalList(playerId);

            eventKeeper.updateStatus(playerId, false);

            assertFalse(eventKeeper.checkActivity(playerId));
        }

        @Test
        public void testCheckActivity() {
            String playerId = "Player1";
            eventKeeper.addPersonalList(playerId);

            assertTrue(eventKeeper.checkActivity(playerId));
        }

        @Test
        public void testIsPresentPersonal() {
            String playerId = "Player1";
            eventKeeper.addPersonalList(playerId);

            assertFalse(eventKeeper.isPresentPersonal(playerId));
        }

        @Test
        public void testFixOffset() {
            String playerId = "Player1";
            eventKeeper.addPersonalList(playerId);

            eventKeeper.fixOffset(playerId, true, false);

            assertFalse(eventKeeper.isPresentPersonal(playerId));
        }


    }
