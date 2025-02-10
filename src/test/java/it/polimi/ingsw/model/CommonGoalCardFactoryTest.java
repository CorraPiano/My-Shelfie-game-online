package it.polimi.ingsw.model;

import it.polimi.ingsw.model.common_card_classes.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;


class CommonGoalCardFactoryTest {
    @Test
    void getCommonGoalCard() {
        CommonGoalCardFactory test = new CommonGoalCardFactory();
        Assertions.assertTrue(test.getCommonGoalCard(0) instanceof GroupCommonGoalCard);
        Assertions.assertTrue(test.getCommonGoalCard(1) instanceof GroupCommonGoalCard);
        Assertions.assertTrue(test.getCommonGoalCard(2) instanceof CornerCommonGoalCard);
        Assertions.assertTrue(test.getCommonGoalCard(3) instanceof GroupCommonGoalCard);
        Assertions.assertTrue(test.getCommonGoalCard(4) instanceof MaxDifferentCommonGoalCard);
        Assertions.assertTrue(test.getCommonGoalCard(5) instanceof SameEightCommonGoalCard);
        Assertions.assertTrue(test.getCommonGoalCard(6) instanceof DiagonalCommonGoalCard);
        Assertions.assertTrue(test.getCommonGoalCard(7) instanceof MaxDifferentCommonGoalCard);
        Assertions.assertTrue(test.getCommonGoalCard(8) instanceof DifferentCommonGoalCard);
        Assertions.assertTrue(test.getCommonGoalCard(9) instanceof DifferentCommonGoalCard);
        Assertions.assertTrue(test.getCommonGoalCard(10) instanceof XCommonGoalCard);
        Assertions.assertTrue(test.getCommonGoalCard(11) instanceof DiagonalCommonGoalCard);
        Assertions.assertEquals(null, test.getCommonGoalCard(12));

    }
}