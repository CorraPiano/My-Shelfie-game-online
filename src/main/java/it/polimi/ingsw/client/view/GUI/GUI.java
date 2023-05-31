package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientGUI;
import it.polimi.ingsw.client.connection.*;
import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.client.view.GUI.controllers.GUIController;
import it.polimi.ingsw.client.view.GUI.controllers.FindGameController;
import it.polimi.ingsw.client.view.GUI.controllers.GameController;
import it.polimi.ingsw.client.view.View;
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
import java.util.Random;
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
        });
        stageLambda.put(SceneName.GAME, (command)-> {
            if(command == Command.CHAT) {
                currentSceneName = SceneName.CHAT;
                changeStage(false, true);
            }
            else if(command == Command.SHOW_BOOKSHELFS) {
                currentSceneName = SceneName.BOOKSHELFS;
                changeStage(false, false);
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
            int exitStatus = ConfirmationBox.exitRequest(primaryStage, windowEvent, "Are you sure you want to exit");
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
            controller = sceneHandler.getController(currentSceneName);
            if ( controller != null ){
                controller.init();
            }
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

    @Override
    public Client getClient() {
        return this.client;
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
        controllerTmp.update(gameList);
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
    // Server to client
    public void updateBoard(LocalBoard board, LocalHand hand){
        GameController controllertmp = (GameController) this.controller;
        //controllertmp.updateBoard(board,hand);
    }
    public void updateBookShelf(LocalBookshelf localBookshelf, String name){
        GameController controllertmp = (GameController) this.controller;
        //controllertmp.updateBookShelf(localBookshelf);
    }

    // Client to server
    public void pickItem(Coordinates coordinates){
        sender.pickItem(coordinates);
    }
    public void putItemList(int column){
        sender.putItemList(column);
    }
    public void sendMessage(String message, String receiver){
        sender.addChatMessage(message, receiver);

    }
    public void leaveGame(){
        sender.leaveGame();
    }

    // Chat
    public void openChat(){}

    public void closeChat(){}

}