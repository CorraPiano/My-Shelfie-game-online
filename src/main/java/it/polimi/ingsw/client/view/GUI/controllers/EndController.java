package it.polimi.ingsw.client.view.GUI.controllers;

import it.polimi.ingsw.client.localModel.LocalPlayer;
import it.polimi.ingsw.client.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Objects;

import static it.polimi.ingsw.util.Constants.ANSI_CYAN;
import static it.polimi.ingsw.util.Constants.ANSI_RESET;

public class EndController implements GUIController {

    @FXML
    Label title;

    public void updateLabel(String name, ArrayList<LocalPlayer> localPlayers) {
        String result = new String();
        for(int i=0; i<localPlayers.size(); i++){
            if(Objects.equals(localPlayers.get(i).name, name)){
                if(i==0){
                    result = "Victory";
                    break;
                } else if(i==1){
                    result = "2° place";
                    break;
                } else if(i==2){
                    result = "3° place";
                    break;
                } else {
                    result = "4° place";
                    break;
                }
            }
        }
        title.setText(result);
    }

    public void onShowStatistics(){}
    public void onQuit(){}

    @Override
    public void setGui(GUI gui) {

    }

    @Override
    public GUI getGui() {
        return null;
    }
}
