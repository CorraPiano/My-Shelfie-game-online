package it.polimi.ingsw.client.view.GUI.controllers;

import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.client.view.GUI.SceneName;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for managing the lobby in the GUI.
 */
public class LobbyController implements GUIController {

    @FXML
    public ImageView commonCard;
    private GUI gui;
    private int currentImage_asset;
    private int currentImage_common;
    private ArrayList<Image> images;
    @FXML
    Label notification;
    @FXML
    private ListView<String> playerLists;
    @FXML
    private ImageView images_pane;
    @FXML
    private Text commonDescription;
    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * Retrieves the GUI object associated with the controller.
     *
     * @return The GUI object associated with the controller.
     */
    @Override
    public GUI getGui() {
        return null;  // TODO: Implement the method logic
    }
    public void updatePlayerList(List<String> players){
        if (gui.getCurrentSceneName() == SceneName.LOBBY){
            playerLists.setPlaceholder(null);
            playerLists.getItems().clear();
            playerLists.getItems().addAll(players);
        }
    }

    /**
     * Displays a new notification in the lobby.
     *
     * @param message The notification message to be displayed.
     */
    public void newNotification(String message) {
        notification.setText(message);
    }

    /**
     * Initializes the lobby controller.
     */
    public void init() {
        currentImage_asset = 1;
        currentImage_common = 1;
        Platform.runLater(this::changeImage);
        Platform.runLater(this::changeCommon);
    }
    public void changeImage(){
        images_pane.setImage(this.gui.getSceneHandler().getAsset(currentImage_asset));
        if(currentImage_asset == 4 )
            currentImage_asset = 1;
        else
            currentImage_asset++;
    }
    public void changeCommon(){
        commonCard.setImage(this.gui.getSceneHandler().getCommon(currentImage_common));
        commonDescription.setText(this.gui.getSceneHandler().getCommonDescription(currentImage_common));
        if(currentImage_common == 11 )
            currentImage_common = 0;
        else currentImage_common++;

    /**
     * Changes the displayed common card image in the lobby.
     */
    public void changeCommon() {
            images_pane.setImage(this.gui.getSceneHandler().getAsset(currentImage_asset));
            if(currentImage_asset == 4 )
                currentImage_asset = 1;
            else currentImage_asset++;
    }

    /**
     * Quits the game and exits the lobby.
     */
    @FXML
    public void quitgame() {
        this.gui.quitGame();
    }
}
