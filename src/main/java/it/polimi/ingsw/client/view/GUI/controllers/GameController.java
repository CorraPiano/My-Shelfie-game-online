package it.polimi.ingsw.client.view.GUI.controllers;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.client.view.GUI.Command;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.exception.LimitReachedPickException;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Item;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;;
import java.net.URL;
import java.util.ArrayList;

import static it.polimi.ingsw.util.Constants.*;

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
    @FXML
    private ImageView hand1;
    @FXML
    private ImageView hand2;
    @FXML
    private ImageView hand3;
    @FXML
    private ListView<String> gameNotifications;
    private int clickCount;
    private LocalBoard localBoard;
    private LocalHand localHand;
    private LocalPersonalCard localPersonalCard;
    private LocalBookshelf localBookshelf;
    private ArrayList<Integer> handOrder = new ArrayList<>();
    private ArrayList<Coordinates> localHandCoordinates = new ArrayList<>();

    @Override
    public void setGui(GUI gui) { this.gui = gui;}
    @Override
    public GUI getGui() { return gui; }

    /* Scene initializers */
    public void init() {
        this.localBoard = gui.getClient().getModelView().getLocalBoard();
        this.localPersonalCard = gui.getClient().getModelView().getLocalPersonalCard();
        this.localHand = gui.getClient().getModelView().getLocalHand();
        this.clickCount = 0;
        initBoard(localBoard);
        initPersonal(localPersonalCard.num);
        //initCommon(gui.getClient().getModelView().getCommonCards());
    }
    public void initBoard(LocalBoard localBoard){
        this.localBoard = localBoard;
        showBoard();
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
    public void initPersonal(int num) {
        URL url = getClass().getResource(getPersonalByType(num));
        if(url != null) {
            personalGoalCard.setImage(new Image(url.toString()));
        }
    }

   /* On click methods */
    public void onBoardClicked (MouseEvent event) {
        Coordinates clickGridCoordinates = getGridCellsIndexes(event);
        Coordinates clickBoardCoordinates = getBoardCellsIndexes(event); //actual model coordinates
        ImageView imageHand = getImageViewFromGridPane(boardGrid, clickGridCoordinates.getRow(), clickGridCoordinates.getColumn());
        localHandCoordinates.add(clickBoardCoordinates);

        if (isCatchable(clickBoardCoordinates)) {
            gui.pickItem(clickBoardCoordinates);
            if(imageHand != null) {
                showSelectedItem(imageHand);
                boardGrid.getChildren().remove(imageHand);
            }
        }

    }
    public void onBookshelfClick(MouseEvent event) {
        int column = getBookshelfCellsColumn(event);
        gui.putItemList(column);
        showBookshelf(column);
        showBoard();
    }
    public void onHandClick(MouseEvent event){
        ImageView clickedImageView = (ImageView) event.getSource();
        if (clickedImageView == hand1)
            handOrder.add(0);
        else if (clickedImageView == hand2)
            handOrder.add(1);
        else if (clickedImageView == hand3)
            handOrder.add(2);
    }

    /* Show methods */
    public void showBookshelf(int column) {
        for (int i = nRowBookshelf - 1; i >= 0; i--) {
            for (int j = 0; j < nColumnBookshelf; j++) {
                Item item = localBookshelf.bookshelf[i][j];
                if (item != null) {
                    URL url = getClass().getResource(item.getImagePath());
                    if (url != null) {
                        ImageView imageView = new ImageView();
                        Image image = new Image(url.toString());
                        imageView.setImage(image);
                        imageView.setFitWidth(20);
                        imageView.setFitHeight(20);
                        bookshelfGrid.add(imageView,i,j); //TODO indici da invertire
                    }
                }
            }
        }
    }
    public void showBoard() {
        for (int i = 8; i >= 0; i--) {
            for (int j = 0; j < nColumnBoard; j++) {
                Item item = localBoard.board[i][j];
                if (item != null) {
                    URL url = getClass().getResource(item.getImagePath());
                    if (url != null) {
                        ImageView imageView = new ImageView();
                        Image image = new Image(url.toString());
                        imageView.setImage(image);
                        imageView.setFitWidth(45);
                        imageView.setFitHeight(45);
                        boardGrid.add(imageView,i,j); //TODO indici da invertire
                    }
                }
            }
        }
    }

    /* Coordinates getters */
    public Coordinates getGridCellsIndexes(MouseEvent event){
        double cellWidth = boardGrid.getWidth() / nColumnBoard;
        double cellHeight = boardGrid.getHeight() / nRowBoard;
        int clickedRow = (int) (event.getY() / cellHeight);
        int clickedCol = (int) (event.getX() / cellWidth);
        return new Coordinates(clickedRow,clickedCol);
    }
    public Coordinates getBoardCellsIndexes(MouseEvent event){
        double cellWidth = boardGrid.getWidth() / nColumnBoard;
        double cellHeight = boardGrid.getHeight() / nRowBoard;
        int clickedRow = (int) (event.getY() / cellHeight);
        int clickedCol = (int) (event.getX() / cellWidth);
        return new Coordinates(8 - clickedRow,clickedCol);
    }
    public int getBookshelfCellsColumn(MouseEvent event){
        double cellWidth = bookshelfGrid.getWidth() / nColumnBookshelf;
        int clickedCol = (int) (event.getX() / cellWidth);

        return clickedCol;
    }

    /* Updating  */
    public void updateBoard(LocalBoard updatedBoard, LocalHand hand) {
        this.localBoard = updatedBoard;
        this.localHand = hand;
        //System.out.println("Update Board done");
    }
    public void updateBookShelf(LocalBookshelf updatedBookshelf){
        this.localBookshelf = updatedBookshelf;
    }

    /* Images */
    public boolean isImageViewEmpty(ImageView imageView) {
        return imageView.getImage() == null;
    }

    public void showSelectedItem(ImageView imageview){
        if (isImageViewEmpty(hand1)){
            hand1.setImage(imageview.getImage());
        }
        else if (isImageViewEmpty(hand2)){
            hand2.setImage(imageview.getImage());
        }
        else hand3.setImage(imageview.getImage());
    }
    public ImageView getImageViewFromGridPane(GridPane gridPane, int rowIndex, int columnIndex) {
        ImageView imageView = null;

        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == rowIndex && GridPane.getColumnIndex(node) == columnIndex && node instanceof ImageView) {
                imageView = (ImageView) node;
                break;
            }
        }

        return imageView;
    }
    public String getCommonPathByType(int type) {
        return "/Images/common/" + (type + 1) + ".jpg";
    }
    public String getPersonalByType(int n) {
        return "/Images/personal/" + n + ".png";
    }

    /* Checking */
    public boolean isCatchable(Coordinates coordinates)  {
        int row = coordinates.getRow();
        int column = coordinates.getColumn();

        if (localHandCoordinates.size() > 3) {
            gameNotifications.getItems().add( "<ERROR> too many items selected!" );
            return false;
        }

        if (localBoard.board[row][column] == null) {
            gameNotifications.getItems().add("<ERROR> empty slot!");
            localHandCoordinates.remove(coordinates);
            return false;
        }

        if (notLinearCheck(coordinates))
        {
            gameNotifications.getItems().add("<ERROR> not linear pick exception!");
            localHandCoordinates.remove(coordinates);
            return false;
        }
        if (checkDistance(coordinates)){
            gameNotifications.getItems().add("<ERROR> not adjacent pick!");
            localHandCoordinates.remove(coordinates);
            return false;
        }

        else if (localBoard.board[row - 1][column] == null && !localHandCoordinates.contains(new Coordinates(row - 1, column)))
             return true;
        else if (localBoard.board[row + 1][column] == null && !localHandCoordinates.contains(new Coordinates(row + 1, column)))
            return true;
        else if (localBoard.board[row][column - 1] == null && !localHandCoordinates.contains(new Coordinates(row,column - 1)))
            return true;
        else if (localBoard.board[row][column + 1] == null && !localHandCoordinates.contains(new Coordinates(row, column + 1)))
            return true;

        return false;
        }
    private boolean checkDistance(Coordinates coordinates) {
        int row = coordinates.getRow();
        int column = coordinates.getColumn();

        if ( localHandCoordinates.size() == 2 ){
            return (Math.abs(row - localHandCoordinates.get(0).getRow()) > 1) || (Math.abs(column - localHandCoordinates.get(0).getColumn()) > 1);
        }
        else if ( localHandCoordinates.size() == 3){
        return (Math.abs(row - localHandCoordinates.get(1).getRow()) > 1) || (Math.abs(column - localHandCoordinates.get(1).getColumn()) > 1);
        }
        return false;
    }

    /*True se c'è ancora spazio nella libreria */
    public boolean noSpaceLeft(int column, int itemListSize) {
        int counterSpace = 0;
        for(int i = (nRowBookshelf - 1); i >= 0; i--) { // (nRow-1) because we count also the row 0
            if(localBookshelf.bookshelf[i][column] == null) {
                counterSpace++;
            }
        }
        return (counterSpace >= itemListSize);
    }
    public boolean notLinearCheck(Coordinates coordinates) {
        int row = coordinates.getRow();
        int column = coordinates.getColumn();
        return localHandCoordinates.contains(new Coordinates(row - 1, column - 1)) ||
                localHandCoordinates.contains(new Coordinates(row - 1, column + 1)) ||
                localHandCoordinates.contains(new Coordinates(row + 1, column - 1)) ||
                localHandCoordinates.contains(new Coordinates(row + 1, column + 1));
    }

    /* Switch Buttons */
    public void onChat(javafx.event.ActionEvent actionEvent){
        this.gui.switchStage(Command.CHAT);
    }
    public void onBookshelfs(javafx.event.ActionEvent actionEvent) {
        this.gui.switchStage(Command.SHOW_BOOKSHELFS);
    }
}



