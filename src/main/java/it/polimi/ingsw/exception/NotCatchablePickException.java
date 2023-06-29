package it.polimi.ingsw.exception;

public class NotCatchablePickException extends Exception{
    public String toString(){
        return "You can't pick the item selected";
    }
}
