package it.polimi.ingsw.model;

import it.polimi.ingsw.model.util.TestFactory;
import it.polimi.ingsw.model.util.InputTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommonGoalCardTest {
    @Test
    void checkFullFilTest(){
        CommonGoalCardFactory cardFactory = new CommonGoalCardFactory();
        CommonGoalCard card = cardFactory.getCommonGoalCard(0);
        InputTest input = TestFactory.createTest();
        for(int i=0; i< input.numberOfTests(); i++){
            Assertions.assertEquals(input.getResult(i), card.checkFullFil(input.getInputLibrary(i)));
        }
    }
    @Test
    void setTokenListTest(){}
    @Test
    void showTokenTest(){}
    @Test
    void popTokenTest(){}

}