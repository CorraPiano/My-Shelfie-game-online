package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.view.GUI.controllers.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.HashMap;
import java.util.function.Consumer;

public class SceneHandler {
    private final HashMap<SceneName, Consumer<String>> stageMap;
    private final HashMap<SceneName, Scene> sceneToFxml;
    private final HashMap<SceneName, Controller> sceneToController;



    /*
        - Set all the fxml and the HashMap relative to the scene
        - Set the gui for all controllers
     */
    private void setupMap(GUI gui){
        try {
            String path = "/fxml/";
            // Login
            FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource(path + "login.fxml"));
            Scene scene = scene = new Scene(fxmlLoader.load());
            sceneToFxml.put(SceneName.LOGIN, scene);
            Controller controller = fxmlLoader.getController();
            controller.setGui(gui);
            sceneToController.put(SceneName.LOGIN, controller);
            // Find game
            fxmlLoader = new FXMLLoader(GUI.class.getResource(path + "find-game.fxml"));
            scene = new Scene(fxmlLoader.load());
            sceneToFxml.put(SceneName.FINDGAME, scene);
            controller = fxmlLoader.getController();
            controller.setGui(gui);
            sceneToController.put(SceneName.FINDGAME, controller);
            // Game
            fxmlLoader = new FXMLLoader(GUI.class.getResource(path + "game.fxml"));
            scene = new Scene(fxmlLoader.load());
            sceneToFxml.put(SceneName.GAME, scene);
            controller = fxmlLoader.getController();
            controller.setGui(gui);
            sceneToController.put(SceneName.GAME, controller);
        } catch (IOException e) {
            System.out.println("Dio cane");
            throw new RuntimeException(e);
        }
    }
    private void setUpStageMap(){}

    public SceneHandler(GUI gui) {
        this.stageMap = new HashMap<>();
        this.sceneToFxml = new HashMap<>();
        this.sceneToController = new HashMap<>();
        setupMap(gui);
        setUpStageMap();
    }

    public void switchScene(SceneName current, String command){
        stageMap.get(current).accept(command);
    }
    public Scene getScene(SceneName scene){
        return sceneToFxml.get(scene);
    }
    public Controller getController(SceneName scene){
        return sceneToController.get(scene);
    }

}
