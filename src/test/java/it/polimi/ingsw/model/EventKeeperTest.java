package it.polimi.ingsw.model;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.connection.ReconnectType;
import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.*;
import it.polimi.ingsw.exception.GameModeException;
import it.polimi.ingsw.exception.NumPlayersException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        public void testRemovePersonalList0() {
            String playerId = "Player1";
            eventKeeper.addPersonalList(playerId);

            eventKeeper.removePersonalList(playerId);

            //assertFalse(eventKeeper.isPresentPersonal(playerId));
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
        public void testRemovePersonalList1() {
            // Create an EventKeeper object
            EventKeeper eventKeeper = new EventKeeper();

            // Add a personal event list for a player with ID "player1"
            eventKeeper.addPersonalList("player1");

            // Verify that the personal event list exists
            assertTrue(eventKeeper.getPersonalList().containsKey("player1"));

            // Call the removePersonalList method to remove the personal event list
            eventKeeper.removePersonalList("player1");

            // Verify that the personal event list has been removed
            assertFalse(eventKeeper.getPersonalList().containsKey("player1"));
            assertFalse(eventKeeper.getOffsets().containsKey("player1"));
            assertFalse(eventKeeper.getOffsetsGui().containsKey("player1"));
            assertFalse(eventKeeper.getStatus().containsKey("player1"));
            assertFalse(eventKeeper.getPersonalListGui().containsKey("player1"));
        }
        @Test
        public void checkActivityTest(){
            eventKeeper.checkActivity("Ciao");
        }
      /*  @Test
        public void testFixOffset() {
            // Create an EventKeeper object
            EventKeeper eventKeeper = new EventKeeper();

            // Create a mock Sendable object
            Sendable sendable = mock(Sendable.class);
            when(sendable.getHeader()).thenReturn(MessageHeader.STARTGAME);

            // Set up the local objects in the EventKeeper
            LocalHand localHand = eventKeeper.getLocalHand();
            LocalBoard localBoard = eventKeeper.getLocalBoard();
            LocalPlayerList localPlayerList = eventKeeper.getLocalPlayerList();
            Map<String, LocalBookshelf> localBookshelfMap = eventKeeper.getLocalBookshelfMap();


            localBoard = mock(LocalBoard.class);
            localHand = mock(LocalHand.class);
            localBookshelfMap = new HashMap<>();
            localBookshelfMap.put("bookshelf1", mock(LocalBookshelf.class));

            // Add a personal event list for a player with ID "player1"
            eventKeeper.addPersonalList("player1");

            // Add a sendable to the personal event list for "player1"
            //eventKeeper.getPersonalList().get("player1").add(sendable);

            // Call the fixOffset method with guiMode = true and reset = false
            eventKeeper.fixOffset("player1", ReconnectType.SENDALL);
            // Verify that the personalListGui has been updated
            ArrayList<Sendable> personalListGui = eventKeeper.getPersonalList().get("player1");
            assertTrue(personalListGui.contains(eventKeeper.getLocalBoard()));
            assertTrue(personalListGui.contains(eventKeeper.getLocalHand()));
            assertTrue(personalListGui.contains(eventKeeper.getLocalPlayerList()));
            assertTrue(personalListGui.contains(eventKeeper.getLocalBookshelfMap().get("bookshelf1")));

            // Verify that the offsetsGui and offsets have been updated
            assertEquals(personalListGui.size(), (int) eventKeeper.getOffsetsGui().get("player1"));
            assertEquals(-1, (int) eventKeeper.getOffsets().get("player1"));

            eventKeeper.fixOffset("player1", ReconnectType.GUI);
            eventKeeper.fixOffset("player1", ReconnectType.SENDNEW);
            // Verify that the personalListGui and offsetsGui have been reset
            assertEquals(0, eventKeeper.getPersonalList().get("player1").size());
            assertEquals(-1, (int) eventKeeper.getOffsetsGui().get("player1"));
        }*/
        @Test
        public void testGetListenablePersonal() {
            // Create an EventKeeper object
            EventKeeper eventKeeper = new EventKeeper();

            // Add a personal event list for a player with ID "player1"
            eventKeeper.addPersonalList("player1");

            // Create mock Sendable objects
            Sendable sendable1 = mock(Sendable.class);
            Sendable sendable2 = mock(Sendable.class);
            when(sendable1.getHeader()).thenReturn(MessageHeader.STARTGAME);
            when(sendable2.getHeader()).thenReturn(MessageHeader.TIMER);

            // Add mock Sendable objects to the personal event list for "player1"
            eventKeeper.getPersonalList().get("player1").add(sendable1);
            eventKeeper.getPersonalList().get("player1").add(sendable2);

            // Call the getListenablePersonal method
            Sendable result1 = eventKeeper.getListenablePersonal("player1");
            Sendable result2 = eventKeeper.getListenablePersonal("player1");
            Sendable result3 = eventKeeper.getListenablePersonal("player1");

        }
        @Test
        public void testUpdate() {
            // Create an EventKeeper object
            EventKeeper eventKeeper = new EventKeeper();

            // Create mock Sendable objects
            Sendable sendable1 = mock(Sendable.class);
            Sendable sendable2 = mock(Sendable.class);
            Sendable sendable3 = mock(Sendable.class);
            Sendable sendable4 = mock(Sendable.class);
            when(sendable1.getHeader()).thenReturn(MessageHeader.BOARD);
            when(sendable2.getHeader()).thenReturn(MessageHeader.PLAYERLIST);
            when(sendable3.getHeader()).thenReturn(MessageHeader.HAND);
            when(sendable4.getHeader()).thenReturn(MessageHeader.BOOKSHELF);

        }

        @Test
        public void testAddSendable() {
            EventKeeper eventKeeper = new EventKeeper();
            eventKeeper.addPersonalList("a");
            eventKeeper.addPersonalList("b");
            eventKeeper.addPersonalList("c");
            eventKeeper.addPersonalList("d");
            eventKeeper.notifyAll(new LocalBookshelf("a"));
            eventKeeper.notifyAll(new LocalBookshelf("b"));
            eventKeeper.notifyAll(new LocalBookshelf("c"));
            eventKeeper.notifyAll(new LocalBookshelf("d"));
            eventKeeper.notifyAll(new LocalHand());
            eventKeeper.notifyAll(new CreateMessage("",GameMode.EXPERT,4));
            eventKeeper.notifyAll(new JoinMessage("a"));
            eventKeeper.notifyAll(new JoinMessage("b"));
            eventKeeper.notifyAll(new JoinMessage("c"));
            eventKeeper.notifyAll(new JoinMessage("d"));
            eventKeeper.notifyAll(new StartGameMessage());
            eventKeeper.notifyAll(new NewTurnMessage("a"));
            eventKeeper.notifyAll(new LocalBookshelf("a"));
            eventKeeper.notifyAll(new LocalBookshelf("b"));
            eventKeeper.notifyAll(new LocalBookshelf("c"));
            eventKeeper.notifyAll(new LocalBookshelf("d"));
            eventKeeper.notifyAll(new NewTurnMessage("b"));
            eventKeeper.notifyAll(new LocalBookshelf("a"));
            eventKeeper.notifyAll(new LocalBookshelf("b"));
            eventKeeper.notifyAll(new LocalBookshelf("c"));
            eventKeeper.notifyAll(new LocalBookshelf("d"));
            eventKeeper.notifyAll(new NewTurnMessage("c"));
            eventKeeper.notifyAll(new LocalBookshelf("a"));
            eventKeeper.notifyAll(new LocalBookshelf("b"));
            eventKeeper.notifyAll(new LocalBookshelf("c"));
            eventKeeper.notifyAll(new LocalBookshelf("d"));
            eventKeeper.notifyAll(new NewTurnMessage("d"));
            eventKeeper.notifyAll(new LocalBookshelf("a"));
            eventKeeper.notifyAll(new LocalBookshelf("b"));
            eventKeeper.notifyAll(new LocalBookshelf("c"));
            eventKeeper.notifyAll(new LocalBookshelf("d"));
            eventKeeper.notifyAll(new EndGameMessage("a",EndCause.LAST_ROUND_FINISHED));
            eventKeeper.notifyToID("a",new ChatMessage("",""));
            eventKeeper.notifyToID("b",new ChatMessage("",""));
            eventKeeper.notifyToID("c",new ChatMessage("",""));
            eventKeeper.notifyToID("d",new ChatMessage("",""));

            eventKeeper.fixOffset("a",ReconnectType.GUI);
            eventKeeper.fixOffset("a",ReconnectType.SENDALL);
            eventKeeper.fixOffset("a",ReconnectType.SENDNEW);
        }
    }
