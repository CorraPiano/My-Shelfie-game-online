package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BagPersonalTest {


    @Test
    void drawPersonalGoalCard() {
        // Test drawing a personal goal card
        BagPersonal bagPersonal = new BagPersonal();
        int initialSize = bagPersonal.getBagPersonalSize();
        PersonalGoalCard card = bagPersonal.drawPersonalGoalCard();

        assertNotNull(card);
        assertEquals(initialSize - 1, bagPersonal.getBagPersonalSize());
    }

    @Test
    void getBagPersonalSize() {
        // Test getting the bag personal size
        BagPersonal bagPersonal = new BagPersonal();
        int initialSize = bagPersonal.getBagPersonalSize();
        assertEquals(12, initialSize);

        for (int i = 0; i < initialSize; i++) {
            bagPersonal.drawPersonalGoalCard();
        }

        int sizeAfterDrawingAllCards = bagPersonal.getBagPersonalSize();
        assertEquals(0, sizeAfterDrawingAllCards);
    }

}