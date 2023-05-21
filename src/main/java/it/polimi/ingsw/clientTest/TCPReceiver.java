package it.polimi.ingsw.clientTest;

import com.google.gson.Gson;
import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.connection.TCPMessage;
import it.polimi.ingsw.connection.message.*;
import it.polimi.ingsw.model.DataCard;


import java.rmi.RemoteException;
import java.util.ArrayList;

public class TCPReceiver {
    private Gson gson;
    private Client client;
    public TCPReceiver(Client client) {
        this.gson = new Gson();
        this.client = client;
    }
    //gestire il remote Exception
    public void receive(TCPMessage TCPmessage) throws Exception {
        System.out.println(TCPmessage.getBody());
        switch (TCPmessage.getHeader()) {
            case OK-> {
                //
            }
            case ID-> {
                client.getID(TCPmessage.getBody());
            }
            case EXCEPTION-> {
                client.getException(TCPmessage.getBody());
            }
            //vedere se va bene lasciarle come stringhe, o se conviene fare i messaggi
            case PLAYERJOIN -> {
                PlayerJoinEvent event = gson.fromJson(TCPmessage.getBody(),PlayerJoinEvent.class);
                client.playerJoin(event.name);
            }
            case PLAYERLEAVE -> {
                PlayerLeaveEvent event = gson.fromJson(TCPmessage.getBody(),PlayerLeaveEvent.class);
                client.playerLeave(event.name);
            }
            case STARTGAME -> {
                StartGameEvent event = gson.fromJson(TCPmessage.getBody(),StartGameEvent.class);
                client.startGame(event.name);
            }
            case NEWTURN -> {
                NewTurnEvent event = gson.fromJson(TCPmessage.getBody(),NewTurnEvent.class);
                client.newTurn(event.name);
            }
            case LASTROUND -> {
                LastRoundEvent event = gson.fromJson(TCPmessage.getBody(),LastRoundEvent.class);
                client.lastRound(event.name);
            }
            case ENDGAME-> {
                EndGameEvent event = gson.fromJson(TCPmessage.getBody(),EndGameEvent.class);
                client.endGame(event.name);
            }

            case GAMESLIST -> {
                GamesList gamesList = gson.fromJson(TCPmessage.getBody(),GamesList.class);
                ArrayList<LocalGame> list = gamesList.gamesList;
                client.getGamesList(list);
            }
            case PICK -> {
                PickMessage message = gson.fromJson(TCPmessage.getBody(),PickMessage.class);
                client.notifyPick(message.name,message.coordinates,message.item);
            }
            case UNDO -> {
                //undo può esserre inviato come stringa
                UndoMessage message = gson.fromJson(TCPmessage.getBody(),UndoMessage.class);
                client.notifyUndo(message.name);
            }
            case ORDER -> {
                OrderMessage message = gson.fromJson(TCPmessage.getBody(),OrderMessage.class);
                client.notifyOrder(message.name,message.orderlist);
            }
            case PUT -> {
                PutMessage message = gson.fromJson(TCPmessage.getBody(),PutMessage.class);
                client.notifyPut(message.name,message.column);
            }

            case BOARD -> {
                LocalBoard board = gson.fromJson(TCPmessage.getBody(),LocalBoard.class);
                client.updateBoard(board);
            }
            case BOOKSHELF -> {
                LocalBookshelf bookshelf = gson.fromJson(TCPmessage.getBody(),LocalBookshelf.class);
                client.updateBookshelf(bookshelf);
            }
            case HAND-> {
                LocalHand hand = gson.fromJson(TCPmessage.getBody(),LocalHand.class);
                client.updateHand(hand);
            }
            case GAME -> {
                LocalGame localGame= gson.fromJson(TCPmessage.getBody(),LocalGame.class);
                client.updateGame(localGame);
            }
            case COMMONGOALCARD -> {
                LocalCommonCard commonGoalCard= gson.fromJson(TCPmessage.getBody(),LocalCommonCard.class);
                client.updateCommonGoalCard(commonGoalCard);
            }
            case PERSONALGOALCARD -> {
                try {
                    LocalPersonalCard personalGoalCard = gson.fromJson(TCPmessage.getBody(), LocalPersonalCard.class);
                    //sistemare in modo da passare direttamente una localpersonalcard
                    client.updatePersonalGoalCard(personalGoalCard);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            default -> {
                throw new Exception();
            }
        }
    }
}
