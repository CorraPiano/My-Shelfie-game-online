package it.polimi.ingsw.model;

import it.polimi.ingsw.model.util.TestFactory;
import it.polimi.ingsw.model.util.InputTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
 public class CommonGoalCardTest {
    @Test
    void checkFullFilTest(){

        InputTest input = TestFactory.createTest("src/test/java/it/polimi/ingsw//model/util/CommonTestFile.txt");

        CommonGoalCardFactory cardFactory = new CommonGoalCardFactory();
        CommonGoalCard card;

        int i = 0;

        // Diagonal -> matrix
        card = cardFactory.getCommonGoalCard(11);
        int numTest = 5;
        boolean result;
        while (i<numTest){
            result = card.checkFullFil(input.getInputLibrary(i));
            Assertions.assertEquals(input.getResult(i), (result) ? 1 : 0, "Matrice numero: "+ i);
            i++;
        }
        // Diagonal -> diagonal
        card = cardFactory.getCommonGoalCard(6);
        numTest += 5;
        while (i<numTest){
            result = card.checkFullFil(input.getInputLibrary(i));
            Assertions.assertEquals(input.getResult(i), (result) ? 1 : 0, "Matrice numero: "+ i);
            i++;
        }
        //Group -> x4 group
        card = cardFactory.getCommonGoalCard(3);
        numTest += 3;
        while (i<numTest){
            result = card.checkFullFil(input.getInputLibrary(i));
            Assertions.assertEquals(input.getResult(i), (result) ? 1 : 0, "Matrice numero: "+ i);
            i++;
        }
        //Group -> x4 line
        card = cardFactory.getCommonGoalCard(1);
        numTest += 3;
        while (i<numTest){
            result = card.checkFullFil(input.getInputLibrary(i));
            Assertions.assertEquals(input.getResult(i), (result) ? 1 : 0, "Matrice numero: "+ i);
            i++;
        }
        //Group -> x2
        card = cardFactory.getCommonGoalCard(0);
        numTest += 2;
        while (i<numTest){
            result = card.checkFullFil(input.getInputLibrary(i));
            Assertions.assertEquals(input.getResult(i), (result) ? 1 : 0, "Matrice numero: "+ i);
            i++;
        }
        // Corner
        card = cardFactory.getCommonGoalCard(2);
        numTest += 2;
        while (i<numTest){
            result = card.checkFullFil(input.getInputLibrary(i));
            Assertions.assertEquals(input.getResult(i), (result) ? 1 : 0, "Matrice numero: "+ i);
            i++;
        }
        // X
        card = cardFactory.getCommonGoalCard(10);
        numTest +=2;
        while (i<numTest){
            result = card.checkFullFil(input.getInputLibrary(i));
            Assertions.assertEquals(input.getResult(i), (result) ? 1 : 0, "Matrice numero: "+ i);
            i++;
        }
        // Different -> columns
        card = cardFactory.getCommonGoalCard(8);
        numTest +=2;
        while (i<numTest){
            result = card.checkFullFil(input.getInputLibrary(i));
            Assertions.assertEquals(input.getResult(i), (result) ? 1 : 0, "Matrice numero: "+ i);
            i++;
        }
        // Different -> rows
        card = cardFactory.getCommonGoalCard(9);
        numTest +=2;
        while (i<numTest){
            result = card.checkFullFil(input.getInputLibrary(i));
            Assertions.assertEquals(input.getResult(i), (result) ? 1 : 0, "Matrice numero: "+ i);
            i++;
        }
        // MaxDifferent -> columns
        card = cardFactory.getCommonGoalCard(4);
        numTest += 3;
        while (i<numTest){
            result = card.checkFullFil(input.getInputLibrary(i));
            Assertions.assertEquals(input.getResult(i), (result) ? 1 : 0, "Matrice numero: "+ i);
            i++;
        }
        // MaxDifferent -> rows
        card = cardFactory.getCommonGoalCard(7);
        numTest += 3;
        while (i<numTest){
            result = card.checkFullFil(input.getInputLibrary(i));
            Assertions.assertEquals(input.getResult(i), (result) ? 1 : 0, "Matrice numero: "+ i);
            i++;
        }
        // SameEight
        card = cardFactory.getCommonGoalCard(5);
        numTest += 2;
        while (i<numTest){
            result = card.checkFullFil(input.getInputLibrary(i));
            Assertions.assertEquals(input.getResult(i), (result) ? 1 : 0, "Matrice numero: "+ i);
            i++;
        }



    }
    @Test
    void setTokenListTest(){}
    @Test
    void showTokenTest(){}
    @Test
    void popTokenTest(){}

}