package it.polimi.ingsw.client.view.GUI.controllers;

import it.polimi.ingsw.client.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

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
        gui.login();
    }

    @Override
    public void setGui(GUI gui) { this.gui = gui; }

    @Override
    public GUI getGui() { return this.gui; }
}
