package it.polimi.ingsw.model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class DataCardTest {
    @Test
    void getXYTest(){
        DataCard test = new DataCard(4);
        Integer color = test.getXY(new Coordinates(1,1)).get();
        Assertions.assertEquals(4, color);
        Assertions.assertEquals(null, test.getXY(new Coordinates(1,5)));
    }
    @Test
    void getCoordinateTest(){
        DataCard test;
        for(int i = 0; i<12; i++){
            test = new DataCard(i);
            Assertions.assertNotEquals(null, test.getCoordinate());
        }
    }
    @Test
    void getCardTest(){
        DataCard test;
        for(int i = 0; i<12; i++){
            test = new DataCard(i);
            Assertions.assertNotEquals(null, test.getCard());
        }
    }
    @Test
    void getColorTest(){
        DataCard test = new DataCard(4);
        Integer color = test.getColor(new Coordinates(1,1));
        Assertions.assertEquals(4, color);
    }
    @Test
    void DataCardTest(){
        DataCard test = new DataCard(4);
        DataCard t2 = new DataCard(test.getCard());

        Assertions.assertNotEquals(null, t2);
    }

}
