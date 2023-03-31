package it.polimi.ingsw;
import it.polimi.ingsw.model.PersonalGoalCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class PersonalGoalCardTest {

    @Test
    void calculatePointsTest(){
        PersonalGoalCard g = new PersonalGoalCard(null);
        Assertions.assertEquals(0, g.calculatePoints(), "library = null");
    }

}
