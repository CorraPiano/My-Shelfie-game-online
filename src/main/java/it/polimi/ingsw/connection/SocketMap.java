package it.polimi.ingsw.connection;

import it.polimi.ingsw.exception.InvalidIdException;

import java.net.Socket;
import java.util.HashMap;

public class SocketMap {
    private HashMap<Connection,String> mapS;
    private HashMap<String,Connection> mapC;
    public SocketMap(){
        mapS = new HashMap();
        mapC = new HashMap();
    }

    public void bind(String id, Connection connection){
        mapS.put(connection,id);
        mapC.put(id,connection);
    }

    public Connection getConnectionById(String id)  {
        return mapC.get(id);
    }

    public String getIdByConnection(Connection connection) throws InvalidIdException{
        if(!mapS.containsKey(connection))
            throw new InvalidIdException();
        return mapS.get(connection);
    }
}
