package it.polimi.ingsw.client.view.GUI.controllers;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.exception.EmptySlotPickException;
import it.polimi.ingsw.exception.LimitReachedPickException;
import it.polimi.ingsw.exception.NotCatchablePickException;
import it.polimi.ingsw.exception.OutOfBoardPickException;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.ItemType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

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

    private LocalBoard localBoard;
    private LocalHand localHand;
    private LocalPersonalCard localPersonalCard;
    private LocalBookshelf localBookshelf;

    @Override
    public void setGui(GUI gui) { this.gui = gui;}
    @Override
    public GUI getGui() { return gui; }

    /* Scene initializers */
    public void init() {
        this.localBoard = gui.getClient().getModelView().getLocalBoard();
        this.localPersonalCard = gui.getClient().getModelView().getLocalPersonalCard();
        this.localHand = gui.getClient().getModelView().getLocalHand();

        initBoard(localBoard);
        initCommon(gui.getClient().getModelView().getCommonCards());
        initPersonal(localPersonalCard.num);
    }
    public void initPersonal(int num) {
        URL url = getClass().getResource(getPersonalByType(num));
        if(url != null) {
            personalGoalCard.setImage(new Image(url.toString()));
        }
    }
    public void initCommon(ArrayList<LocalCommonCard> commonCards) {

        int type1 = commonCards.get(0).getType();
        int type2 = commonCards.get(1).getType();

        URL url1 = getClass().getResource(getCommonPathByType(type1));
                if(url1 != null) {
                    common1.setImage(new Image(url1.toString()));
                }

        URL url2 = getClass().getResource(getCommonPathByType(type2));
        if(url2 != null) {
            common2.setImage(new Image(url2.toString()));
        }
    }
    public void initBoard(LocalBoard localBoard){
        this.localBoard = localBoard;
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                Item item = localBoard.board[i][j];
                if (item != null) {
                    URL url = getClass().getResource(getItemPathByType(item.getType()));
                    if (url != null) {
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

    /* Images and paths getters */
    public String getCommonPathByType(int type) {
        return "/Images/common/" + (type + 1) + ".jpg";
    }

    public String getItemPathByType(ItemType type) {
        Random random = new Random();
        int i = random.nextInt(3) + 1;

        return switch(type){
            case BLUE -> "/Images/items/Blue" + i + ".png";
            case YELLOW -> "/Images/items/Yellow" + i + ".png";
            case GREEN -> "/Images/items/Green" + i + ".png";
            case CYAN -> "/Images/items/Cyan" + i + ".png";
            case WHITE -> "/Images/items/White" + i + ".png";
            case PINK -> "/Images/items/Pink" + i + ".png";
        };

    }

    public String getPersonalByType(int n) {
        return "/Images/personal/" + n + ".png";
    }

   /* On click methods */

    public void onBoardClicked (MouseEvent event) {
        Coordinates clickCoordinates = getBoardCellsIndexes(event);
        ImageView clickedImageView = (ImageView) boardGrid.getChildren().stream()
                .filter(node -> GridPane.getRowIndex(node) == clickCoordinates.getRow()
                        && GridPane.getColumnIndex(node) == clickCoordinates.getColumn())
                .findFirst()
                .orElse(null);

        if (clickCoordinates != null) {
            gui.pickItem(clickCoordinates);
        }
    }
    public void onBookshelfClick(MouseEvent event){
        int column = getBookshelfCellsColumn(event);
        gui.putItemList(column);

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
    public int getBookshelfCellsColumn(MouseEvent event){
        int numColumns = 5;
        int numRows = 6;

        double cellWidth = boardGrid.getWidth() / numColumns;
        int clickedCol = (int) (event.getX() / cellWidth);

        return clickedCol;
    }

    /* Updating and printing */
    /*public void printBookshelf(int column, LocalHand hand) {
        //dovrò aggiungere la hand riordinata nella colonna passata come parametro
        for (Item item : localHand.hand){
            for(int i = 0; i < 5; i++){
                if (localBookshelf.bookshelf[i][column] == null) {
                    bookshelfGrid.add();
                    i = 6;
                }
            }

        }
    }*/

    /*   public void updateBoard(LocalBoard updatedBoard, LocalHand hand){
        this.localHand = hand;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (localBoard.board[i][j] != updatedBoard.board[i][j]) {
                    localBoard.board[i][j] = updatedBoard.board[i][j];
                }
            }
        }
    }*/

    /*public void updateBookShelf(LocalBookshelf updatedBookshelf){
        this.localBookshelf = updatedBookshelf;
    }*/


}



