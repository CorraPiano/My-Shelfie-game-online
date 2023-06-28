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
        int curr_test = 0, num_test = 0;
        // Personal 0
        num_test = 3;
        curr_test = 0;
        for(int i=0; i< num_test; i++){
            g = new PersonalGoalCard(0);
            g.setBookshelf(input.getInputLibrary(i + curr_test));
            Assertions.assertEquals(input.getResult(i + curr_test), g.calculatePoints(), "Matrice numero: " + (i + curr_test));
        }

        // Personal 2
        curr_test += num_test;
        num_test = 2;
        for(int i=0; i< num_test; i++){
            g = new PersonalGoalCard(2);
            g.setBookshelf(input.getInputLibrary(i + curr_test));
            Assertions.assertEquals(input.getResult(i + curr_test), g.calculatePoints(), "Matrice numero: " + (i + curr_test));
        }
        // Personal 3
        curr_test += num_test;
        num_test = 1;
        for(int i=0; i< num_test; i++){
            g = new PersonalGoalCard(3);
            g.setBookshelf(input.getInputLibrary(i + curr_test));
            Assertions.assertEquals(input.getResult(i + curr_test), g.calculatePoints(), "Matrice numero: " + (i + curr_test));
        }
        // Personal 4
        curr_test += num_test;
        num_test = 1;
        for(int i=0; i< num_test; i++){
            g = new PersonalGoalCard(4);
            g.setBookshelf(input.getInputLibrary(i + curr_test));
            Assertions.assertEquals(input.getResult(i + curr_test), g.calculatePoints(), "Matrice numero: " + (i + curr_test));
        }
        // Personal 5
        curr_test += num_test;
        num_test = 1;
        for(int i=0; i< num_test; i++){
            g = new PersonalGoalCard(5);
            g.setBookshelf(input.getInputLibrary(i + curr_test));
            Assertions.assertEquals(input.getResult(i + curr_test), g.calculatePoints(), "Matrice numero: " + (i + curr_test));
        }
        // Personal 6
        curr_test += num_test;
        num_test = 1;
        for(int i=0; i< num_test; i++){
            g = new PersonalGoalCard(6);
            g.setBookshelf(input.getInputLibrary(i + curr_test));
            Assertions.assertEquals(input.getResult(i + curr_test), g.calculatePoints(), "Matrice numero: " + (i + curr_test));
        }
        // Personal 7
        curr_test += num_test;
        num_test = 1;
        for(int i=0; i< num_test; i++){
            g = new PersonalGoalCard(7);
            g.setBookshelf(input.getInputLibrary(i + curr_test));
            Assertions.assertEquals(input.getResult(i + curr_test), g.calculatePoints(), "Matrice numero: " + (i + curr_test));
        }
        // Personal 8
        curr_test += num_test;
        num_test = 1;
        for(int i=0; i< num_test; i++){
            g = new PersonalGoalCard(8);
            g.setBookshelf(input.getInputLibrary(i + curr_test));
            Assertions.assertEquals(input.getResult(i + curr_test), g.calculatePoints(), "Matrice numero: " + (i + curr_test));
        }
        // Personal 9
        curr_test += num_test;
        num_test = 1;
        for(int i=0; i< num_test; i++){
            g = new PersonalGoalCard(9);
            g.setBookshelf(input.getInputLibrary(i + curr_test));
            Assertions.assertEquals(input.getResult(i + curr_test), g.calculatePoints(), "Matrice numero: " + (i + curr_test));
        }
        // Personal 10
        curr_test += num_test;
        num_test = 1;
        for(int i=0; i< num_test; i++){
            g = new PersonalGoalCard(10);
            g.setBookshelf(input.getInputLibrary(i + curr_test));
            Assertions.assertEquals(input.getResult(i + curr_test), g.calculatePoints(), "Matrice numero: " + (i + curr_test));
        }
        // Personal 11
        curr_test += num_test;
        num_test = 1;
        for(int i=0; i< num_test; i++){
            g = new PersonalGoalCard(11);
            g.setBookshelf(input.getInputLibrary(i + curr_test));
            Assertions.assertEquals(input.getResult(i + curr_test), g.calculatePoints(), "Matrice numero: " + (i + curr_test));
        }

    }
    @Test
    void getNumTest(){
        PersonalGoalCard test = new PersonalGoalCard(3);
        Assertions.assertEquals(3, test.getNum());
    }
    @Test
    void getIDTest(){
        PersonalGoalCard test = new PersonalGoalCard(6);
        Assertions.assertEquals(null, test.getID());
    }
    @Test
    void setIDTest(){
        PersonalGoalCard test = new PersonalGoalCard(3);
        test.setID("nicola");
        Assertions.assertEquals("nicola", test.getID());
    }
    @Test
    void getCardTest(){
        PersonalGoalCard test = new PersonalGoalCard(3);
        DataCard dataCardTest = test.getCard();
        Assertions.assertNotEquals(null, dataCardTest);
    }
    @Test
    void getLocalTest(){
        PersonalGoalCard test = new PersonalGoalCard(3);
        Assertions.assertNotEquals(null, test.getLocal());
    }
    @Test
    void setBookShelfTest(){
        PersonalGoalCard test = new PersonalGoalCard(3);
        test.setBookshelf(new Bookshelf("nicola"));
        Assertions.assertNotNull(test.getLibrary());
    }
}
