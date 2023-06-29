package it.polimi.ingsw.exception;

public class InvalidColumnPutException extends Exception{
    public String toString(){
        return "The column you selected is not valid";
    }
}
