package it.polimi.ingsw.connection;

import it.polimi.ingsw.model.GameMode;

import java.util.ArrayList;

public class Message {

    private String head;
    private String[] body;
    public Message(String line) {
        String[] innerStrings=line.split("#");
        head=innerStrings[0];
        body = innerStrings[1].split("\\|");
    }

    public String getHead(){
        return head;
    }

    public String getString(int n) throws Exception {
        if(body.length<=n)
            throw new Exception();
        return body[n];
    }

    public int getInt(int n) throws Exception {
        if(body.length<=n)
            throw new Exception();

        return Integer.parseInt(body[n]);
    }

    public ArrayList<Integer> getBodyAsIntegerList(){
        ArrayList<Integer> list = new ArrayList<>();
        for(String s: body){
            list.add(Integer.parseInt(s));
        }
        return list;
    }

    public GameMode getGameMode(int n) throws Exception {
        if(body.length<=n)
            throw new Exception();

        if(body[n].equalsIgnoreCase("EASY"))
            return GameMode.EASY;
        else
            return GameMode.EXPERT;
    }
}
