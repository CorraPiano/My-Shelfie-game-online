package it.polimi.ingsw.client.view.GUI.controllers;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.client.view.GUI.Command;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.Item;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;;
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
    @FXML
    private GridPane arrowPane;
    private ModelView modelView;

    private LocalBookshelf localBookshelf;
    private ArrayList<Integer> handOrder = new ArrayList<>();

    private ArrayList<Image> handImages = new ArrayList<>();
    private ArrayList<Coordinates> localHandCoordinates = new ArrayList<>();
    private int handClickCount;

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
    @Override
    public GUI getGui() {
        return gui;
    }

    /* Scene initializers */
    public void init() {
        this.modelView = gui.getClient().getModelView();

        showBoard();
        initPersonal(modelView.getLocalPersonalCard().num);
        initCommon(modelView.getCommonCards());
    }
    public void initCommon(ArrayList<LocalCommonCard> commonCards) {
        if (gui.getClient().getModelView().getGameMode().equals(GameMode.EASY))
            return;
        int type1 = commonCards.get(0).getType();
        int type2 = commonCards.get(1).getType();

        URL url1 = getClass().getResource(getCommonPathByType(type1));
        if (url1 != null) {
            common1.setImage(new Image(url1.toString()));
        }

        URL url2 = getClass().getResource(getCommonPathByType(type2));
        if (url2 != null) {
            common2.setImage(new Image(url2.toString()));
        }
    }
    public void initPersonal(int num) {
        URL url = getClass().getResource(getPersonalByType(num));
        if (url != null) {
            personalGoalCard.setImage(new Image(url.toString()));
        }
    }

    /* On click methods */
    public void onBoardClicked(MouseEvent event) {
        Coordinates clickGridCoordinates = getGridCellsIndexes(event);
        Coordinates clickBoardCoordinates = getBoardCellsIndexes(event); //actual model coordinates
        localHandCoordinates.add(clickBoardCoordinates);
        //if(isCatchable(clickBoardCoordinates)) {
            gui.pickItem(clickBoardCoordinates);
        //}
    }
    public void onBookshelfClick(MouseEvent event) {
        int column = getBookshelfCellsColumn(event);
        if (handClickCount != 0 ) {
            //showArrows();
            gui.putItemList(column);
            setEffectNull();
            //removeArrows();
        }
    }
    public void onHandClick(MouseEvent event) {
        handClickCount++;
        ImageView clickedImageView = (ImageView) event.getSource();
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.YELLOW);
        dropShadow.setWidth(20);
        dropShadow.setHeight(20);
        clickedImageView.setEffect(dropShadow);

        if (clickedImageView.equals(hand1))
            handOrder.add(0);
        else if (clickedImageView.equals(hand2))
            handOrder.add(1);
        else if (clickedImageView.equals(hand3))
            handOrder.add(2);

    }

    /* Show methods */
    public void showBookshelf() {
        resetBookshelf();
        String name = gui.getClient().getName();
        LocalBookshelf localBookshelf = modelView.getLocalBookshelfs().get(name);
        for (int i = 0; i < nRowBookshelf; i++) {
            for (int j = 0; j < nColumnBookshelf; j++) {
                Item item = localBookshelf.bookshelf[i][j];
                if (item != null) {
                    URL url = getClass().getResource(item.getImagePath());
                    if (url != null) {
                        ImageView imageView = new ImageView();
                        Image image = new Image(url.toString());
                        imageView.setImage(image);
                        imageView.setFitWidth(35.5);
                        imageView.setFitHeight(35);
                        bookshelfGrid.add(imageView, j, nRowBookshelf - 1 - i); //inserisci colonna, riga
                        //bisogna prima leggere correttamente la matrice, poi stamparla al contrario
                    }
                }
            }
        }
    }
    public void showBoard() {
        LocalBoard localBoard = modelView.getLocalBoard();
        resetBoard();


        for (int i = 0; i < nRowBoard; i++) {
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
                            boardGrid.add(imageView, j, nRowBoard-1-i); //inserisci colonna, riga
                            //bisogna prima leggere correttamente la matrice, poi stamparla al contrario
                        }
                    }
                }
        }
    }
    public void showHand() {
        removeHandImages();
        handClickCount = 0;
        LocalHand localHand = modelView.getLocalHand();
        if (localHand.size > 0){
            URL url = getClass().getResource(localHand.hand[0].getImagePath());
            if (url != null) {
                Image image = new Image(url.toString());
                hand1.setImage(image);
            }
        }
        if (localHand.size > 1){
            URL url = getClass().getResource(localHand.hand[1].getImagePath());
            if (url != null) {
                Image image = new Image(url.toString());
                hand2.setImage(image);
            }
        }
        if (localHand.size > 2){
            URL url = getClass().getResource(localHand.hand[2].getImagePath());
            if (url != null) {
                Image image = new Image(url.toString());
                hand3.setImage(image);
            }
        }


    }

    /* Coordinates getters */
    public Coordinates getGridCellsIndexes(MouseEvent event) {
        double cellWidth = boardGrid.getWidth() / nColumnBoard;
        double cellHeight = boardGrid.getHeight() / nRowBoard;
        int clickedRow = (int) (event.getY() / cellHeight);
        int clickedCol = (int) (event.getX() / cellWidth);
        return new Coordinates(clickedRow, clickedCol);
    }
    public Coordinates getBoardCellsIndexes(MouseEvent event) {
        double cellWidth = boardGrid.getWidth() / nColumnBoard;
        double cellHeight = boardGrid.getHeight() / nRowBoard;
        int clickedRow = (int) (event.getY() / cellHeight);
        int clickedCol = (int) (event.getX() / cellWidth);
        return new Coordinates(8 - clickedRow, clickedCol);
    }
    public int getBookshelfCellsColumn(MouseEvent event) {
        double cellWidth = bookshelfGrid.getWidth() / nColumnBookshelf;
        int clickedCol = (int) (event.getX() / cellWidth);

        return clickedCol;
    }

    /* Updating  */
    public void resetBoard(){
        for (Node node : boardGrid.getChildren()) {
            ((ImageView) node).setImage(null);
        }
    }
    private void resetBookshelf() {
        for (Node node : bookshelfGrid.getChildren()) {
            if(node instanceof ImageView)
                ((ImageView) node).setImage(null);
        }
    }
    public void removeHandImages(){
        hand1.setImage(null);
        hand2.setImage(null);
        hand3.setImage(null);
    }
    public void setTurn(String name){
        if(! name.equals(gui.getClient().getName())){
            bookshelfGrid.setDisable(true);
            boardGrid.setDisable(true);
        }
        else if(name.equals(gui.getClient().getName())){
            bookshelfGrid.setDisable(false);
            boardGrid.setDisable(false);
        }

    }

    /* Images */
    public boolean isImageViewEmpty(ImageView imageView) {
        return imageView.getImage() == null;
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
        boolean catchable = false;
        LocalBoard localBoard = modelView.getLocalBoard();

        if (localHandCoordinates.size() > 3) {
            gameNotifications.getItems().add( "<ERROR> too many items selected!" );
            localHandCoordinates.remove(coordinates);
            return false;
        }
        if ((row < 0 || row > 8) || (column < 0 || column > 8)) {
            gameNotifications.getItems().add("<ERROR> out of Board slot!");
            localHandCoordinates.remove(coordinates);
            return false;

        }
        if (localBoard.board[row][column] == null) {
            gameNotifications.getItems().add("<ERROR> empty slot!");
            localHandCoordinates.remove(coordinates);
            return false;
        }

        else if (localBoard.board[row - 1][column] == null && !localHandCoordinates.contains(new Coordinates(row - 1, column))) {
            catchable = true;
        }
        else if (localBoard.board[row + 1][column] == null && !localHandCoordinates.contains(new Coordinates(row + 1, column))) {
            catchable = true;
        }
        else if (localBoard.board[row][column - 1] == null && !localHandCoordinates.contains(new Coordinates(row,column - 1))) {
            catchable = true;
        }
        else if (localBoard.board[row][column + 1] == null && !localHandCoordinates.contains(new Coordinates(row, column + 1))) {
            catchable = true;
        }

        if (catchable)
            return checkNewCoordinates(coordinates);

        gameNotifications.getItems().add("<ERROR> not Pickable!");
        return false;
        }
    public boolean checkNewCoordinates (Coordinates coordinates) {
        if (localHandCoordinates.isEmpty())
            return true;

        boolean checkNear = false;
        boolean checkSameColumn = true;
        boolean checkSameRow = true;
        int row = coordinates.getRow();
        int column = coordinates.getColumn();

        for (Coordinates c : localHandCoordinates) {
            if (c.equals(coordinates))
                return false;
            if (c.getRow() != row)
                checkSameRow = false;
            if (c.getColumn() != column)
                checkSameColumn = false;
            if (c.getColumn() == column + 1 || c.getColumn() == column - 1 || c.getRow() == row + 1 || c.getRow() == row - 1)
                checkNear = true;
            if (!(checkSameRow || checkSameColumn))
                break;
        }
        return checkNear && (checkSameRow || checkSameColumn);
    }
    /* True se c'è ancora spazio nella colonna */
    public boolean checkFreeColumn(int column) {
        int counterSpace = 0;
        LocalBookshelf localBookshelf = new LocalBookshelf(gui.getClient().getName(),null);
        for(int i = (nRowBookshelf - 1); i >= 0; i--) {
            if(localBookshelf.bookshelf[i][column] == null) {
                counterSpace++;
            }
        }
        return counterSpace > 0;
    }

    /* Switch Buttons */
    public void onChat(javafx.event.ActionEvent actionEvent){
        this.gui.switchStage(Command.CHAT);
    }
    public void onBookshelfs(javafx.event.ActionEvent actionEvent) {
        this.gui.switchStage(Command.SHOW_BOOKSHELFS);
    }

    /* Other features */
    public void setEffectNull(){
        hand1.setEffect(null);
        hand2.setEffect(null);
        hand3.setEffect(null);
    }
    public void showArrows(){
        for (int j = 0; j < nColumnBookshelf; j++){
                Image arrowImage = new Image("/Images/misc/arrow.png");
                ImageView arrowImageView = new ImageView(arrowImage);
                int columnIndex = j;
                int rowIndex = 0;
                arrowPane.add(arrowImageView, columnIndex, rowIndex);
                System.out.println("Eseguito");
        }

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
}



