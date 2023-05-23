package it.polimi.ingsw.model;

import it.polimi.ingsw.client.localModel.LocalHand;
import it.polimi.ingsw.exception.WrongContentOrderException;
import it.polimi.ingsw.exception.WrongLengthOrderException;

import java.util.ArrayList;

public class Hand extends Listenable {
    private ArrayList<Item> hand;
    private ArrayList<Coordinates> coordinatesList;

    public Hand (){
        hand = new ArrayList<Item>();
        coordinatesList = new ArrayList<Coordinates>();
        //setListener(listener);
    }

    public int getSize(){
        return  hand.size();
    }

    public ArrayList<Item> getHand(){
        return hand;
    }

    public ArrayList<Coordinates> getCoordinatesList(){
        return coordinatesList;
    }

    public void putItem(Item item, Coordinates coordinates){
        hand.add(item);
        coordinatesList.add(coordinates);
        //notifyListener("HAND");
        notifyUpdate();
    }

    public boolean containsCoords(Coordinates coordinates){
        return coordinatesList.contains(coordinates);
    }

    public void clear(){
        hand.clear();
        coordinatesList.clear();
        //notifyListener("HAND");
        notifyUpdate();
    }

    public void selectOrder(ArrayList<Integer> list) throws WrongLengthOrderException, WrongContentOrderException {
        checkContentOrderList(list);
        ArrayList<Item> supp1=new ArrayList<>();
        ArrayList<Coordinates> supp2=new ArrayList<>();

        for(int i: list){
            supp1.add(hand.get(i));
            supp2.add(coordinatesList.get(i));
        }
        hand = supp1;
        coordinatesList = supp2;
        //notifyListener("HAND");
        notifyUpdate();
    }

    private void checkContentOrderList(ArrayList<Integer> list) throws WrongLengthOrderException, WrongContentOrderException {
        if(list.size()!=hand.size())
            throw new WrongLengthOrderException();
        for(int i=0;i<hand.size();i++) {
            if(!list.contains(i))
                throw new WrongContentOrderException();
        }
    }

    public boolean checkNewCoordinates(Coordinates coordinates) {
        if (coordinatesList.isEmpty())
            return true;

        boolean checkNear = false;
        boolean checkSameColumn = true;
        boolean checkSameRow = true;
        int row = coordinates.getRow();
        int column = coordinates.getColumn();

        for (Coordinates c : coordinatesList) {
            if (c.equals(coordinates))
                return false;
            if (c.getRow() != row)
                checkSameRow = false;
            if (c.getColumn() != column)
                checkSameColumn = false;
            if (c.getColumn() == column + 1 || c.getColumn() == column - 1 || c.getRow() == row + 1 || c.getRow() == row - 1)
                checkNear = true;
            if (!(checkSameRow || checkSameColumn))
                break;
        }
        return checkNear && (checkSameRow || checkSameColumn);
    }

    @Override
    public LocalHand getLocal() {
        Item[] items = new Item[hand.size()];
        for(int i=0;i<hand.size();i++)
            items[i]=hand.get(i);
        return new LocalHand(items,hand.size());
    }
}
