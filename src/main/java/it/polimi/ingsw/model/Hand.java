package it.polimi.ingsw.model;

import it.polimi.ingsw.client.localModel.LocalHand;
import it.polimi.ingsw.exception.WrongContentOrderException;
import it.polimi.ingsw.exception.WrongLengthOrderException;

import java.util.ArrayList;

/**
 * The Hand class represents a player's hand in the game.
 * It contains a list of items and their corresponding coordinates.
 */
public class Hand extends Listenable {
    private ArrayList<Item> hand;
    private ArrayList<Coordinates> coordinatesList;

    /**
     * Constructs a new Hand object.
     * Initializes the list of items and coordinates.
     */
    public Hand (){
        hand = new ArrayList<Item>();
        coordinatesList = new ArrayList<Coordinates>();
        //setListener(listener);
    }

    /**
     * Adds an item to the hand with its corresponding coordinates.
     * Notifies the listeners about the update.
     *
     * @param item        The item to be added to the hand.
     * @param coordinates The coordinates of the item.
     */
    public void putItem(Item item, Coordinates coordinates){
        hand.add(item);
        coordinatesList.add(coordinates);
        //notifyListener("HAND");
        notifyUpdate();
    }

    /**
     * Checks if the hand contains the specified coordinates.
     *
     * @param coordinates The coordinates to check.
     * @return True if the hand contains the coordinates, false otherwise.
     */
    public boolean containsCoords(Coordinates coordinates){
        return coordinatesList.contains(coordinates);
    }

    /**
     * Clears the hand by removing all items and their coordinates.
     * Notifies the listeners about the update.
     */
    public void clear(){
        hand.clear();
        coordinatesList.clear();
        //notifyListener("HAND");
        notifyUpdate();
    }

    /**
     * Reorders the items in the hand based on the specified order list.
     * The order list contains the indices of items in the desired order.
     * Notifies the listeners about the update.
     *
     * @param list The order list specifying the new item order.
     * @throws WrongLengthOrderException   if the length of the order list is different from the size of the hand.
     * @throws WrongContentOrderException if the order list contains invalid indices.
     */
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

    /**
     * Checks if the specified coordinates can be added to the hand.
     * The coordinates must be adjacent to an existing item in the hand
     * and belong to the same row or column.
     *
     * @param coordinates The coordinates to check.
     * @return True if the coordinates can be added to the hand, false otherwise.
     */
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

    /**
     * Returns the size of the hand.
     *
     * @return The size of the hand.
     */
    public int getSize(){
        return  hand.size();
    }

    /**
     * Returns the list of items in the hand.
     *
     * @return The list of items in the hand.
     */
    public ArrayList<Item> getHand(){
        return hand;
    }

    /**
     * Returns the list of coordinates corresponding to the items in the hand.
     *
     * @return The list of coordinates corresponding to the items in the hand.
     */
    public ArrayList<Coordinates> getCoordinatesList(){
        return coordinatesList;
    }

    /**
     * Converts the hand to its local representation.
     *
     * @return The local representation of the hand.
     */
    @Override
    public LocalHand getLocal() {
        Item[] items = new Item[hand.size()];
        for(int i=0;i<hand.size();i++)
            items[i]=hand.get(i);
        ArrayList<Coordinates> coordinates = new ArrayList<>(coordinatesList);
        return new LocalHand(items,hand.size(),coordinates);
    }
}
