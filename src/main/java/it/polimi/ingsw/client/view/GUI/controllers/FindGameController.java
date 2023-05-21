package it.polimi.ingsw.client.view.GUI.controllers;
import it.polimi.ingsw.client.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class FindGameController implements Controller{
    private GUI gui;
    @FXML
    private ListView<String> gameList;
    @FXML
    private Button searchGame;
    @FXML
    private TextField gameId;
    @FXML
    private TextField playerName;
    @FXML
    private TextField playerNameJoin;
    @FXML
    private TextField numberOfPlayer;
    @FXML
    protected void onSearchGameButton(){

    }
    public void updateGameList(){

    }
    @FXML
    protected void onCreateGame(){
        String name = playerName.getText();
        int playerNum = Integer.parseInt(numberOfPlayer.getText());
        System.out.println("Name: " + name + "Number of player: " + playerNum);
    }
    @FXML
    protected void onJoinGame(){
        String gameId = this.gameId.getText();
        String name = playerNameJoin.getText();
        System.out.println("JOIN\nGame: "+ gameId + " Name: " + name);
    }

    @Override
    public void setGui(GUI gui) { this.gui = gui;}

    public void update(List<String> games){
        gameList.getItems().addAll(games);
    }

}
