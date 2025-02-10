package it.polimi.ingsw.exception;

public class NumPlayersException extends Exception {
    public String toString(){
        return "The number of players you selected is not valid";
    }
}
