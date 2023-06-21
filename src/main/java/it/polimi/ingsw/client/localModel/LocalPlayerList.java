package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;

import java.io.Serializable;
import java.util.ArrayList;

public class LocalPlayerList implements Sendable, Serializable {
    public final ArrayList<LocalPlayer> playerList;

    public LocalPlayerList(ArrayList<LocalPlayer> playerList){
        this.playerList=playerList;
    }

    @Override
    public MessageHeader getHeader() {
        return MessageHeader.PLAYERLIST;
    }
}
