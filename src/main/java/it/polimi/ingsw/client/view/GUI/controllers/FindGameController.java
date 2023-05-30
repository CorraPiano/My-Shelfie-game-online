package it.polimi.ingsw.client.view.GUI.controllers;
import it.polimi.ingsw.client.localModel.LocalBoard;
import it.polimi.ingsw.client.localModel.LocalCommonCard;
import it.polimi.ingsw.client.localModel.LocalPersonalCard;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.model.GameMode;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class FindGameController implements GUIController {
    private GUI gui;
    @FXML
    private ListView<String> gameList;
    @FXML
    private Button searchGame;
    @FXML
    private TextField gameId;
    @FXML
    private TextField gameMode;
    @FXML
    private TextField playerName;
    @FXML
    private TextField playerNameJoin;
    @FXML
    private TextField numberOfPlayer;
    @FXML
    protected void onSearchGameButton(){
        this.gui.getGamesList();
    }
    @FXML
    protected void onCreateGame(){
        String name = playerName.getText();
        int mode = Integer.parseInt(gameMode.getText());
        int playerNum = Integer.parseInt(numberOfPlayer.getText());
        gui.addFirstPlayer(name, GameMode.values()[mode], playerNum);
    }
    @FXML
    protected void onJoinGame(){
        int gameId = Integer.parseInt(this.gameId.getText());
        String name = playerNameJoin.getText();
        gui.joinGame(name, gameId);
    }

    @Override
    public void setGui(GUI gui) { this.gui = gui;}

    /**
     * @return the GUI.
     */
    @Override
    public GUI getGui() {
        return gui;
    }

    @Override
    public void init() {

    }


    public void update(List<String> games){
        gameList.getItems().clear();
        gameList.getItems().addAll(games);
    }

    public void waitStartGame(){}

}
