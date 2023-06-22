package it.polimi.ingsw.model;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.util.InputTest;
import it.polimi.ingsw.model.util.TestFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class PersonalGoalCardTest {

    @Test
    void calculatePointsTest(){

        InputTest input = TestFactory.createTest("src/test/java/it/polimi/ingsw/model/util/PersonalTestFile.txt");
        PersonalGoalCard g;
        for(int i=0; i< input.numberOfTests(); i++){
            g = new PersonalGoalCard(0);
            g.setBookshelf(input.getInputLibrary(i));
            Assertions.assertEquals(input.getResult(i), g.calculatePoints(), "Matrice numero: " + i);
        }

    }

}
