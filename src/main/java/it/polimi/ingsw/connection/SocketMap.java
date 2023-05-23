package it.polimi.ingsw.connection;

import it.polimi.ingsw.exception.InvalidIdException;

import java.net.Socket;
import java.util.HashMap;

public class SocketMap {
    private final HashMap<Connection,String> mapS;

    public SocketMap(){
        mapS = new HashMap<>();
    }

    public void bind(String id, Connection connection){
        mapS.put(connection,id);
    }

    public String getIdByConnection(Connection connection) throws InvalidIdException{
        if(!mapS.containsKey(connection))
            throw new InvalidIdException();
        return mapS.get(connection);
    }
}
