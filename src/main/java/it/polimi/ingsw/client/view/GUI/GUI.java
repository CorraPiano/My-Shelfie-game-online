package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientGUI;
import it.polimi.ingsw.client.connection.*;
import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.client.view.GUI.controllers.*;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.utils.NotificationsType;
import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.model.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Represents the graphical user interface of the application.
 */
public class GUI extends Application implements View {
    private Stage primaryStage;
    private Stage secondaryStage;
    private SceneName currentSceneName;
    private final int timerSleep_img = 5;
    private final int timerSleep_common = 10;

    private ScheduledExecutorService executorService_images;
    private ScheduledExecutorService executorService_common;

    private Scene currentScene;
    // Connection
    private Sender sender;
    private ClientGUI client;
    private boolean imLast;
    public boolean imDisconnected = false;
    public boolean imRMIClient = false;
    private GUIController controller;
    private SceneHandler sceneHandler;
    private HashMap<SceneName, Consumer<Command>> stageLambda;

    private void changeStage(Boolean fullScreen, Boolean secondStage){
        System.out.println(" ----------> Change");
        if(!secondStage){
            primaryStage.setTitle("My Shelfie");
            currentScene = sceneHandler.getScene(currentSceneName);
            controller = sceneHandler.getController(currentSceneName);
            this.controller.init();
            primaryStage.setScene(currentScene);
            primaryStage.show();
        } else{
            secondaryStage = new Stage();
            secondaryStage.setTitle("My Shelfie");
            currentScene = sceneHandler.getScene(currentSceneName);
            controller = sceneHandler.getController(currentSceneName);
            secondaryStage.setScene(currentScene);
            secondaryStage.show();
        }

    }
    private void setLambdaMap(){
        stageLambda = new HashMap<>();
        stageLambda.put(SceneName.SETUP, (command)-> {
            currentSceneName = SceneName.FINDGAME;
            changeStage(false, false);
        });
        stageLambda.put(SceneName.FINDGAME, (command)-> {
            if(command == Command.START_GAME ){
                currentSceneName = SceneName.GAME;
                changeStage(false, false);
            }
            else if(command == Command.CREATE_GAME || command == Command.JOIN_GAME){
                currentSceneName = SceneName.LOBBY;
                changeStage(false, false);
                timerRoutine();
            }

        });
        stageLambda.put(SceneName.LOBBY, (command)-> {
            if(command == Command.START_GAME){
                currentSceneName = SceneName.GAME;
                changeStage(false, false);
            }
            else if(command == Command.QUIT){
                currentSceneName = SceneName.FINDGAME;
                changeStage(false, false);
            }

        });
        stageLambda.put(SceneName.GAME, (command)-> {
            if(command == Command.END) {
                currentSceneName = SceneName.END;
                changeStage(false, false);
                this.updateEnd();
            }
            else if(command == Command.QUIT){
                currentSceneName = SceneName.FINDGAME;
                changeStage(false, false);
            }
        });
        stageLambda.put(SceneName.END, (command)-> {
            if(command == Command.START_GAME){
                currentSceneName = SceneName.FINDGAME;
                changeStage(false, false);
            }
            else {
                //esce definitivamente dal gioco
            }
            changeStage(false, false);
        });
    }

    private void setupConnection() {
        try {
            this.client = new ClientGUI();
            this.client.setGui(this);
        } catch (RemoteException e) {
            System.out.println("connection problem");
            throw new RuntimeException(e);
        }
    }

    /**
     * Starts the GUI application.
     *
     * @param stage The primary stage of the application.
     */
    @Override
    public void start(Stage stage) {
        setupConnection();
        this.primaryStage = stage;
        sceneHandler = new SceneHandler(this);
        setLambdaMap();
        currentSceneName = SceneName.SETUP;
        controller = sceneHandler.getController(currentSceneName);
        Scene scene = sceneHandler.getScene(currentSceneName);

        this.primaryStage.setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            int exitStatus = AlertBox.exitRequest(primaryStage, windowEvent, "Are you sure you want to exit?");
            if(exitStatus == 1) {
               // if(currentSceneName== SceneName.LOBBY || currentSceneName ==SceneName.GAME || currentSceneName ==SceneName.CHAT || currentSceneName ==SceneName.BOOKSHELFS)
                //    this.leaveGame();
                System.exit(0);
            }
        });
        //stage.setFullScreen(true);
        stage.setTitle("My Shelfie");
        stage.setScene(scene);
        stage.show();
    }
    private void testMain(){
        // Change scene test
    }

    /**
     * Retrieves the current scene name.
     *
     * This method returns the name of the current scene in the application. The scene name represents the
     * currently active scene or screen displayed to the user.
     *
     * @return The current scene name.
     */
    public SceneName getCurrentScene() {
        return currentSceneName;
    }

    /**
     * The main entry point of the JAVAFX application.
     *
     * This method is called when the application is launched. It initializes and starts the application.
     * It is responsible for setting up the initial configuration and launching the application's user interface.
     *
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Switches the current stage based on the given command.
     *
     * @param command The command that determines the stage to switch to.
     */
    public void switchStage(Command command){
        if (executorService_images != null) {
            executorService_images.shutdown();
        }
        if (executorService_common != null) {
            executorService_common.shutdown();
        }
        Platform.runLater(() -> {
            stageLambda.get(currentSceneName).accept(command);
        });
    }

    /**
     * Sets the sender for the GUI.
     *
     * @param sender The sender object used for communication.
     */
    @Override
    public void setSender(Sender sender) {
        this.sender = sender;
    }

    /**
     * Returns the sender object associated with the GUI.
     *
     * @return The sender object.
     */
    @Override
    public Sender getSender() {
        return null;
    }

    @Override
    public void setClient() {

    }

    /**
     * Returns the current scene of the GUI.
     *
     * @return The current scene.
     */
    public Scene getScene() {
        return sceneHandler.getScene(currentSceneName);
    }

    /**
     * Returns the name of the current scene.
     *
     * @return The name of the current scene.
     */
    public SceneName getCurrentSceneName() {
        return currentSceneName;
    }

    /**
     * Returns the client object associated with the GUI.
     *
     * @return The client object.
     */
    @Override
    public Client getClient() {
        return this.client;
    }
    public boolean isLast(){
        System.out.println(imLast);
        return imLast;
    }

    /**
     * Returns the primary stage of the GUI.
     *
     * @return The primary stage.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Returns the scene handler object used for managing scenes.
     *
     * @return The scene handler object.
     */
    public SceneHandler getSceneHandler() {
        return sceneHandler;
    }

    public void notifyDisconnection(String name) {
        if(currentSceneName==SceneName.LOBBY){
            Platform.runLater(()->{
                LobbyController controllerTmp = (LobbyController) controller;
                if(Objects.equals(name, client.getName())){
                    controllerTmp.newNotification("You have connection problems");
                }
                else {
                    controllerTmp.newNotification(name + " has connection problems");
                }
            });
        }
    }

    public void notifyReconnection() {
        System.out.println("--> notifyReconnection");
        imDisconnected = false;
    }
    public void notifyDisconnectionRMI() {
        imDisconnected = true;
    }


    // link: http://patorjk.com/software/taag/#p=testall&f=Calvin%20S&t=SET-UP%20
    // font: Big

    /* ***************************************************
               _____ ______ _______     _    _ _____
              / ____|  ____|__   __|   | |  | |  __ \
             | (___ | |__     | |______| |  | | |__) |
              \___ \|  __|    | |______| |  | |  ___/
              ____) | |____   | |      | |__| | |
             |_____/|______|  |_|       \____/|_|
     ******************************************************/

    /**
     * Sets up an RMI connection with the specified IP address.
     *
     * @param ip The IP address for the RMI connection.
     * @throws Exception if an error occurs during the RMI connection setup.
     */
    public void setRMIConnection(String ip) throws Exception {
//        Registry registry = LocateRegistry.getRegistry();
//        ControllerSkeleton controller = (ControllerSkeleton) registry.lookup(Settings.remoteObjectName);
//        this.setSender(new RMISender(controller, this.getClient()));
//        this.switchStage(Command.SET_CONNECTION);
        this.setSender(new RMISender(ip, client));
        this.switchStage(Command.SET_CONNECTION);
    }

    /**
     * Sets up a TCP connection with the specified IP address.
     *
     * @param ip The IP address for the TCP connection.
     * @throws Exception if an error occurs during the TCP connection setup.
     */
    public void setTCPConnection(String ip) throws Exception {
//        TCPReceiver tcpreceiver = new TCPReceiver(client);
//        ClientConnection clientConnection = new ClientConnection(new Socket("localhost", 8081), tcpreceiver);
//        new Thread(clientConnection).start();
        this.setSender(new TCPSender(ip, client));
        this.switchStage(Command.SET_CONNECTION);
    }

    /* ***************************************************
         _____ ___ _   _ ____     ____    _    __  __ _____
        |  ___|_ _| \ | |  _ \   / ___|  / \  |  \/  | ____|
        | |_   | ||  \| | | | | | |  _  / _ \ | |\/| |  _|
        |  _|  | || |\  | |_| | | |_| |/ ___ \| |  | | |___
        |_|   |___|_| \_|____/   \____/_/   \_\_|  |_|_____|
     *******************************************************/

    /**
     * Retrieves the list of games from the server.
     * This method uses the sender to request the game list from the server.
     */
    public void getGamesList() {
        this.sender.getGameList();
    }

    /**
     * Refreshes the game list in the FindGameController with the provided list of games.
     * This method is used to update the UI with the latest game list.
     *
     * @param games The list of games to be displayed in the UI.
     */
    public void refreshGameList(List<LocalGame> games){
        Platform.runLater(()->{
                FindGameController controllerTmp = (FindGameController) controller;
                controllerTmp.updateList(games);
        });
    }

    /**
     * Adds the first player to create a new game with the given name, game mode, and number of players.
     * This method uses the sender to send the necessary information to the server.
     * After adding the first player, it switches the stage to the CREATE_GAME scene.
     *
     * @param name      The name of the player.
     * @param gameMode  The game mode chosen for the new game.
     * @param numPlayer The number of players for the new game.
     */
    public void addFirstPlayer(String name, GameMode gameMode, int numPlayer){
        sender.addFirstPlayer(name, gameMode, numPlayer);
        //this.switchStage(Command.CREATE_GAME);
    }

    /**
     * Joins a game with the given name and game ID.
     * This method uses the sender to send the join request to the server.
     * If switchScene is true, it switches the stage to the JOIN_GAME scene.
     *
     * @param name        The name of the player.
     * @param gameId      The ID of the game to join.
     * @param switchScene Indicates whether to switch the stage to the JOIN_GAME scene after joining.
     */
    public void joinGame(String name,int gameId, boolean switchScene){
        this.imLast = switchScene;
        sender.addPlayer(name, gameId);
        //if (switchScene) this.switchStage(Command.JOIN_GAME);
    }

    public void joinLobby(){
        if( currentSceneName != SceneName.LOBBY)
            this.switchStage(Command.JOIN_GAME);
    }

    public void reconnect(String ID){
        sender.reconnectGame(ID, true);
    }

    /* *******************************************************
              _      ____  ____  ______     __
             | |    / __ \|  _ \|  _ \ \   / /
             | |   | |  | | |_) | |_) \ \_/ /
             | |   | |  | |  _ <|  _ < \   /
             | |___| |__| | |_) | |_) | | |
             |______\____/|____/|____/  |_|
     ***************************************************** */

    /**
     * Initializes the lobby by calling the init() method of the LobbyController.
     * This method is used to set up the lobby scene and start the lobby functionality.
     * It also invokes the timerRoutine() method to initiate periodic tasks.
     */
    public void initLobby(){
        LobbyController tmp = (LobbyController) this.controller;
        tmp.init();
        timerRoutine();
    }

    /**
     * Updates the player list in the lobby with the provided list of players.
     * This method is used to display the updated player list in the lobby UI.
     * It also shows a notification message indicating if a player joined or left the game.
     *
     * @param list        The list of players to be displayed in the UI.
     * @param command     The command indicating the action (JOIN or QUIT) performed by the player.
     * @param playerName  The name of the player who joined or left the game.
     */
    public void updatePlayerList(ArrayList<LocalPlayer> list,Command command, String playerName){
        if (currentSceneName != SceneName.LOBBY) return;
        List<String> players = new ArrayList<>();
        for (LocalPlayer p : list) {
            players.add(p.name);
        }
        String message = (command == Command.QUIT) ?
                "Player " + playerName + " left the game" : "Player " + playerName + " has joined the game";
        Platform.runLater(()->{
            LobbyController tmp = (LobbyController) this.controller;
            tmp.newNotification(message);
            tmp.updatePlayerList(players);
        });
    }
    public ArrayList<String> getPlayers(){
        ArrayList<String> players = new ArrayList<>();
        for (LocalPlayer p : client.getModelView().getLocalPlayerList()) {
            players.add(p.name);
        }
        return players;
    }
    public void timerRoutine(){
        executorService_images = Executors.newSingleThreadScheduledExecutor();
        executorService_images.scheduleAtFixedRate(() ->{
            if(controller instanceof LobbyController){
                Platform.runLater(()->{
                    if(controller instanceof LobbyController) {
                        LobbyController tmp = (LobbyController) controller;
                        tmp.changeImage();
                        tmp.changeCommon();
                    }
                });
            }
        }, 0, timerSleep_common, TimeUnit.SECONDS);

    }

    /**
     * Quits the game by leaving the current game and switching the stage to the QUIT scene.
     * This method uses the sender to notify the server that the player is leaving the game.
     * It then switches the stage to the QUIT scene using the switchStage() method.
     */
    public  void quitGame(){
        sender.leaveGame();
        switchStage(Command.QUIT);
    }

    /* *****************************************************
      ____    _    __  __ _____
     / ___|  / \  |  \/  | ____|
    | |  _  / _ \ | |\/| |  _|
    | |_| |/ ___ \| |  | | |___
     \____/_/   \_\_|  |_|_____|
     ******************************************************/

    /**
     * Initializes the game by calling the init() method of the GameController associated with the current scene.
     * This method is used to set up the game scene and start the game functionality.
     */
    public void initGame(){
        GameController c = (GameController) sceneHandler.getController(currentSceneName);
        c.init();
    }
    // Server to client

    /**
     * Updates the game board in the UI by invoking the showBoard() method of the GameController.
     * This method is used to reflect changes in the game board on the client side.
     */
    public void updateBoard(){
        if(this.currentSceneName == SceneName.GAME) {
            Platform.runLater(() -> {
                GameController controllertmp = (GameController) this.controller;
                controllertmp.showBoard();
            });
        }
    }

    /**
     * Updates the hand of the current player in the UI by invoking the showHand() method of the GameController.
     * This method is used to display the updated hand of the current player.
     */
    public void updateHand() {
        if(this.currentSceneName == SceneName.GAME) {
            Platform.runLater(() -> {
                GameController controllertmp = (GameController) this.controller;
                controllertmp.showHand();
            });
        }
    }

    /**
     * Updates the bookshelf display in the UI by invoking the showBookshelf() method of the GameController.
     * This method is used to update the bookshelf representation on the client side.
     */
    public void updateBookShelf(LocalBookshelf bookshelf){
        Platform.runLater(() -> {
            if(this.currentSceneName == SceneName.GAME) {
                GameController controllertmp = (GameController) this.controller;
                controllertmp.showbookshelf(bookshelf);
            }
        });
    }

    /**
     * Updates the global notifications in the UI by invoking the showGlobalNotification() method of the GameController.
     * This method is used to display various game-related notifications to all players.
     *
     * @param notificationsType  The type of notification to be displayed.
     * @param name               The name of the player associated with the notification.
     * @param coordinates        The coordinates related to the notification.
     * @param list               The list of integers associated with the notification.
     * @param column             The column number associated with the notification.
     */
    public void updateGlobalNotifications(NotificationsType notificationsType, String name, Coordinates coordinates, ArrayList<Integer> list, int column){
        Platform.runLater(()->{
            if(this.currentSceneName == SceneName.GAME) {
                GameController controllertmp = (GameController) this.controller;
                controllertmp.showGlobalNotification(notificationsType, name, coordinates, list, column);
            }
        });
    }

    public void updateExceptionNotification(String e) {
        Platform.runLater(()->{
            if(this.currentSceneName == SceneName.GAME) {
                GameController controllertmp = (GameController) this.controller;
                controllertmp.showExceptionNotification(e);
            }
        });
    }

    /**
     * Updates the table view in the UI by invoking the showTableView() method of the GameController.
     * This method is used to update the display of the table view in the game.
     */
    public void updateTableView() {
        Platform.runLater(()->{
            if(this.currentSceneName == SceneName.GAME) {
                GameController controllertmp = (GameController) this.controller;
                controllertmp.showTableView();
            }
        });
    }

    /**
     * Updates the tokens in the UI by invoking the showTokens() method of the GameController.
     * This method is used to update the display of tokens in the game.
     */
    public void updateTokens() {
        Platform.runLater(()->{
            if(this.currentSceneName == SceneName.GAME) {
                GameController controllertmp = (GameController) this.controller;
                controllertmp.showTokens();
            }
        });
    }

    /**
     * Updates the end game token in the UI by invoking the showEndGameToken() method of the GameController.
     * This method is used to display the end game token in the game.
     */
    public void updateEndGameToken() {
        Platform.runLater(()->{
            if(this.currentSceneName == SceneName.GAME) {
                GameController controllertmp = (GameController) this.controller;
                controllertmp.showEndGameToken();
            }
        });
    }

    /* CLIENT TO SERVER METHODS */

    /**
     * Sends a request to pick an item at the specified coordinates to the server by invoking the pickItem() method of the sender.
     * This method is used when a player wants to pick an item from the game board.
     *
     * @param coordinates  The coordinates of the item to pick.
     */
    public void pickItem(Coordinates coordinates){
        sender.pickItem(coordinates);
        System.out.println("pick");
    }

    /**
     * Sends a request to undo the pick of an item.
     * This method is used when a player wants to undo the pick of an item from the game board.
     */
    public void undoPick() {
        sender.undoPick();
        System.out.println("undo pick");
    }

    /**
     * Sends a request to put an item list in the specified column to the server by invoking the putItemList() method of the sender.
     * This method is used when a player wants to put an item list in the table view column.
     *
     * @param column  The column number where the item list will be put.
     */
    public void putItemList(int column){
        sender.putItemList(column);
    }

    /**
     * Sends a request to select the insert order of the hand to the server by invoking the selectInsertOrder() method of the sender.
     * This method is used when a player wants to select the order in which the item list will be inserted.
     *
     * @param positions  The list of positions indicating the insert order.
     */
    public void selectInsertOrder(ArrayList<Integer> positions) {
        sender.selectInsertOrder(positions);
    }

    /**
     * Sets the turn of the current player in the UI by invoking the setTurn() method of the GameController.
     * This method is used to indicate which player's turn it currently is.
     *
     * @param name  The name of the player whose turn it is.
     */
    public void setTurn(String name) {
        Platform.runLater(() -> {
            GameController controllertmp = (GameController) this.controller;
            controllertmp.setTurn(name);
        });

    }

    /**
     * Sends a request to leave the game to the server by invoking the leaveGame() method of the sender.
     * This method is used when a player wants to leave the game.
     */
    public void leaveGame(){
        sender.leaveGame();
        switchStage(Command.QUIT);
    }
    /* ********************************************************
      ____   ____   ____  _  __ _____ _    _ ______ _      ______ _____
     |  _ \ / __ \ / __ \| |/ // ____| |  | |  ____| |    |  ____/ ____|
     | |_) | |  | | |  | | ' /| (___ | |__| | |__  | |    | |__ | (___
     |  _ <| |  | | |  | |  <  \___ \|  __  |  __| | |    |  __| \___ \
     | |_) | |__| | |__| | . \ ____) | |  | | |____| |____| |    ____) |
     |____/ \____/ \____/|_|\_\_____/|_|  |_|______|______|_|   |_____/
    ***********************************************************/

    /**
     * Updates the bookshelves of the players in the UI by invoking the showPlayersBookshelfs() method of the GameController.
     * This method is used to display the bookshelves of all players in the game.
     *
     */
    public void updateAllBookshelfs() {
        Platform.runLater(()-> {
            if (this.currentSceneName == SceneName.GAME){
                GameController controllertmp = (GameController) this.controller;
                controllertmp.showAllBookshelf();
            }
        });
    }

    /* **********************************************************
               _____ _    _       _______
              / ____| |  | |   /\|__   __|
             | |    | |__| |  /  \  | |
             | |    |  __  | / /\ \ | |
             | |____| |  | |/ ____ \| |
              \_____|_|  |_/_/    \_\_|
     ***************************************************************/
    public void openChat(){}

    public void closeChat(){}

    /*
    public void updateChat(ChatMessage chatMessage, String name){
        ChatController chat = (ChatController) this.controller;
        Platform.runLater(() ->{
            chat.displayMessage(chatMessage, name);
        });
    }
    */

    /**
     * Updates the chat window with a new chat message by invoking the displayMessage() method of the GameController.
     * This method is used to display a new chat message in the chat window.
     *
     * @param chatMessage  The chat message to be displayed.
     * @param name         The name of the sender of the chat message.
     */
    public void updateChat(ChatMessage chatMessage, String name){
        Platform.runLater(()->{
            if(this.currentSceneName == SceneName.GAME) {
                GameController controllertmp = (GameController) this.controller;
                controllertmp.displayMessage(chatMessage, name);
            }
        });
    }

    /**
     * Sends a chat message to the server by invoking the addChatMessage() method of the sender.
     * This method is used to send a chat message to other players in the game.
     *
     * @param message   The chat message to be sent.
     * @param receiver  The name of the receiver of the chat message. If null, the message will be sent to all players.
     */
    public void sendMessage(String message, String receiver){
        if(receiver == null){
            sender.addChatMessage(message);
        } else {
            sender.addChatMessage(message, receiver);
        }
    }

    /* *********************
     ______ _   _ _____
     |  ____| \ | |  __ \
     | |__  |  \| | |  | |
     |  __| | . ` | |  | |
     | |____| |\  | |__| |
     |______|_| \_|_____/
     **********************/

    /**
     * Updates the end game label in the UI by invoking the updateLabel() method of the EndController.
     * This method is used to update the label displayed in the end game screen.
     */
    public void updateEnd(){
        Platform.runLater(()->{
            EndController end = (EndController) this.controller;
            end.updateLabel();
        });
    }

    /* *******************************************************************
       _____ _______    _______ _____  _____ _______ _____ _____  _____
      / ____|__   __|/\|__   __|_   _|/ ____|__   __|_   _/ ____|/ ____|
     | (___    | |  /  \  | |    | | | (___    | |    | || |    | (___
      \___ \   | | / /\ \ | |    | |  \___ \   | |    | || |     \___ \
      ____) |  | |/ ____ \| |   _| |_ ____) |  | |   _| || |____ ____) |
     |_____/   |_/_/    \_\_|  |_____|_____/   |_|  |_____\_____|_____/
     ********************************************************************/

    /**
     * Updates the statistics in the UI by invoking the updateBookshelfs() method of the StatisticsController.
     * This method is used to update the statistics related to bookshelves and players in the game.
     *
     * @param name                 The name of the player associated with the statistics.
     * @param localBookshelfMap    The map of local bookshelves associated with each player.
     * @param localPlayers         The list of local players in the game.
     */
    public void updateStatistics(String name, Map<String, LocalBookshelf> localBookshelfMap, ArrayList<LocalPlayer> localPlayers){
        StatisticsController statistics = (StatisticsController) this.controller;
        statistics.updateBookshelfs(name, localBookshelfMap, localPlayers);
    }

    /* ************************************************************************* */

    public void disconnectionHandler(String name) {
        switch(currentSceneName){
            case GAME -> {
                updateGlobalNotifications(NotificationsType.DISCONNECT, name, new Coordinates(), new ArrayList<>(), 0);
            }
        }
    }

    public void reconnectHandler(String name) {
        switch(currentSceneName){
            case GAME -> {
                updateGlobalNotifications(NotificationsType.RECONNECT, name, new Coordinates(), new ArrayList<>(), 0);
            }
        }
    }

    public void leaveHandler(String name) {
        switch(currentSceneName){
            case GAME -> {
                updateGlobalNotifications(NotificationsType.INACTIVE, name, new Coordinates(), new ArrayList<>(), 0);
            }
        }
    }
}
