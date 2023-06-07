package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientGUI;
import it.polimi.ingsw.client.connection.*;
import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.client.view.GUI.controllers.*;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.controller.ControllerSkeleton;
import it.polimi.ingsw.controller.Settings;
import it.polimi.ingsw.model.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GUI extends Application implements View {
    private Stage primaryStage;
    private Stage secondaryStage;
    private SceneName currentSceneName;
    private Scene currentScene;
    // Connection
    private Sender sender;
    private ClientGUI client;
    private GUIController controller;
    private SceneHandler sceneHandler;
    private HashMap<SceneName, Consumer<Command>> stageLambda;

    private void changeStage(Boolean fullScreen, Boolean secondStage){
        if(!secondStage){
            primaryStage.setTitle("My Shelfie");
            currentScene = sceneHandler.getScene(currentSceneName);
            controller = sceneHandler.getController(currentSceneName);
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
        stageLambda.put(SceneName.LOGIN, (command)-> {
            currentSceneName = SceneName.FINDGAME;
            changeStage(false, false);
        });
        stageLambda.put(SceneName.FINDGAME, (command)-> {
            currentSceneName = SceneName.GAME;
            changeStage(false, false);
            this.initGame();
        });
        stageLambda.put(SceneName.GAME, (command)-> {
            if(command == Command.CHAT) {
                currentSceneName = SceneName.CHAT;
                changeStage(false, true);
            }
            else if(command == Command.SHOW_BOOKSHELFS) {
                currentSceneName = SceneName.BOOKSHELFS;
                changeStage(false, true);
            }
            else if(command == Command.END) {
                currentSceneName = SceneName.END;
                changeStage(false, false);
            }
        });
        stageLambda.put(SceneName.END, (command)-> {
            if(command == Command.SHOW_STATISTICS) {
                currentSceneName = SceneName.STATISTICS;
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
            int exitStatus = AlertBox.exitRequest(primaryStage, windowEvent, "Are you sure you want to exit");
            if(exitStatus == 1) {
                System.exit(0);
            }
        });

        //stage.setFullScreen(true);
        stage.setTitle("My Shelfie");
        stage.setScene(scene);
        stage.show();
        // Funzione che simula un listener
        testMain();

    }
    private void testMain(){
        // Change scene test
    }

    public SceneName getCurrentScene() {
        return currentSceneName;
    }

    public static void main(String[] args) {
        launch();
    }

    public void switchStage(Command command){
        Platform.runLater(() -> {
            stageLambda.get(currentSceneName).accept(command);
        });
    }
    @Override
    public void setSender(Sender sender) {
        this.sender = sender;
    }

    @Override
    public Sender getSender() {
        return null;
    }

    @Override
    public void setClient() {

    }

    public Scene getScene() {
        return sceneHandler.getScene(currentSceneName);
    }

    @Override
    public Client getClient() {
        return this.client;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
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
    public void setRMIConnection() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();
        ControllerSkeleton controller = (ControllerSkeleton) registry.lookup(Settings.remoteObjectName);
        this.setSender(new RMISender(controller, this.getClient()));
        this.switchStage(Command.SET_CONNECTION);
    }
    public void setTCPConnection() throws IOException {
        TCPReceiver tcpreceiver = new TCPReceiver(client);
        ClientConnection clientConnection = new ClientConnection(new Socket("localhost", 8081), tcpreceiver);
        new Thread(clientConnection).start();
        this.setSender(new TCPSender(clientConnection,client));
        this.switchStage(Command.SET_CONNECTION);
    }

    /* ***************************************************
                 _     ___   ____ ___ _   _
                | |   / _ \ / ___|_ _| \ | |
                | |  | | | | |  _ | ||  \| |
                | |__| |_| | |_| || || |\  |
                |_____\___/ \____|___|_| \_|
     ****************************************************/
    public void login(){};

    /* ***************************************************
         _____ ___ _   _ ____     ____    _    __  __ _____
        |  ___|_ _| \ | |  _ \   / ___|  / \  |  \/  | ____|
        | |_   | ||  \| | | | | | |  _  / _ \ | |\/| |  _|
        |  _|  | || |\  | |_| | | |_| |/ ___ \| |  | | |___
        |_|   |___|_| \_|____/   \____/_/   \_\_|  |_|_____|
     *******************************************************/

    public void getGamesList() {
        this.sender.getGameList();
    }

    public void refreshGameList(List<LocalGame> games){
        FindGameController controllerTmp = (FindGameController) controller;
        ArrayList<String> gameList = new ArrayList<>();
        for ( LocalGame g: games) {
            gameList.add(g.toString());
        }
        controllerTmp.updateList(gameList);
    }
    public void addFirstPlayer(String name, GameMode gameMode, int numPlayer){
        sender.addFirstPlayer(name, gameMode, numPlayer);
    }
    public void joinGame(String name,int gameId){
        sender.addPlayer(name, gameId);
    }


    /* *****************************************************
      ____    _    __  __ _____
     / ___|  / \  |  \/  | ____|
    | |  _  / _ \ | |\/| |  _|
    | |_| |/ ___ \| |  | | |___
     \____/_/   \_\_|  |_|_____|
     ******************************************************/

    public void initGame(){
        GameController c = (GameController) sceneHandler.getController(currentSceneName);
        c.init();
    }
    // Server to client
    public void updateBoard(){
        Platform.runLater(() -> {
            GameController controllertmp = (GameController) this.controller;
            controllertmp.showBoard();
        });
    }
    public void updateBookShelf(){
        GameController controllertmp = (GameController) this.controller;
            Platform.runLater(()->{
            controllertmp.showBookshelf();
        });

    }

    // Client to server
    public void pickItem(Coordinates coordinates){
        sender.pickItem(coordinates);
    }
    public void putItemList(int column){
        sender.putItemList(column);
    }
    public void selectInsertOrder(ArrayList<Integer> positions) {
        sender.selectInsertOrder(positions);
    }
    public void setTurn(String name) {
        Platform.runLater(() -> {
            GameController controllertmp = (GameController) this.controller;
            controllertmp.setTurn(name);
        });

    }
    public void leaveGame(){
        sender.leaveGame();
    }

    /* ********************************************************
      ____   ____   ____  _  __ _____ _    _ ______ _      ______ _____
     |  _ \ / __ \ / __ \| |/ // ____| |  | |  ____| |    |  ____/ ____|
     | |_) | |  | | |  | | ' /| (___ | |__| | |__  | |    | |__ | (___
     |  _ <| |  | | |  | |  <  \___ \|  __  |  __| | |    |  __| \___ \
     | |_) | |__| | |__| | . \ ____) | |  | | |____| |____| |    ____) |
     |____/ \____/ \____/|_|\_\_____/|_|  |_|______|______|_|   |_____/
    ***********************************************************/

    //public void initBookshelfs(){}

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
    public void updateChat(ChatMessage chatMessage, String name){
        ChatController chat = (ChatController) this.controller;
        Platform.runLater(() ->{
            chat.displayMessage(chatMessage, name);
        });
    }
    public void sendMessage(String message, String receiver){
        if(receiver == null){
            sender.addChatMessage(message);
        } else {
            sender.addChatMessage(message, receiver);
        }
    }

    //TODO: sarebbe bello risolvere il fatto che non si possa riaprire la chat

    /* *********************
     ______ _   _ _____
     |  ____| \ | |  __ \
     | |__  |  \| | |  | |
     |  __| | . ` | |  | |
     | |____| |\  | |__| |
     |______|_| \_|_____/
     **********************/

    public void updateEnd(String name, ArrayList<LocalPlayer> localPlayers){
        EndController end = (EndController) this.controller;
        end.updateLabel(name, localPlayers);
    }

    /* *******************************************************************
       _____ _______    _______ _____  _____ _______ _____ _____  _____
      / ____|__   __|/\|__   __|_   _|/ ____|__   __|_   _/ ____|/ ____|
     | (___    | |  /  \  | |    | | | (___    | |    | || |    | (___
      \___ \   | | / /\ \ | |    | |  \___ \   | |    | || |     \___ \
      ____) |  | |/ ____ \| |   _| |_ ____) |  | |   _| || |____ ____) |
     |_____/   |_/_/    \_\_|  |_____|_____/   |_|  |_____\_____|_____/
     ********************************************************************/

    public void updateStatistics(String name, Map<String, LocalBookshelf> localBookshelfMap, ArrayList<LocalPlayer> localPlayers){
        StatisticsController statistics = (StatisticsController) this.controller;
        statistics.updateBookshelfs(name, localBookshelfMap, localPlayers);
    }

    public void updateHand() {
        Platform.runLater(() -> {
            GameController controllertmp = (GameController) this.controller;
            controllertmp.showHand();
        });

    }
}