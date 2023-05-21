package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.view.GUI.controllers.Controller;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
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
        //stage.setFullScreen(true);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
        // Funzione che simula un listener
        testMain();
    }
    private void testMain(){
        // Change scene test
        PauseTransition delay = new PauseTransition(Duration.seconds(10));
        delay.setOnFinished(event -> {
            switchStage(Command.LOGIN);
        });
        delay.play();
        System.out.println("Ciao");
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

    /* *****************************************************
      ____    _    __  __ _____
     / ___|  / \  |  \/  | ____|
    | |  _  / _ \ | |\/| |  _|
    | |_| |/ ___ \| |  | | |___
     \____/_/   \_\_|  |_|_____|
     ******************************************************/


}