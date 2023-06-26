package it.polimi.ingsw.exception;

public class EmptySlotPickException extends Exception {
    public String toString(){
        return "there is no item in the coordinates you chose";
    }
}
