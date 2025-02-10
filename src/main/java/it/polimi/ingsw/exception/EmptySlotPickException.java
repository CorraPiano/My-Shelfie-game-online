package it.polimi.ingsw.exception;

public class EmptySlotPickException extends Exception {
    public String toString(){
        return "There are no items at the selected coordinates";
    }
}
