package it.polimi.ingsw.exception;

public class NameAlreadyExistentException extends Exception{
    public String toString(){
        return "There is already a player with the name you chose, try with a new one";
    }
}
