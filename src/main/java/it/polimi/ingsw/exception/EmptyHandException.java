package it.polimi.ingsw.exception;

public class EmptyHandException extends Exception{
    public String toString(){
        return "you can't put in the bookshelf an empty hand";
    }
}
