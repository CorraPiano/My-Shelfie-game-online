package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.view.GUI.controllers.GUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.HashMap;
import java.util.function.Consumer;

public class SceneHandler {

    private final HashMap<SceneName, Scene> sceneToFxml;
    private final HashMap<SceneName, GUIController> sceneToController;


    /*
        - Set all the fxml and the HashMap relative to the scene
        - Set the gui for all controllers
     */
    private void setupMap(GUI gui){
        try {
            String path = "/fxml/";
            SceneName name;

            // Setup
            FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource(path + "setup.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            name = SceneName.SETUP;
            sceneToFxml.put(name, scene);
            GUIController controller = fxmlLoader.getController();
            controller.setGui(gui);
            sceneToController.put(name, controller);

            // Login
            fxmlLoader = new FXMLLoader(GUI.class.getResource(path + "login.fxml"));
            scene = new Scene(fxmlLoader.load());
            name = SceneName.LOGIN;
            sceneToFxml.put(name, scene);
            controller = fxmlLoader.getController();
            controller.setGui(gui);
            sceneToController.put(name, controller);

            // Find game
            fxmlLoader = new FXMLLoader(GUI.class.getResource(path + "find-game.fxml"));
            scene = new Scene(fxmlLoader.load());
            name = SceneName.FINDGAME;
            sceneToFxml.put(name, scene);
            controller = fxmlLoader.getController();
            controller.setGui(gui);
            sceneToController.put(name, controller);

            // Lobby
            fxmlLoader = new FXMLLoader(GUI.class.getResource(path + "lobby.fxml"));
            scene = new Scene(fxmlLoader.load());
            name = SceneName.LOBBY;
            sceneToFxml.put(name, scene);
            controller = fxmlLoader.getController();
            controller.setGui(gui);
            sceneToController.put(name, controller);

            // Game
            fxmlLoader = new FXMLLoader(GUI.class.getResource(path + "gameTable.fxml"));
            scene = new Scene(fxmlLoader.load());
            name = SceneName.GAME;
            sceneToFxml.put(name, scene);
            controller = fxmlLoader.getController();
            controller.setGui(gui);
            sceneToController.put(name, controller);

            // Chat
            fxmlLoader = new FXMLLoader(GUI.class.getResource(path + "chat.fxml"));
            scene = new Scene(fxmlLoader.load());
            name = SceneName.CHAT;
            sceneToFxml.put(name, scene);
            controller = fxmlLoader.getController();
            controller.setGui(gui);
            sceneToController.put(name, controller);

            // Bookshelfs
            fxmlLoader = new FXMLLoader(GUI.class.getResource(path + "bookshelfs.fxml"));
            scene = new Scene(fxmlLoader.load());
            name = SceneName.BOOKSHELFS;
            sceneToFxml.put(name, scene);
            controller = fxmlLoader.getController();
            controller.setGui(gui);
            sceneToController.put(name, controller);

            // End
            fxmlLoader = new FXMLLoader(GUI.class.getResource(path + "end.fxml"));
            scene = new Scene(fxmlLoader.load());
            name = SceneName.END;
            sceneToFxml.put(name, scene);
            controller = fxmlLoader.getController();
            controller.setGui(gui);
            sceneToController.put(name, controller);

            // Statistics
            fxmlLoader = new FXMLLoader(GUI.class.getResource(path + "statistics.fxml"));
            scene = new Scene(fxmlLoader.load());
            name = SceneName.STATISTICS;
            sceneToFxml.put(name, scene);
            controller = fxmlLoader.getController();
            controller.setGui(gui);
            sceneToController.put(name, controller);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setUpStageMap(){}
    public SceneHandler(GUI gui) {
        this.sceneToFxml = new HashMap<>();
        this.sceneToController = new HashMap<>();
        setupMap(gui);
        setUpStageMap();
    }
    public Scene getScene(SceneName scene){
        return sceneToFxml.get(scene);
    }
    public GUIController getController(SceneName scene){
        return sceneToController.get(scene);
    }

}
