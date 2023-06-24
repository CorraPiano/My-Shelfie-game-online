package it.polimi.ingsw.client.view.GUI.controllers;
import it.polimi.ingsw.client.localModel.LocalGame;
import it.polimi.ingsw.client.view.GUI.AlertBox;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.model.GameMode;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class FindGameController implements GUIController {
    private GUI gui;
    private List<LocalGame> games;
    @FXML
    private ListView<String> gameList;
    @FXML
    private Button searchGame;
    @FXML
    private TextField gameMode;
    @FXML
    private TextField playerName;
    @FXML
    private TextField playerNameJoin;
    @FXML
    private TextField numberOfPlayer;
    @FXML
    protected void onSearchGameButton() {
        this.gui.getGamesList();
    }
    @FXML
    protected void onCreateGame() {
        String name = playerName.getText();
        String gMode = gameMode.getText();
        String nPlayer = numberOfPlayer.getText();
        if (checkDataCreate(name, gMode, nPlayer)) {
            int mode = Integer.parseInt(gMode);
            int playerNum = Integer.parseInt(nPlayer);
            gui.addFirstPlayer(name, GameMode.values()[mode], playerNum);
            // switch to lobby
        } else {
            int exitStatus = AlertBox.errorData(gui.getPrimaryStage(), "The parameters are wrong, please control the game rules below", "Input error");
        }
    }
    @FXML
    protected void onJoinGame() {
        String name = playerNameJoin.getText();
        LocalGame game = games.get(gameList.getSelectionModel().getSelectedIndex());
        String game_line = gameList.getSelectionModel().getSelectedItem();
        if(checkJoinData(name, game_line)) {
            int gameId = getGameId(game_line);
            gui.joinGame(name, gameId, (game.currPerson + 1 < game.maxPerson));
        }
        else{
            int exitStatus = AlertBox.errorData(gui.getPrimaryStage(), "The parameters are wrong, please control the game rules below", "Input error");
        }
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * @return the GUI.
     */
    @Override
    public GUI getGui() {
        return gui;
    }

    public void updateList(List<LocalGame> games) {
        this.games = games;
        ArrayList<String> games_str= new ArrayList<>();
        for ( LocalGame g: games) {
            games_str.add(g.toString());
        }
        if(games.size() == 0){
            Label label = new Label("I can't find any game");
            label.setFont(Font.font("Arial", FontWeight.BLACK, 12));
            gameList.setPlaceholder(label);
        }
        else {
            gameList.setPlaceholder(null);
            gameList.getItems().clear();
            gameList.getItems().addAll(games_str);
        }

    }


    public void waitStartGame() {
    }

    /* ****************************************************************************
        UTIL
     ********************************************************************************/
    // Check if the argument are correct
    private boolean checkDataCreate(String name, String mode, String nPlayers) {
        if (mode.length()==0 || nPlayers.length()==0 || name.length()==0)
            return false;
        for (int i = 0; i < nPlayers.length(); i++){
            if(!Character.isDigit(nPlayers.charAt(i)))
                return false;
        }
        for (int i = 0; i < mode.length(); i++){
            if(!Character.isDigit(mode.charAt(i)))
                return false;
        }
        int m = Integer.parseInt(mode);
        int numPlayers = Integer.parseInt(nPlayers);

        return (m == 0 || m == 1) && (numPlayers > 1 && numPlayers < 5);
    }

    private boolean checkJoinData(String name, String game){
        return name.length() !=0 && game != null;
    }
    private int getGameId(String game){
        String num = new String();
        for(int i = 4; i < game.length(); i++){
            if(Character.isDigit(game.charAt(i))){
                num += game.charAt(i);
            }else{break;}
        }
        return Integer.parseInt(num);
    }
    private int getCurrentPlayers(String game){
        int commaCounter = 0;
        for (int i=0; i<game.length(); i++){
            if(game.charAt(i) == ',' )
                commaCounter++;
            if (commaCounter == 3)
                return game.charAt(i) - '0';
        }
        return 0;
    }
    private int getNumPlayers(String game){
        int commaCounter = 0;
        for (int i=0; i<game.length(); i++){
            if(game.charAt(i) == ',' )
                commaCounter++;
            if (commaCounter == 2)
                return game.charAt(i) - '0';
        }
        return 0;
    }

}


