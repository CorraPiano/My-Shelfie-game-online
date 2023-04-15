package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Item> hand;
    private ArrayList<Coordinates> coordinatesList;

    public Hand (){
        hand = new ArrayList<Item>();
        coordinatesList = new ArrayList<Coordinates>();
    }

    public void pickItem(Item item, Coordinates coordinates){
        hand.add(item);
        coordinatesList.add(coordinates);
    }

    public boolean containsCoords(Coordinates coordinates){
        if(coordinatesList.contains(coordinates))
            return true;
        else
            return false;
    }

    public ArrayList<Item> getHand(){
        return hand;
    }

    public ArrayList<Coordinates> getCoords(){
        return coordinatesList;
    }

    public void clear(){
        hand.clear();
    }

    public void selectOrderHand(ArrayList<Integer> list){
        // effettuare controlli lato server
        ArrayList<Item> supp1=new ArrayList<>();
        ArrayList<Coordinates> supp2=new ArrayList<>();
        for(int i: list){
            supp1.add(hand.get(i));
            supp2.add(coordinatesList.get(i));
        }
        hand = supp1;
        coordinatesList = supp2;
    }
}
