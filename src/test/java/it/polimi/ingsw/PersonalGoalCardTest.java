package it.polimi.ingsw;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.PersonalGoalCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PersonalGoalCardTest {
    Bookshelf library = new Bookshelf();
    PersonalGoalCard g = new PersonalGoalCard(library);
    @Test
    void calculatePointsTest(){
        Assertions.assertEquals(g.calculatePoints(), 0, "Library == null");
    }
}
