package it.polimi.ingsw.exception;

public class LimitReachedPickException extends Exception{
    public String toString(){
        return "you can't do more than 3 picks";
    }
}
