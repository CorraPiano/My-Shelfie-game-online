package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

/*
* CLASSE CHE IMPLEMENTA UNA STRUTTURA DATI PER MEMORIZZARE LE COORDINATE
*
* Green yellow  blue    pink    cyan    white
* 0     1       2       3       4       5
*/
public class DataCard {
    private final HashMap<Coordinates, Integer> m;
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

    public static void main(String[] args) {
        DataCard card = new DataCard(3);
        Coordinates c = new Coordinates(0,4);
        Set<Coordinates> key = card.getCoordinate();
        System.out.println(card.getColor(c));
        System.out.println();
    }
}
