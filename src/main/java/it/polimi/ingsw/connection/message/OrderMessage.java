package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

import java.util.ArrayList;

public class OrderMessage implements Sendable{
    // -> cmd: order
    // <- notify: order

    public final ArrayList<Integer> orderlist;
    public final String name;

    public OrderMessage (ArrayList<Integer> list){
        this.orderlist=list;
        this.name=null;
    }
    public OrderMessage (ArrayList<Integer> list, String name){
        this.orderlist=list;
        this.name=name;
    }

    @Override
    public MessageHeader getHeader(){
        return MessageHeader.ORDER;
    }
}
