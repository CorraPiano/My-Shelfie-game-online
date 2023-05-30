package it.polimi.ingsw.client.view.GUI.controllers;

import it.polimi.ingsw.client.localModel.LocalBoard;
import it.polimi.ingsw.client.localModel.LocalCommonCard;
import it.polimi.ingsw.client.localModel.LocalPersonalCard;
import it.polimi.ingsw.client.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController implements GUIController {
    private GUI gui;
    @FXML
    private TextField userID;
    @FXML
    private PasswordField password;
    @FXML
    protected void onLoginButton() throws IOException {
        String user = userID.getText();
        String pw = password.getText();
        System.out.println("User: " + user + "\t pw: "+ pw);
        gui.getClient();
    }

    @Override
    public void setGui(GUI gui) { this.gui = gui; }

    @Override
    public GUI getGui() { return this.gui; }

    @Override
    public void init() {

    }


}
