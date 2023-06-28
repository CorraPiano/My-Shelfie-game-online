package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.model.DataCard;
import it.polimi.ingsw.model.GameMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The `ModelView` class represents the model view for the game.
 * It stores the game data required for the CLI and GUI to display the game state.
 */
public class ModelView {
    //deve essere sincronizzato?
    private int numPlayers;
    private int gameID;
    private String localName;
    private String currentPlayer;
    private GameMode gameMode;

    private LocalBoard localBoard;
    private LocalPlayerList localPlayerList;
    private Map<String, LocalBookshelf> localBookshelfMap;
    private LocalHand localHand;
    private ArrayList<LocalCommonCard> localCommonCardList;
    private LocalPersonalCard localPersonalCard;
    private ArrayList<ChatMessage> localChat;

    // CONSTRUCTOR
    public ModelView() {
    }

    /**
     * Initializes the model view with the game ID, game mode, number of players, and local player's name.
     *
     * @param gameID     The game ID.
     * @param gameMode   The game mode.
     * @param numPlayers The number of players.
     * @param localName  The local player's name.
     */
    public void init(int gameID, GameMode gameMode, int numPlayers, String localName) {
        this.gameID = gameID;
        this.gameMode = gameMode;
        this.numPlayers = numPlayers;
        this.localBookshelfMap = new HashMap<>();
        this.localPlayerList = new LocalPlayerList();
        this.localHand = new LocalHand();
        this.localCommonCardList = new ArrayList<>();
        this.currentPlayer = "";
        this.localName = localName;
        this.localChat = new ArrayList<>();
    }

    /**
     * Loads the players and initializes the local bookshelf map.
     */
    public void loadPlayers() {
        for (LocalPlayer p : localPlayerList.playerList) {
            if(localBookshelfMap.get(p.name)==null)
                localBookshelfMap.put(p.name, new LocalBookshelf(p.name));
        }
    }

    // SETTERS

    /**
     * Sets the current player's name.
     *
     * @param name The current player's name.
     */
    public void setCurrentPlayer(String name) {
        currentPlayer = name;
    }

    /**
     * Sets the local board.
     *
     * @param localBoard The local board.
     */
    public void setLocalBoard(LocalBoard localBoard) {
        this.localBoard = localBoard;
    }

    /**
     * Sets the local player list.
     *
     * @param localPlayerList The local player list.
     */
    public void setLocalPlayerList(LocalPlayerList localPlayerList) {
        this.localPlayerList = localPlayerList;
    }

    /**
     * Sets the local bookshelf for a player.
     *
     * @param localBookshelf The local bookshelf.
     */
    public void setLocalBookshelf(LocalBookshelf localBookshelf) {
        this.localBookshelfMap.put(localBookshelf.name, localBookshelf);
    }

    /**
     * Sets the local hand.
     *
     * @param localHand The local hand.
     */
    public void setLocalHand(LocalHand localHand) {
        this.localHand = localHand;
    }

    /**
     * Sets the local common card.
     *
     * @param localCommonCard The local common card.
     */
    public void setLocalCommonCard(LocalCommonCard localCommonCard) {
        if (this.localCommonCardList.size() < 2)
            this.localCommonCardList.add(localCommonCard);
        else if (this.localCommonCardList.get(0).type == localCommonCard.type)
            this.localCommonCardList.set(0, localCommonCard);
        else
            this.localCommonCardList.set(1, localCommonCard);
    }

    /**
     * Sets the local personal card.
     *
     * @param localPersonalCard The local personal card.
     */
    public void setLocalPersonalCard(LocalPersonalCard localPersonalCard) {
        this.localPersonalCard = new LocalPersonalCard(localPersonalCard.num, new DataCard(localPersonalCard.num));
    }

    public void updateChat(ChatMessage message){
        localChat.add(message);
    }

    // GETTERS

    /**
     * Returns the local player's name.
     *
     * @return The local player's name.
     */
    public String getLocalName() {
        return localName;
    }

    /**
     * Returns the common card at the specified index.
     *
     * @param n The index of the common card.
     * @return The common card at the specified index.
     */
    public LocalCommonCard getCommonCard(int n) {
        return localCommonCardList.get(n);
    }

    /**
     * Returns the current player's name.
     *
     * @return The current player's name.
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns the local board.
     *
     * @return The local board.
     */
    public LocalBoard getLocalBoard() {
        return localBoard;
    }

    /**
     * Returns the local bookshelf map.
     *
     * @return The local bookshelf map.
     */
    public Map<String, LocalBookshelf> getLocalBookshelfs() {
        return localBookshelfMap;
    }

    /**
     * Returns the local hand.
     *
     * @return The local hand.
     */
    public LocalHand getLocalHand() {
        return localHand;
    }

    /**
     * Returns the local player list.
     *
     * @return The local player list.
     */
    public ArrayList<LocalPlayer> getLocalPlayerList() {
        return localPlayerList.playerList;
    }

    /**
     * Returns the list of common cards.
     *
     * @return The list of common cards.
     */
    public ArrayList<LocalCommonCard> getCommonCards() {
        return localCommonCardList;
    }

    /**
     * Returns the data card associated with the local personal card.
     *
     * @return The data card.
     */
    public DataCard getDataCard() {
        return localPersonalCard.dataCard;
    }

    /**
     * Returns the game mode.
     *
     * @return The game mode.
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * Returns the number of players.
     *
     * @return The number of players.
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     * Returns the game ID.
     *
     * @return The game ID.
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * Returns the number of players in the local player list.
     *
     * @return The number of players.
     */
    public int getNumOfPlayer() {
        return localPlayerList.playerList.size();
    }

    /**
     * Returns the local personal card.
     *
     * @return The local personal card.
     */
    public LocalPersonalCard getLocalPersonalCard() {
        return localPersonalCard;
    }

    public ArrayList<ChatMessage> getChatMessageList() {
        return localChat;
    }


    public void update (LocalBoard localBoard){
        this.localBoard = localBoard;
    }
    public void update (LocalPlayerList localPlayerList){
        this.localPlayerList = localPlayerList;
    }
    public void update (LocalHand localHand){
        this.localHand = localHand;
    }
    public void update (LocalBookshelf localBookshelf){
        this.localBookshelfMap.put(localBookshelf.name, localBookshelf);
    }


}