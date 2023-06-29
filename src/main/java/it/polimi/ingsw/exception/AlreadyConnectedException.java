package it.polimi.ingsw.exception;

public class AlreadyConnectedException extends Exception {

    public String toString(){
        return "You are already connected to the game";
    }
}
