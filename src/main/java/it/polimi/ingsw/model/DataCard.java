package it.polimi.ingsw.model;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.util.Constants;
import it.polimi.ingsw.model.BagItem;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

import static it.polimi.ingsw.model.BagItem.getItemPathByType;

/*
* CLASSE CHE IMPLEMENTA UNA STRUTTURA DATI PER MEMORIZZARE LE COORDINATE
*
* Green yellow  blue    pink    cyan    white
* 0     1       2       3       4       5
*/
public class DataCard implements Serializable {
    private final HashMap<Coordinates, Integer> m;

    public DataCard(HashMap<Coordinates, Integer> m){
        this.m=m;
    }
    public DataCard(int n) {

        this.m = new HashMap<>();

        if(n == 0) {
            m.put(new Coordinates(0, 0), 3);
            m.put(new Coordinates(0, 2), 2);
            m.put(new Coordinates(1, 4), 0);
            m.put(new Coordinates(2, 3), 5);
            m.put(new Coordinates(3, 1), 1);
            m.put(new Coordinates(5, 2), 4);
            return;
        }
        if(n == 1) {
            m.put(new Coordinates(1, 1), 3);
            m.put(new Coordinates(2, 0), 0);
            m.put(new Coordinates(2, 2), 1);
            m.put(new Coordinates(3, 4), 5);
            m.put(new Coordinates(4, 3), 4);
            m.put(new Coordinates(5, 4), 2);
            return;
        }
        if(n == 2) {
            m.put(new Coordinates(1, 1), 2);
            m.put(new Coordinates(1, 3), 1);
            m.put(new Coordinates(2, 2), 3);
            m.put(new Coordinates(3, 1), 0);
            m.put(new Coordinates(3, 4), 4);
            m.put(new Coordinates(5, 0), 5);
            return;
        }
        if(n == 3) {
            m.put(new Coordinates(0, 4), 1);
            m.put(new Coordinates(2, 0), 4);
            m.put(new Coordinates(2, 2), 2);
            m.put(new Coordinates(3, 3), 3);
            m.put(new Coordinates(4, 1), 5);
            m.put(new Coordinates(4, 2), 0);
            return;
        }
        if(n == 4) {
            m.put(new Coordinates(1, 1), 4);
            m.put(new Coordinates(3, 1), 2);
            m.put(new Coordinates(3, 2), 5);
            m.put(new Coordinates(4, 2), 3);
            m.put(new Coordinates(5, 4), 1);
            m.put(new Coordinates(5, 3), 0);
            return;
        }
        if(n == 5) {
            m.put(new Coordinates(0, 2), 4);
            m.put(new Coordinates(0, 4), 0);
            m.put(new Coordinates(2, 3), 5);
            m.put(new Coordinates(4, 1), 1);
            m.put(new Coordinates(4, 3), 2);
            m.put(new Coordinates(5, 0), 3);
            return;
        }
        if(n == 6) {
            m.put(new Coordinates(0, 0), 0);
            m.put(new Coordinates(1, 3), 2);
            m.put(new Coordinates(2, 1), 3);
            m.put(new Coordinates(3, 0), 4);
            m.put(new Coordinates(4, 4), 1);
            m.put(new Coordinates(5, 2), 5);
            return;
        }
        if(n == 7) {
            m.put(new Coordinates(0, 4), 2);
            m.put(new Coordinates(1, 1), 0);
            m.put(new Coordinates(2, 2), 4);
            m.put(new Coordinates(3, 0), 3);
            m.put(new Coordinates(4, 3), 5);
            m.put(new Coordinates(5, 3), 1);
            return;
        }
        if(n == 8) {
            m.put(new Coordinates(0, 1), 1);
            m.put(new Coordinates(2, 2), 0);
            m.put(new Coordinates(3, 4), 5);
            m.put(new Coordinates(4, 1), 4);
            m.put(new Coordinates(4, 4), 3);
            m.put(new Coordinates(5, 0), 2);
            return;
        }
        if(n == 9) {
            m.put(new Coordinates(0, 4), 4);
            m.put(new Coordinates(1, 1), 1);
            m.put(new Coordinates(2, 0), 5);
            m.put(new Coordinates(3, 3), 0);
            m.put(new Coordinates(4, 1), 2);
            m.put(new Coordinates(5, 3), 3);
            return;
        }
        if(n == 10) {
            m.put(new Coordinates(0, 2), 3);
            m.put(new Coordinates(1, 1), 5);
            m.put(new Coordinates(2, 0), 1);
            m.put(new Coordinates(3, 2), 2);
            m.put(new Coordinates(4, 4), 0);
            m.put(new Coordinates(5, 3), 4);
            return;
        }
        if(n == 11) {
            m.put(new Coordinates(0, 2), 5);
            m.put(new Coordinates(1, 1), 3);
            m.put(new Coordinates(2, 2), 2);
            m.put(new Coordinates(3, 3), 4);
            m.put(new Coordinates(4, 4), 1);
            m.put(new Coordinates(5, 0), 0);
        }

    }

    Optional<Integer> getXY(Coordinates c) {
        if(!m.containsKey(c))
            return null;
        Optional<Integer> color = Optional.of(m.get(c));
        return color ;
    }
    Set<Coordinates> getCoordinate(){
        return m.keySet();
    }
    int getColor(Coordinates key){
        return m.get(key);
    }

    public HashMap<Coordinates, Integer> getCard() {
        return m;
    }

    public Item[][] getCardMatrix(){
        Item[][] cardMatrix = new Item[Constants.nRowBookshelf][Constants.nColumnBookshelf];

        for(int i=0;i<Constants.nRowBookshelf;i++) {
            for (int j = 0; j < Constants.nColumnBookshelf; j++) {
                Coordinates coords = new Coordinates(i,j);
                if(m.containsKey(coords))
                    cardMatrix[i][j]=convert(m.get(coords));
            }
        }
        return cardMatrix;
    }

    private Item convert(int n){
        if(n==0)
            return new Item(ItemType.GREEN,getItemPathByType(ItemType.GREEN));
        if(n==1)
            return new Item(ItemType.YELLOW,getItemPathByType(ItemType.YELLOW));
        if(n==2)
            return new Item(ItemType.BLUE,getItemPathByType(ItemType.BLUE));
        if(n==3)
            return new Item(ItemType.PINK,getItemPathByType(ItemType.PINK));
        if(n==4)
            return new Item(ItemType.CYAN,getItemPathByType(ItemType.CYAN));
        if(n==5)
            return new Item(ItemType.WHITE,getItemPathByType(ItemType.WHITE));
        return null;
    }

}
