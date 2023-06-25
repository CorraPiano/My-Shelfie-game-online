package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.view.GUI.controllers.GUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * The `SceneHandler` class manages the scenes and controllers in a JavaFX application.
 * It provides methods to load scenes from FXML files, set up mappings between scenes and controllers,
 * and retrieve scenes and controllers by their corresponding names.
 */
public class SceneHandler {

    private final HashMap<SceneName, Scene> sceneToFxml;
    private final HashMap<SceneName, GUIController> sceneToController;
    private final List<Image> images_asset;
    private final List<Image> images_common;


    private void loadImages() {
        // ASSET
        List<String> image_file = List.of(
                "Display_1.jpg",
                "Display_2.jpg",
                "Display_3.jpg",
                "Display_4.jpg"
        );
        URL url;
        String file = "/Images/PublisherMaterial/";
        String img;
        for (String filename : image_file) {
            url = getClass().getResource(file + filename);
            this.images_asset.add(new Image(url.toString()));
        }
        // COMMON CARD
        file = "/Images/common/";
        for (int i = 0; i < 12; i++) {
            img = file + i + ".jpg";
            url = getClass().getResource(img);
            this.images_common.add(new Image(url.toString()));
        }
    }

    private void setupMap(GUI gui) {
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

            // Bookshelves
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

    private void setUpStageMap() {
        // TODO: Implement this method
    }

    /**
     * Constructs a `SceneHandler` object with the given GUI instance.
     *
     * @param gui The GUI instance.
     */
    public SceneHandler(GUI gui) {
        this.images_asset = new ArrayList<>();
        this.images_common = new ArrayList<>();
        this.sceneToFxml = new HashMap<>();
        this.sceneToController = new HashMap<>();
        loadImages();
        setupMap(gui);
        setUpStageMap();
    }

    /**
     * Returns the scene associated with the given scene name.
     *
     * @param scene The scene name.
     * @return The corresponding scene.
     */
    public Scene getScene(SceneName scene) {
        return sceneToFxml.get(scene);
    }

    /**
     * Returns the controller associated with the given scene name.
     *
     * @param scene The scene name.
     * @return The corresponding controller.
     */
    public GUIController getController(SceneName scene) {
        return sceneToController.get(scene);
    }

    /**
     * Returns the asset image with the specified ID.
     *
     * @param ID The ID of the asset image (1-4).
     * @return The corresponding image.
     */
    public Image getAsset(int ID) {
        /*
        ID = 1 : Display 1
        ID = 2 : Display 2
        ID = 3 : Display 3
        ID = 4 : Display 4
         */
        return images_asset.get(ID - 1);
    }

    /**
     * Returns the common image with the specified ID.
     *
     * @param ID The ID of the common image.
     * @return The corresponding image.
     */
    public Image getCommon(int ID) {
        return images_common.get(ID - 1);
    }

}
