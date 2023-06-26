package it.polimi.ingsw.exception;

public class GameLeftException extends Exception{
    public String toString(){
        return "you have left the game, so you can't join this game anymore";
    }
}
