package it.polimi.ingsw;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.PersonalGoalCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PersonalGoalCardTest {
    @Test
    void calculatePointsTest(){
        PersonalGoalCard g = new PersonalGoalCard(new Bookshelf());
        Assertions.assertEquals(g.calculatePoints(), 0, "Test calculate points");
    }


}
