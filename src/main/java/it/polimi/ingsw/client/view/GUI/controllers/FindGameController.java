package it.polimi.ingsw.client.view.GUI.controllers;
import it.polimi.ingsw.client.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class FindGameController implements Controller{
    private GUI gui;
    @FXML
    private ListView GameList;
    @FXML
    private Button searchGame;
    @FXML
    private TextField gameId;
    @FXML
    private TextField playerName;
    @FXML
    private TextField numberOfPlayer;
    @FXML
    protected void onSearchGameButton(){}
    @FXML
    protected void onCreateGame(){}
    @FXML
    protected void onJoinGame(){
        String gameId = this.gameId.getText();
    }

    @Override
    public void setGui(GUI gui) { this.gui = gui;}
}
