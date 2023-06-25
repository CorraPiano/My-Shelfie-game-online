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
    private final List<String> commonDescription;

    // Immagini di tutti gli item caricate in partenza
    private final List<Image> itemBlue;
    private final List<Image> itemCyan;
    private final List<Image> itemGreen;
    private final List<Image> itemPink;
    private final List<Image> itemWhite;
    private final List<Image> itemYellow;


    /*
        - Set all the fxml and the HashMap relative to the scene
        - Set the gui for all controllers
     */
    private void loadImages(){
        // ASSET
        List<String> image_file = Arrays.asList(
                "Display_1.jpg",
                "Display_2.jpg",
                "Display_3.jpg",
                "Display_4.jpg"
        );
        URL url;
        String file = "/Images/PublisherMaterial/";
        String img;
        for(int i = 0; i<image_file.size(); i++){
            url = getClass().getResource(file + image_file.get(i));
            this.images_asset.add(new Image(url.toString()));
        }
        // COMMON CARD
        file = "/Images/common/";
        for(int i = 0; i<12; i++){
            img = file + String.valueOf(i) + ".jpg";
            url = getClass().getResource(img);
            this.images_common.add(new Image(url.toString()));
        }
        // ITEM
        file = "/Images/items/";
        String imgBlue, imgCyan, imgGreen, imgWhite, imgPink, imgYellow;
        for(int i = 0; i<3; i++){
            imgBlue = file + "Blue" + String.valueOf(i+1)  + ".png";
            imgCyan = file + "Cyan" + String.valueOf(i+1)  + ".png";
            imgGreen = file + "Green" + String.valueOf(i+1)  + ".png";
            imgPink = file + "Pink" + String.valueOf(i+1)  + ".png";
            imgWhite = file + "White" + String.valueOf(i+1)  + ".png";
            imgYellow = file + "Yellow" + String.valueOf(i+1)  + ".png";

            url = getClass().getResource(imgBlue);
            this.itemBlue.add(new Image(url.toString()));
            url = getClass().getResource(imgCyan);
            this.itemCyan.add(new Image(url.toString()));
            url = getClass().getResource(imgGreen);
            this.itemGreen.add(new Image(url.toString()));
            url = getClass().getResource(imgPink);
            this.itemPink.add(new Image(url.toString()));
            url = getClass().getResource(imgWhite);
            this.itemWhite.add(new Image(url.toString()));
            url = getClass().getResource(imgYellow);
            this.itemYellow.add(new Image(url.toString()));
        }

    }
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
    private void setupCommonDescription() {
        commonDescription.add(0,
                "Six groups each containing at least" +
                        " 2 tiles of the same type (not necessarily" +
                        " in the depicted shape).\n" +
                        " The tiles of one group can be different" +
                        " from those of another group");
        commonDescription.add(1,
                "Four groups each containing at least" +
                        " 4 tiles of the same type (not necessarily" +
                        " in the depicted shape).\n" +
                        " The tiles of one group can be different" +
                        " from those of another group");
        commonDescription.add(2,
                "Four tiles of the same type in the four" +
                        " corners of the bookshelf");
        commonDescription.add(3,
                "Two groups each containing 4 tiles of" +
                        " the same type in a 2x2 square. The tiles" +
                        " of one square can be different from" +
                        " those of the other square");
        commonDescription.add(4,
                "Three columns each formed by 6 tiles" +
                        " of maximum three different types. One" +
                        " column can show the same or a different" +
                        " combination of another column");
        commonDescription.add(5,
                "Eight tiles of the same type. There’s no" +
                        " restriction about the position of these" +
                        " tiles");
        commonDescription.add(6,
                "Five tiles of the same type forming a" +
                        " diagonal");
        commonDescription.add(7,
                "Four lines each formed by 5 tiles of" +
                        " maximum three different types. One" +
                        " line can show the same or a different" +
                        " combination of another line");
        commonDescription.add(8,
                "Two columns each formed by 6" +
                        " different types of tiles");
        commonDescription.add(9,
                "Two lines each formed by 5 different" +
                        " types of tiles. One line can show the" +
                        " same or a different combination of the" +
                        " other line");
        commonDescription.add(10, "Five tiles of the same type forming an X");
        commonDescription.add(11,
                "Five columns of increasing or decreasing" +
                        " height. Starting from the first column on" +
                        " the left or on the right, each next column" +
                        " must be made of exactly one more tile.\n" +
                        " Tiles can be of any type");
    }
    private void setUpStageMap(){}
    public SceneHandler(GUI gui) {
        this.images_asset = new ArrayList<>();
        this.images_common = new ArrayList<>();
        this.sceneToFxml = new HashMap<>();
        this.sceneToController = new HashMap<>();
        this.commonDescription =new ArrayList<>();
        this.itemBlue = new ArrayList<>();
        this.itemCyan = new ArrayList<>();
        this.itemGreen = new ArrayList<>();
        this.itemPink = new ArrayList<>();
        this.itemWhite = new ArrayList<>();
        this.itemYellow = new ArrayList<>();
        loadImages();
        setupMap(gui);
        setupCommonDescription();
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
    public Image getCommon(int ID){
        return images_common.get(ID);
    }
    public String getCommonDescription(int num_card) {
        return commonDescription.get(num_card);
    }

    public List<Image> getItemBlue() {
        return itemBlue;
    }
    public List<Image> getItemCyan() {
        return itemCyan;
    }

    public List<Image> getItemPink() {
        return itemPink;
    }

    public List<Image> getItemGreen() {
        return itemGreen;
    }

    public List<Image> getItemWhite() {
        return itemWhite;
    }

    public List<Image> getItemYellow() {
        return itemYellow;
    }
}
