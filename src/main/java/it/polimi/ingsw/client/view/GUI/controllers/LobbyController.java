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

public class LobbyController implements GUIController{
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
    @Override
    public GUI getGui() {
        return null;
    }
    public void updatePlayerList(List<String> players){
        if (gui.getCurrentSceneName() == SceneName.LOBBY){
            playerLists.setPlaceholder(null);
            playerLists.getItems().clear();
            playerLists.getItems().addAll(players);
        }
    }
    public void newNotification(String message){
        notification.setText(message);
    }
    public void init(){
        currentImage_asset = 1;
        currentImage_common = 1;
        Platform.runLater(()->changeImage());
        Platform.runLater(()->changeCommon());

    }
    public void changeImage(){
        images_pane.setImage(this.gui.getSceneHandler().getAsset(currentImage_asset));
        if(currentImage_asset == 4 )
            currentImage_asset = 1;
        else currentImage_asset++;
    }
    public void changeCommon(){
        commonCard.setImage(this.gui.getSceneHandler().getCommon(currentImage_common));
        commonDescription.setText(this.gui.getSceneHandler().getCommonDescription(currentImage_common));
        if(currentImage_common == 11 )
            currentImage_common = 0;
        else currentImage_common++;
    }
    @FXML
    public void quitgame(){this.gui.quitGame();}
}
