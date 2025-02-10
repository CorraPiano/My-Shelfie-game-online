package it.polimi.ingsw.exception;

public class NotEnoughSpacePutException extends Exception{
    public String toString(){
        return "There isn't enough space in the column you selected";
    }
}
