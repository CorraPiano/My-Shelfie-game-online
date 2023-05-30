package it.polimi.ingsw.client;

import it.polimi.ingsw.connection.message.ChatMessage;

import java.io.*;
import java.util.ArrayList;

import static it.polimi.ingsw.util.Constants.*;

public class Chat implements Runnable{

    private final ArrayList<ChatMessage> chatList;
    private boolean active;
    private Client client;

    public Chat(Client client){
        chatList = new ArrayList<>();
        this.client = client;
    }

    public synchronized void stopThread(){
        active = false;
        this.notifyAll();
        //si può anche stoppare con client.ClientPhase
    }
    public synchronized void addChatMessage(ChatMessage message){
        chatList.add(message);
        this.notifyAll();
    }

    public synchronized ChatMessage getChatMessage(int n){
        synchronized (chatList) {
            return chatList.get(n);
        }
    }

    public synchronized boolean isPresent(int n){
        return n < chatList.size();
    }

    public void run() {
        int cursor = 0;
        active = true;
        try {
            //Process p = Runtime.getRuntime().exec("cmd /c start");
            System.out.println(BROWN_FOREGROUND + "\n\n───────────────────────────────────────────────────────── ❮❰ CHAT ❱❯ ─────────────────────────────────────────────────────────\n" + ANSI_RESET);
            synchronized (this) {
                while (active) {
                    if (isPresent(cursor)) {
                        ChatMessage message = getChatMessage(cursor);
                        if(message.sender.equals(client.getName())) {
                            //System.out.printf("\t\t\t\t\t");
                            if (message.all) {
                                System.out.println(ANSI_YELLOW + "❮TO ALL❯ " + ANSI_CYAN + "you" + ANSI_RESET + ": " + ANSI_GREEN + message.message + ANSI_RESET);
                            } else {
                                System.out.println(ANSI_YELLOW + "❮TO "+ message.receiver +"❯ " +  ANSI_RESET + ": " + ANSI_GREEN + message.message + ANSI_RESET);
                            }
                        }
                        else {
                            if (message.all) {
                                System.out.println(ANSI_YELLOW + "❮TO ALL❯ " + ANSI_CYAN + message.sender + ANSI_RESET + ": " + ANSI_GREEN + message.message + ANSI_RESET);
                            } else {
                                String receiver = message.receiver;
                                System.out.println(ANSI_YELLOW + "❮FROM "+  message.sender +"❯ " + ANSI_RESET + ": " + ANSI_GREEN + message.message + ANSI_RESET);
                            }
                        }
                        cursor++;
                    } else {
                        this.wait();
                        // fixare wait e synchronized
                    }
                }

            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
