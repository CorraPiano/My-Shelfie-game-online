package it.polimi.ingsw.connection;

import com.google.gson.Gson;

import it.polimi.ingsw.client.localModel.LocalPersonalCard;
import it.polimi.ingsw.connection.message.*;

public class SenderTCP {

    private final SocketMap socketMap;
    private final Gson gson;
    public SenderTCP(SocketMap socketMap) {
        this.socketMap=socketMap;
        gson = new Gson();
    }

    //per messaggi semplici
    public void send(MessageHeader header, String body, String id){
        TCPMessage TCPmessage = new TCPMessage(header,body);
        Connection conn = socketMap.getConnectionById(id);
        conn.send(TCPmessage);
    }

    // per oggetti sendable che devono essere convertiti in json
    public void send(Sendable sendable, String id){
        String json =  gson.toJson(sendable);
        TCPMessage TCPmessage = new TCPMessage(sendable.getHeader(),json);
        System.out.println(sendable.getHeader()+": "+json);
        Connection conn = socketMap.getConnectionById(id);
        conn.send(TCPmessage);
    }

    public void sendToConnection(Sendable sendable, Connection conn){
        String json =  gson.toJson(sendable);
        TCPMessage TCPmessage = new TCPMessage(sendable.getHeader(),json);
        conn.send(TCPmessage);
    }

}

