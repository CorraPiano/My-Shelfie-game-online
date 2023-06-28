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
    private Gameplay gameplay;
    private ArrayList<Sendable> listenableList;
    private HashMap<String, ArrayList<Sendable>> personalList;
    private HashMap<String, Long> lastPing;
    private ArrayList<String> idList;
    private HashMap<String, Integer> offsets;

    @BeforeEach
    void setUp() throws GameModeException, NumPlayersException {
        gameplay = new Gameplay(GameMode.EXPERT,3,10547);
        //eventKeeper = new EventKeeper(gameplay);
        listenableList = new ArrayList<>();
        personalList = new HashMap<>();
        lastPing = new HashMap<>();
        idList = new ArrayList<>();
        offsets = new HashMap<>();
    }

    @Test
    void addPersonalList() {

    }

    @Test
    void isPresent() {
    }

    @Test
    void getListenable() {
    }

    @Test
    void nextpos() {
    }

    @Test
    void isPresentPersonal() {
    }

    @Test
    void resetOffset() {
    }

    @Test
    void getListenablePersonal() {
    }

    @Test
    void testNotifyAll() {
    }

    @Test
    void notifyToID() {
    }

    @Test
    void checkConnection() {
    }

    @Test
    void ping() {
    }

    @Test
    void resetPingKeeper() {
    }
}