package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.model.Token;

import java.io.Serializable;

public class localPlayer implements Serializable {
    public final String name;
    public final boolean firstPlayerSeat;
    public final int points;
    public localPlayer(String name, boolean firstPlayerSeat, int points){
        this.name = name;
        this.firstPlayerSeat = firstPlayerSeat;
        this.points = points;
    }
}
