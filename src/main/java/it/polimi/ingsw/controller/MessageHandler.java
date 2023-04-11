package it.polimi.ingsw.controller;

import it.polimi.ingsw.connection.Connection;
import it.polimi.ingsw.connection.Message;

import java.util.HashMap;

public class MessageHandler {

    private HashMap<Integer,String> map;
    private Controller controller;
    public MessageHandler(Controller controller){
        this.controller=controller;
        map=new HashMap<>();
    }

    public boolean receive(Message message, int connNum){

        if (message.getHead().equalsIgnoreCase("CHAT")){
            try{
                System.out.println("--> SEND : " + message.getString(0));
                controller.addChatMessage(message.getString(0), map.get(connNum));
                return true;
            } catch(Exception e){
                return false;
            }
        }

        else if(message.getHead().equalsIgnoreCase("PICK")){
            try{
                System.out.println("--> SEND : " + message.getInt(0) + ", " + message.getInt(1));
                controller.pickItem(message.getInt(0),message.getInt(1), map.get(connNum));
                return true;
            } catch(Exception e){
                return false;
            }
        }

        else if(message.getHead().equalsIgnoreCase("UNDO")){
            try{
                System.out.println("--> UNDO");
                controller.undoPick(map.get(connNum));
                return true;
            } catch(Exception e){
                return false;
            }
        }

        else if(message.getHead().equalsIgnoreCase("ORDER")){
            try{
                System.out.println("--> ORDER : "+ message.getBodyAsIntegerList());
                controller.selectInsertOrder(message.getBodyAsIntegerList(), map.get(connNum));
                return true;
            } catch(Exception e){
                return false;
            }
        }

        else if(message.getHead().equalsIgnoreCase("PUT")){
            try{
                System.out.println("--> PUT : "+ message.getInt(0));
                controller.putItemList(message.getInt(0), map.get(connNum));
                return true;
            } catch(Exception e){
                return false;
            }
        }

        else if(message.getHead().equalsIgnoreCase("JOIN")){
            try{
                System.out.println("--> JOIN : "+ message.getString(0));
                String id=controller.addPlayer(message.getString(0));
                map.put(connNum,id);
                return true;
            } catch(Exception e){
                return false;
            }
        }

        else if(message.getHead().equalsIgnoreCase("CREATE")){
            try{
                System.out.println("--> CREATE : "+ message.getString(0) +", "+ message.getGameMode(1) +", "+message.getInt(2));
                String id=controller.addFirstPlayer(message.getString(0),message.getGameMode(1),message.getInt(2));
                map.put(connNum,id);
                return true;
            } catch(Exception e){
                return false;
            }
        }
        else{
            System.out.println("--> errore: "+message.getHead());
            return false;
        }

    }
}
