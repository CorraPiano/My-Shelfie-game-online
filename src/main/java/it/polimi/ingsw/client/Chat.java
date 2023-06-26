package it.polimi.ingsw.client;

import it.polimi.ingsw.connection.message.ChatMessage;

import java.io.*;
import java.util.ArrayList;

import static it.polimi.ingsw.util.Constants.*;

/**
 * The Chat class handles the chat functionality for the client.
 */
public class Chat implements Runnable{

    private final ArrayList<ChatMessage> chatList;
    private boolean active;
    private Client client;

    /**
     * Constructs a new Chat object.
     *
     * @param client The client associated with the chat.
     */
    public Chat(Client client){
        chatList = new ArrayList<>();
        this.client = client;
    }

    /**
     * Stops the chat thread.
     */
    public synchronized void stopThread(){
        active = false;
        this.notifyAll();
        //si può anche stoppare con client.ClientPhase
    }

    /**
     * Adds a new chat message to the chat list.
     *
     * @param message The chat message to be added.
     */
    public synchronized void addChatMessage(ChatMessage message){
        chatList.add(message);
        this.notifyAll();
    }

    /**
     * Retrieves the chat message at the specified index.
     *
     * @param n The index of the chat message.
     * @return The chat message at the specified index.
     */
    public synchronized ChatMessage getChatMessage(int n){
        synchronized (chatList) {
            return chatList.get(n);
        }
    }

    /**
     * Checks if a chat message is present at the specified index.
     *
     * @param n The index to check.
     * @return True if a chat message is present at the specified index, false otherwise.
     */
    public synchronized boolean isPresent(int n){
        return n < chatList.size();
    }

    /**
     * Prints the introduction message for the chat.
     */
    private void printIntro(){
        System.out.println(BROWN_FOREGROUND + "\n\n───────────────────────────────────────────────── ❮❰ CHAT ❱❯ ─────────────────────────────────────────────────\n" + ANSI_RESET);
        System.out.println(BROWN_FOREGROUND + "Here you can chat with the other players:\n" + ANSI_RESET  +
                    " ➤ " + ANSI_GREEN + "for a public message simply write in the console \n" +
                    " ➤ " + ANSI_GREEN + "for a private message use /send [player name] [message] \n" +
                    " ➤ " + ANSI_GREEN + "for closing the chat type /CLOSE \n" +
                    " ➤ " + ANSI_GREEN + "for the list of command type /HELP \n" + ANSI_RESET);
    }

    /**
     * Runs the chat thread and handles incoming chat messages.
     */
    public void run() {
        int cursor = 0;
        active = true;
        try {
            printIntro();
            while (client.getPhase().equals(ClientPhase.CHAT)) {
                synchronized (this) {
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
