package it.polimi.ingsw.exception;

public class NotCatchablePickException extends Exception{
    public String toString(){
        return "you can't pick the item you selected";
    }
}
