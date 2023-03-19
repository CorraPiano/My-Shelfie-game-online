package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Set;

/*
* CLASSE CHE IMPLEMENTA UNA STRUTTURA DATI PER MEMORIZZARE LE COORDINATE
*
* Green yellow  blue    pink    cyan    white
* 0     1       2       3       4       5
*/
public class DataCard {
    private final HashMap<int[], Integer> m;
    public DataCard(int n) {
        this.m = new HashMap<int[], Integer>();

        if(n==0){
            m.put(new int[]{0, 0}, 3);
            m.put(new int[]{0, 2}, 2);
            m.put(new int[]{1, 4}, 0);
            m.put(new int[]{2, 3}, 5);
            m.put(new int[]{3, 1}, 1);
            m.put(new int[]{5, 2}, 4);
            return;
        }
        if(n==1){
            m.put(new int[]{1, 1}, 3);
            m.put(new int[]{2, 0}, 0);
            m.put(new int[]{2, 2}, 1);
            m.put(new int[]{3, 4}, 5);
            m.put(new int[]{4, 3}, 4);
            m.put(new int[]{5, 4}, 2);
            return;
        }
        if(n==2){
            m.put(new int[]{1, 1}, 2);
            m.put(new int[]{1, 3}, 1);
            m.put(new int[]{2, 2}, 3);
            m.put(new int[]{3, 1}, 0);
            m.put(new int[]{3, 4}, 4);
            m.put(new int[]{5, 0}, 5);
            return;
        }
        if(n==3){
            m.put(new int[]{0, 4}, 1);
            m.put(new int[]{2, 0}, 4);
            m.put(new int[]{2, 2}, 2);
            m.put(new int[]{3, 3}, 3);
            m.put(new int[]{4, 1}, 5);
            m.put(new int[]{4, 2}, 0);
            return;
        }
        if(n==4){
            m.put(new int[]{1, 1}, 4);
            m.put(new int[]{3, 1}, 2);
            m.put(new int[]{3, 2}, 5);
            m.put(new int[]{4, 2}, 3);
            m.put(new int[]{5, 4}, 1);
            m.put(new int[]{5, 3}, 0);
            return;
        }
        if(n==5){
            m.put(new int[]{0, 2}, 4);
            m.put(new int[]{2, 4}, 0);
            m.put(new int[]{4, 3}, 5);
            m.put(new int[]{4, 1}, 1);
            m.put(new int[]{4, 3}, 2);
            m.put(new int[]{5, 0}, 3);
            return;
        }
        if(n==6){
            m.put(new int[]{0, 0}, 0);
            m.put(new int[]{1, 3}, 2);
            m.put(new int[]{2, 1}, 3);
            m.put(new int[]{3, 0}, 4);
            m.put(new int[]{4, 4}, 1);
            m.put(new int[]{5, 2}, 5);
            return;
        }
        if(n==7){
            m.put(new int[]{0, 4}, 2);
            m.put(new int[]{1, 1}, 0);
            m.put(new int[]{2, 2}, 4);
            m.put(new int[]{3, 0}, 3);
            m.put(new int[]{4, 3}, 5);
            m.put(new int[]{5, 3}, 1);
            return;
        }
        if(n==8){
            m.put(new int[]{0, 1}, 1);
            m.put(new int[]{2, 2}, 0);
            m.put(new int[]{3, 4}, 5);
            m.put(new int[]{4, 1}, 4);
            m.put(new int[]{4, 4}, 3);
            m.put(new int[]{5, 0}, 2);
            return;
        }
        if(n==9){
            m.put(new int[]{0, 4}, 4);
            m.put(new int[]{1, 1}, 1);
            m.put(new int[]{2, 0}, 5);
            m.put(new int[]{3, 3}, 0);
            m.put(new int[]{4, 1}, 2);
            m.put(new int[]{5, 3}, 3);
            return;
        }
        if(n==10){
            m.put(new int[]{0, 2}, 3);
            m.put(new int[]{1, 1}, 5);
            m.put(new int[]{2, 0}, 1);
            m.put(new int[]{3, 2}, 2);
            m.put(new int[]{4, 4}, 0);
            m.put(new int[]{5, 3}, 4);
            return;
        }
        if(n==11){
            m.put(new int[]{0, 2}, 5);
            m.put(new int[]{1, 1}, 3);
            m.put(new int[]{2, 2}, 2);
            m.put(new int[]{3, 3}, 4);
            m.put(new int[]{4, 4}, 1);
            m.put(new int[]{5, 0}, 0);
        }

    }

    int getXY(int i, int j){
        return m.get(new int[]{i, j});
    }
    Set<int[]> getCoordinate(){
        return m.keySet();
    }
}
