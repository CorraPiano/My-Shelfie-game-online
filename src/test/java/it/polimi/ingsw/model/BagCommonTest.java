package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BagCommonTest {
    private BagCommon bag;

    @BeforeEach
    public void setup() {
        bag = new BagCommon();
    }

    @Test
    public void testDrawCommonGoalCard() {
        int initialSize = bag.getBagCommonSize();

        // Draw a card from the bag
        CommonGoalCard card = bag.drawCommonGoalCard();

        // Assert that the card is not null
        assertNotNull(card);

        // Assert that the size of the bag is reduced by 1
        assertEquals(initialSize - 1, bag.getBagCommonSize());

        // Draw all the remaining cards from the bag
        for (int i = 0; i < initialSize - 1; i++) {
            assertNotNull(bag.drawCommonGoalCard());
        }

        // Assert that the bag is empty after drawing all the cards
        assertNull(bag.drawCommonGoalCard());

        // Assert that the size of the bag is 0
        assertEquals(0, bag.getBagCommonSize());
    }
}