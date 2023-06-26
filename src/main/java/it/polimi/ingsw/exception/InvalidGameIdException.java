package it.polimi.ingsw.exception;

public class InvalidGameIdException extends Exception{
    public String toString(){
        return "there is not a game with the ID you selected";
    }
}
