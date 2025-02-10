package it.polimi.ingsw.exception;

public class GameLeftException extends Exception{
    public String toString(){
        return "You left the game, you can't join this game anymore";
    }
}
