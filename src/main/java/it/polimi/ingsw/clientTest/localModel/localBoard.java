package it.polimi.ingsw.clientTest.localModel;

import it.polimi.ingsw.model.Item;

import java.io.Serializable;

public class localBoard implements Serializable {
    public final Item[][] board;
    public final int Columns = 9;
    public final int Rows = 9;

    public localBoard(Item[][] board){
        this.board=board;
    }

}
