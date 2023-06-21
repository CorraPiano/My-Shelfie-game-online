package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.DataCard;
import it.polimi.ingsw.model.Item;
import javafx.scene.chart.PieChart;

import java.io.Serializable;
import java.util.HashMap;

public class LocalPersonalCard implements Sendable, Serializable {
    public final int num;
    public final DataCard dataCard;

    public LocalPersonalCard( int num){
        this.num=num;
        this.dataCard=null;
    }
    public LocalPersonalCard(int num, DataCard dataCard){
        this.num=num;
        this.dataCard=dataCard;
    }

    public MessageHeader getHeader(){
        return MessageHeader.PERSONALGOALCARD;
    }
}
