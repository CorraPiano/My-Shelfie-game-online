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

public class SceneHandler {

    private final HashMap<SceneName, Scene> sceneToFxml;
    private final HashMap<SceneName, GUIController> sceneToController;
    private final List<Image> images_asset;
    private final List<Image> images_common;
    private final List<String> commonDescription;


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

    }
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
        this.images_asset = new ArrayList<>();
        this.images_common = new ArrayList<>();
        this.sceneToFxml = new HashMap<>();
        this.sceneToController = new HashMap<>();
        this.commonDescription =new ArrayList<>();
        loadImages();
        setupMap(gui);
        setUpStageMap();
        setupCommonDescription();
    }
    public Scene getScene(SceneName scene){
        return sceneToFxml.get(scene);
    }
    public GUIController getController(SceneName scene){
        return sceneToController.get(scene);
    }

    public Image getAsset(int ID){
        /*
        ID = 1 : Display 1
        ID = 2 : Display 2
        ID = 3 : Display 3
        ID = 4 : Display 4
         */
        return images_asset.get(ID - 1);
    }
    public Image getCommon(int ID){
        return images_common.get(ID);
    }

    private void setupCommonDescription(){
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

    public String getCommonDescription(int num_card) {
        return commonDescription.get(num_card);
    }
}
