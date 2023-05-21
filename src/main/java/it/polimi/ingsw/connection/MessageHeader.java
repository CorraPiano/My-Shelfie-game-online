package it.polimi.ingsw.connection;

public enum MessageHeader {
    //up
    LIST,
    CREATE,
    JOIN,
    LEAVE,

    //up and down
    PICK,
    ORDER,
    UNDO,
    PUT,

    //down
    EXCEPTION,
    GAMESLIST,
    OK,
    ID,
    PLAYERJOIN,
    PLAYERLEAVE,
    STARTGAME,
    NEWTURN,
    LASTROUND,
    ENDGAME,
    BOARD,
    BOOKSHELF,
    COMMONGOALCARD,
    PERSONALGOALCARD,
    GAME,
    HAND,
    PLAYER;


}
