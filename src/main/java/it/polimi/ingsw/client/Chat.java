package it.polimi.ingsw.client;
import it.polimi.ingsw.client.view.TUI.OutputHandler;
import it.polimi.ingsw.connection.message.ChatMessage;

import java.io.*;
import java.util.ArrayList;

import static it.polimi.ingsw.util.Constants.*;

/**
 * The Chat class handles the chat functionality for the client.
 */
public class Chat implements Runnable {

    private final ArrayList<ChatMessage> chatList;
    private boolean active;
    private Client client;
    OutputHandler outputHandler=null;

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
     * set the OutputHandler, required from printing the TUI.
     */
    public void bindOutputHandler(OutputHandler outputHandler){
        this.outputHandler = outputHandler;
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

/// da cancellare

    /**
     * Runs the chat thread and handles incoming chat messages.
     */
    public void run() {
        int cursor = 0;
        active = true;
        try {
            outputHandler.showChat();
            while (client.getPhase().equals(ClientPhase.CHAT)) {
                synchronized (this) {
                    if (isPresent(cursor)) {
                        ChatMessage message = getChatMessage(cursor);
                        if (message.all)
                            outputHandler.showPublicChatMessage(message.sender, message.message);
                        else
                            outputHandler.showPrivateChatMessage(message.sender, message.receiver, message.message);
                        /*if(message.sender.equals(client.getName())) {
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
                        }*/
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
