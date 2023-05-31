package it.polimi.ingsw.client.view.GUI.controllers;

import it.polimi.ingsw.client.localModel.LocalBoard;
import it.polimi.ingsw.client.localModel.LocalCommonCard;
import it.polimi.ingsw.client.localModel.LocalHand;
import it.polimi.ingsw.client.localModel.LocalPersonalCard;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;;
import java.net.URL;
import java.util.ArrayList;

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

    public void init() {
        initBoard(gui.getClient().getModelView().getLocalBoard());
        //initCommon(gui.getClient().getModelView().getCommonCards());
        initPersonal(gui.getClient().getModelView().getLocalPersonalCard().num);
    }

    /* Auxiliary method used to initialize PersonalGoalCards in the scene */
    public void initPersonal(int num) {
        URL url = getClass().getResource(gui.getPersonalByType(num));
        if(url != null) {
            personalGoalCard.setImage(new Image(url.toString()));
        }
    }

    /* Auxiliary method used to initialize CommonGoalCards in the scene */
    public void initCommon(ArrayList<LocalCommonCard> commonCards) {

        int type1 = commonCards.get(0).getType();
        int type2 = commonCards.get(1).getType();

        URL url1 = getClass().getResource(gui.getCommonPathByType(type1));
                if(url1 != null) {
                    common1.setImage(new Image(url1.toString()));
                }

        URL url2 = getClass().getResource(gui.getCommonPathByType(type2));
        if(url2 != null) {
            common2.setImage(new Image(url2.toString()));
        }
    }

    public void initBoard(LocalBoard localBoard){
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                Item item = localBoard.board[i][j];
                if (item != null) {
                    URL url = getClass().getResource(gui.getItemPathByType(item.getType()));
                    if(url != null) {
                        ImageView imageView = new ImageView();
                        Image image = new Image(url.toString());
                        imageView.setImage(image);
                        imageView.setFitWidth(49);
                        imageView.setFitHeight(49);
                        boardGrid.add(imageView, i, j);
                    }

                }

            }
        }
    }

    public void onBoardClicked (MouseEvent event){
        Coordinates clickCoordinates = getBoardCellsIndexes(event);
        if ( clickCoordinates != null){
            gui.pickItem(clickCoordinates);
        }
    }

    public Coordinates getBoardCellsIndexes(MouseEvent event){
        int numColumns = 9;
        int numRows = 9;

        double cellWidth = boardGrid.getWidth() / numColumns;
        double cellHeight = boardGrid.getHeight() / numRows;
        int clickedRow = (int) (event.getY() / cellHeight);
        int clickedCol = (int) (event.getX() / cellWidth);

        return new Coordinates(clickedRow,clickedCol);

    }

    public void updateBoard(LocalBoard localBoard, LocalHand hand){
    }
    public void onBookshelfClick(){

    }

}



