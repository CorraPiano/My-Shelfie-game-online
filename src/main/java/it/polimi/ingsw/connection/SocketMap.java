package it.polimi.ingsw.connection;

import it.polimi.ingsw.exception.InvalidIdException;

import java.net.Socket;
import java.util.HashMap;

/**
 * This class represents a mapping between connections and their corresponding identifiers.
 */
public class SocketMap {

    private final HashMap<Connection,String> mapS;

    /**
     * Constructs a new SocketMap object.
     */
    public SocketMap(){
        mapS = new HashMap<>();
    }

    /**
     * Binds a connection with its corresponding identifier.
     *
     * @param id         The identifier to bind.
     * @param connection The connection to bind the identifier to.
     */
    public void bind(String id, Connection connection){
        mapS.put(connection,id);
    }

    /**
     * Retrieves the identifier associated with the specified connection.
     *
     * @param connection The connection for which to retrieve the identifier.
     * @return The identifier associated with the connection.
     * @throws InvalidIdException If the specified connection does not have an associated identifier.
     */
    public String getIdByConnection(Connection connection) throws InvalidIdException{
        if(!mapS.containsKey(connection))
            throw new InvalidIdException();
        return mapS.get(connection);
    }
}
