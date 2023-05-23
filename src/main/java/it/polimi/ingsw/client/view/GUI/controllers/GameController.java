package it.polimi.ingsw.client.view.GUI.controllers;

import it.polimi.ingsw.client.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class GameController implements GUIController {
    private GUI gui;
    @FXML
    private AnchorPane backgroundPane;
    @FXML
    private GridPane boardGrid;
    @FXML
    private GridPane bookshelfGrid;
    @FXML
    private ImageView board;
    @FXML
    private ImageView bookshelf;
    @FXML
    private ImageView common1;
    @FXML
    private ImageView common2;
    @FXML
    private ImageView personalGoalCard;
    @FXML
    private Button chat;


    @Override
    public void setGui(GUI gui) { this.gui = gui;}

    @Override
    public GUI getGui() { return gui; }

}
