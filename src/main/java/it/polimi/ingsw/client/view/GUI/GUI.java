package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.view.GUI.controllers.Controller;
import it.polimi.ingsw.client.view.GUI.controllers.FindGameController;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.awt.event.WindowEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class GUI extends Application {
    private Stage primaryStage;
    private Stage secondaryStage;
    private SceneName currentSceneName;
    private Scene currentScene;
    private Controller controller;
    private SceneHandler sceneHandler;
    private HashMap<SceneName, Consumer<Command>> stageLambda;
    private void changeStage(Boolean fullScreen){
        primaryStage.setTitle("My Shelfie");
        currentScene = sceneHandler.getScene(currentSceneName);
        controller = sceneHandler.getController(currentSceneName);
        primaryStage.setScene(currentScene);
        primaryStage.show();
    }
    private void setLambdaMap(){
        stageLambda = new HashMap<>();
        stageLambda.put(SceneName.LOGIN, (command)-> {
            currentSceneName = SceneName.FINDGAME;
            changeStage(false);
        });
        stageLambda.put(SceneName.FINDGAME, (command)-> {
            currentSceneName = SceneName.GAME;
            changeStage(false);
        });
        stageLambda.put(SceneName.GAME, (command)-> {
            currentSceneName = SceneName.GAME;
            changeStage(false);
        });
    }

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        sceneHandler = new SceneHandler(this);
        setLambdaMap();
        currentSceneName = SceneName.LOGIN;
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
        PauseTransition delay = new PauseTransition(Duration.seconds(2)), delay2 = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> {
            switchStage(Command.LOGIN);
            delay2.play();
        });
        delay.play();
        delay2.setOnFinished(event -> {
            refreshGameList(Arrays.asList(
                    "ID: 3 NUmber of player: 3",
                    "ID: 2 NUmber of player: 3",
                    "ID: 1 NUmber of player: 3"
            ));
        });


    }

    public SceneName getCurrentScene() {
        return currentSceneName;
    }

    public static void main(String[] args) {
        launch();
    }

    public void switchStage(Command command){
        Platform.runLater(() -> stageLambda.get(currentSceneName).accept(command));
        controller = sceneHandler.getController(currentSceneName);
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
    public void refreshGameList(List<String> games){
        FindGameController controllerTmp = (FindGameController) controller;
        controllerTmp.update(games);
    }
    /* *****************************************************
      ____    _    __  __ _____
     / ___|  / \  |  \/  | ____|
    | |  _  / _ \ | |\/| |  _|
    | |_| |/ ___ \| |  | | |___
     \____/_/   \_\_|  |_|_____|
     ******************************************************/


}