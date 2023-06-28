package it.polimi.ingsw.client.view.GUI.controllers;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.client.view.GUI.AlertBox;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.client.view.utils.NotificationsType;
import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.Item;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static it.polimi.ingsw.client.view.utils.NotificationsType.*;
import static it.polimi.ingsw.util.Constants.*;

public class GameController implements GUIController {
    private GUI gui;
    @FXML
    private AnchorPane anchorPane;

    //BOARD
    @FXML
    private ImageView board;
    @FXML
    private GridPane boardGrid;


    //PERSONAL BOOKSHELF
    @FXML
    private ImageView bookshelf;
    @FXML
    private GridPane bookshelfGrid;

    //HAND
    @FXML
    private ImageView hand1;
    @FXML
    private ImageView hand2;
    @FXML
    private ImageView hand3;
    @FXML
    private Button undoPick;

    //PERSONAL AND COMMON
    @FXML
    private ImageView common1;
    @FXML
    private Text commonDescription1;
    @FXML
    private Pane commonPane1;
    @FXML
    private ImageView common2;
    @FXML
    private Text commonDescription2;
    @FXML
    private Pane commonPane2;
    @FXML
    private ImageView personalGoalCard;

    //PERSONAL TOKEN AND THOSE OF OTHERS
    @FXML
    private ImageView token1;
    @FXML
    private ImageView token2;
    @FXML
    private ImageView bookshelf1token1;
    @FXML
    private ImageView bookshelf1token2;
    @FXML
    private ImageView bookshelf2token1;
    @FXML
    private ImageView bookshelf2token2;
    @FXML
    private ImageView bookshelf3token1;
    @FXML
    private ImageView bookshelf3token2;
    @FXML
    private ImageView common1token;
    @FXML
    private ImageView common2token;
    @FXML
    private ImageView boardtoken1;
    @FXML
    private ImageView bookshelf1endGameToken;
    @FXML
    private ImageView bookshelf2endGameToken;
    @FXML
    private ImageView bookshelf3endGameToken;
    @FXML
    private ImageView endGameToken;



    //OTHER
    @FXML
    private GridPane arrowGrid;
    @FXML
    private ImageView firstPlayerSeat;

    //NOTIFCATIONS
    @FXML
    private ListView<String> gameNotifications;
    private int notificationsLenght = 0;
    @FXML
    private TableView tableView;

    //CHAT
    @FXML
    private Button sendButton;
    @FXML
    private TextField chatMessage;
    @FXML
    private TextField receiver;
    @FXML
    private ListView<String> chatField;
    private int chatLenght = 0;

    //BOOKSHELF OF OTHER PLAYERS
    @FXML
    private ImageView bookshelf1;
    @FXML
    private GridPane bookshelf1Grid;
    @FXML
    private Text bookshelf1name;
    @FXML
    private ImageView bookshelf2;
    @FXML
    private GridPane bookshelf2Grid;
    @FXML
    private Text bookshelf2name;
    @FXML
    private ImageView bookshelf3;
    @FXML
    private GridPane bookshelf3Grid;
    @FXML
    private Text bookshelf3name;
    @FXML
    private Button orderConfirm;
    @FXML
    private ImageView arrow1;
    @FXML
    private ImageView arrow2;
    @FXML
    private ImageView arrow3;
    @FXML
    private ImageView arrow4;
    @FXML
    private ImageView arrow5;

    private ModelView modelView;

    //private LocalBookshelf localBookshelf;
    private ArrayList<Integer> handOrder = new ArrayList<>();

    private ArrayList<Image> handImages = new ArrayList<>();
    private int imagesHighlighted;
    private boolean HandVisible;

    //METHODS

    /**
     * Sets the GUI object associated with the controller.
     *
     * @param gui The GUI object to be associated with the controller.
     */
    /**
     * Quits the game and exits the lobby.
     */
    @FXML
    public void onLeaveButton() {
        resetGame();
        this.gui.leaveGame();
    }


    public void resetGame(){
        //reset bookshelfs
        resetPlayersBookshelfs(bookshelfGrid);
        resetPlayersBookshelfs(bookshelf1Grid);
        resetPlayersBookshelfs(bookshelf2Grid);
        bookshelf2.setImage(null);
        resetPlayersBookshelfs(bookshelf3Grid);
        bookshelf3.setImage(null);
        bookshelf1name.setText("");
        bookshelf2name.setText("");
        bookshelf3name.setText("");
        //reset tabView, notifications anche chat
        tableView.getColumns().clear();
        gameNotifications.getItems().clear();
        chatField.getItems().clear();
        receiver.clear();
        //reset common
        common1.setImage(null);
        common1token.setImage(null);
        commonDescription1.setText("");
        commonPane1.setStyle(null);
        common2.setImage(null);
        common2token.setImage(null);
        commonDescription2.setText("");
        commonPane2.setStyle(null);
        //reset tokens
        endGameToken.setImage(null);
        bookshelf1endGameToken.setImage(null);
        bookshelf2token1.setImage(null);
        bookshelf2token2.setImage(null);
        bookshelf2endGameToken.setImage(null);
        bookshelf3token1.setImage(null);
        bookshelf3token2.setImage(null);
        bookshelf3endGameToken.setImage(null);
    }

    /* SCENE INITIALIZERS */
    /**
     * Initializes the game scene.
     */
    public void init() {
        anchorPane.setOnMouseClicked((event) -> this.blockPane());
        this.modelView = gui.getClient().getModelView();
        resetGame();

        showBoard();

        initPersonal(modelView.getLocalPersonalCard().num);
        initCommon(modelView.getCommonCards());

        initBookshelfs(modelView.getLocalBookshelfs());
        showAllBookshelf();

        initTableView();
        showTableView();

        initTokens();
        showTokens();

        setArrows();
        showHand();

       /* gui.updateBoard();
        gui.updateTableView();
        gui.updateHand();
        gui.updateAllBookshelfs();*/
        showFirstPlayerSeat();
    }

    private void blockPane(){
        if(gui.imDisconnected){
            int status = AlertBox.errorData(gui.getPrimaryStage(), "Error", "Disconnected");
        }
    }

    private void initCommon(ArrayList<LocalCommonCard> commonCards) {
        if (gui.getClient().getModelView().getGameMode().equals(GameMode.EASY))
            return;
        int type1 = commonCards.get(0).getType();
        int type2 = commonCards.get(1).getType();

        String style =
                "-fx-background-color: #B77C52;" +
                "-fx-border-color: rgb(60, 40, 14);" +
                "-fx-border-width: 3px;" +
                "-fx-border-radius: 20px;" +
                "-fx-background-radius: 20px;";

        URL url1 = getClass().getResource(getCommonPathByType(type1));
        if (url1 != null) {
            common1.setImage(new Image(url1.toString()));
            common1.getStyleClass().add("imageView");
            commonPane1.setStyle(style);
            commonDescription1.setText(this.gui.getSceneHandler().getCommonDescription(type1));
        }

        URL url2 = getClass().getResource(getCommonPathByType(type2));
        if (url2 != null) {
            common2.setImage(new Image(url2.toString()));
            common2.getStyleClass().add("imageView");
            commonPane2.setStyle(style);
            commonDescription2.setText(this.gui.getSceneHandler().getCommonDescription(type2));
        }
    }
    private void initPersonal(int num) {
        URL url = getClass().getResource(getPersonalByType(num));
        if (url != null) {
            personalGoalCard.setImage(new Image(url.toString()));
            personalGoalCard.getStyleClass().add("imageView");
        }
    }
    private void initBookshelfs(Map<String, LocalBookshelf> mappa){
        URL url = getClass().getResource("/Images/boards/bookshelf_orth.png");
        if (url != null) {
            if(mappa.size() >= 2){
                bookshelf1.setImage(new Image(url.toString()));
                if(mappa.size() >= 3){
                    bookshelf2.setImage(new Image(url.toString()));
                    if(mappa.size() >= 4){
                        bookshelf3.setImage(new Image(url.toString()));
                    }
                }
            }
        }
    }
    private void initTableView(){

        //da aggiungere un numero che identifichi la sequenza di gioco?!

        modelView.getLocalPlayerList().get(0);

        TableColumn<LocalPlayer, String> nameColumn = new TableColumn<>("Name");
        TableColumn<LocalPlayer, Integer> pointsColumn = new TableColumn<>("Points");
        TableColumn<LocalPlayer, Enum> statusColumn = new TableColumn<>("Status");

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(pointsColumn);
        tableView.getColumns().add(statusColumn);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("playerState"));

        ObservableList<LocalPlayer> data = FXCollections.observableArrayList();
        data.addAll(modelView.getLocalPlayerList());
        tableView.setItems(data);

        updateCurrentPlayer(gui.getClient().getName());
    }
    private void initTokens() {
        URL url_empty = getClass().getResource("/Images/scoring_tokens/scoring_back_EMPTY.jpg");
        URL url_end = getClass().getResource("/Images/scoring_tokens/end_game.jpg");
        int numPlayer = modelView.getLocalPlayerList().size();
        boardtoken1.setImage(new Image(url_end.toString()));
        if(numPlayer>=2){
            token1.setImage(new Image(url_empty.toString()));
            token2.setImage(new Image(url_empty.toString()));
            bookshelf1token1.setImage(new Image(url_empty.toString()));
            bookshelf1token2.setImage(new Image(url_empty.toString()));
            if(numPlayer>=3){
                bookshelf2token1.setImage(new Image(url_empty.toString()));
                bookshelf2token2.setImage(new Image(url_empty.toString()));
                if(numPlayer==4){
                    bookshelf3token1.setImage(new Image(url_empty.toString()));
                    bookshelf3token2.setImage(new Image(url_empty.toString()));
                }
            }
        }
    }


    // URL url_empty = getClass().getResource("/Images/scoring_tokens/scoring_back_EMPTY.jpg");

    /* ON CLICK METHODS */

    /**
     * Retrieves the row and column indices of the board cells based on the provided mouse event.
     *
     * @param event The mouse event that triggered the method.
     * @return The coordinates of the clicked board cell (row, column).
     */
    public Coordinates getBoardCellsIndexes(MouseEvent event) {
        double cellWidth = boardGrid.getWidth() / nColumnBoard;
        double cellHeight = boardGrid.getHeight() / nRowBoard;
        int clickedRow = (int) (event.getY() / cellHeight);
        int clickedCol = (int) (event.getX() / cellWidth);
        //System.out.println("getBoardCellsIndex ritorna:\n riga --> " + (8-clickedRow) + "\n colonna --> " + clickedCol + "\n");
        return new Coordinates(8 - clickedRow, clickedCol);
    }

    /**
     * Handles the click event on the game board.
     *
     * @param event The mouse event.
     */
    public void onBoardClicked(MouseEvent event) {
        //Coordinates clickGridCoordinates = getGridCellsIndexes(event);
        Coordinates clickBoardCoordinates = getBoardCellsIndexes(event); //actual model coordinates
        if(isCatchable(clickBoardCoordinates)) {
            gui.pickItem(clickBoardCoordinates);
        }
    }

    /**
     * Handles the click event on the arrow images.
     *
     * @param event The mouse event.
     */
    public void onArrowClicked(MouseEvent event){
        ImageView clickedArrow = (ImageView) event.getSource();
        int column = 0;
        if (clickedArrow.equals(arrow1)) column = 0;
        else if (clickedArrow.equals(arrow2)) column = 1;
        else if (clickedArrow.equals(arrow3)) column = 2;
        else if (clickedArrow.equals(arrow4)) column = 3;
        else if (clickedArrow.equals(arrow5)) column = 4;

        gui.putItemList(column);
        setArrowsInvisible();
        setEffectNull();
    }

    /**
     * Handles the click event on the hand items.
     *
     * @param event The mouse event.
     */
    public void onHandClick(MouseEvent event) {
        if (!HandVisible)
            return;
        ImageView clickedImageView = (ImageView) event.getSource();
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.YELLOW);
        dropShadow.setWidth(20);
        dropShadow.setHeight(20);
        clickedImageView.setEffect(dropShadow);
        imagesHighlighted++;

        if (clickedImageView.equals(hand1)) {
            handOrder.add(0);
            //System.out.println("--> aggiunto l'intero 0 in handOrder");
        } else if (clickedImageView.equals(hand2)) {
            handOrder.add(1);
            //System.out.println("--> aggiunto l'intero 1 in handOrder");
        } else if (clickedImageView.equals(hand3)){
            handOrder.add(2);
            //System.out.println("--> aggiunto l'intero 2 in handOrder");
        }

    }

    /**
     * Handles the click event on the "tick" button to confirm the card order.
     */
    public void onTickClick() {
        if(!HandVisible)
            return;

        int handSize = modelView.getLocalHand().coordinatesList.size();

        if (imagesHighlighted == handOrder.size() && (handOrder.size() == handSize) && handOrder.size() != 0) {
            gui.selectInsertOrder(handOrder);
            handOrder.clear();
            showArrows(handSize);
        }
    }

    /**
     * Handles the click event on the "Undo" button to undo the card order.
     */
    public void onOrderUndo(){
        if(!HandVisible)
            return;
        setEffectNull();
        handOrder.clear();
        imagesHighlighted = 0;
    }

    /**
     * Handles the click event on the "Undo" button to undo the card pick.
     */
    public void onUndoButton(){
        if(!HandVisible)
            return;
        gui.undoPick();
        setEffectNull();
        imagesHighlighted = 0;
        handOrder.clear();
        showBoard();
        showHand();
    }



    /* DISPLAY METHODS */

    /**
     * Shows the FirstPlayerSeat to the player who'll start the game
     */
    public void showFirstPlayerSeat(){
        for(LocalPlayer p : modelView.getLocalPlayerList()){
            if(Objects.equals(gui.getClient().getName(), p.name) && p.firstPlayerSeat){
                URL url_firstPlayerSeat = getClass().getResource("/Images/misc/firstplayertoken.png");
                firstPlayerSeat.setImage(new Image(url_firstPlayerSeat.toString()));
            }
        }
    }

    /**
     * Shows the bookshelf of the local player on the game screen.
     * Resets the bookshelf grid and populates it with the items from the player's bookshelf.
     * Each item is represented by an image on the grid.
     */
    private void showYourBookshelf() {
        resetBookshelf();
        String name = gui.getClient().getName();
        LocalBookshelf localBookshelf = modelView.getLocalBookshelfs().get(name);
        for (int i = 0; i < nRowBookshelf; i++) {
            for (int j = 0; j < nColumnBookshelf; j++) {
                Item item = localBookshelf.bookshelf[i][j];
                if (item != null) {
                    ImageView imageView = new ImageView();
                    Image image = gui.getSceneHandler().getImage(item.getImagePath());
                    imageView.setImage(image);
                    imageView.setFitWidth(35.5);
                    imageView.setFitHeight(35);
                    bookshelfGrid.add(imageView, j,  i); //inserisci colonna, riga
                    //bisogna prima leggere correttamente la matrice, poi stamparla al contrario
                }
            }
        }
    }

    public void showbookshelf(LocalBookshelf localBookshelf){
        if(modelView.getLocalName().equals(localBookshelf.name))
            showYourBookshelf();
        else
            showOtherBookshelfs(localBookshelf);
    }
    public void showAllBookshelf(){
        showYourBookshelf();
        Map<String,LocalBookshelf> map = modelView.getLocalBookshelfs();
        for(String s: modelView.getLocalBookshelfs().keySet()){
            showOtherBookshelfs(map.get(s));
        }
    }

    /**
     * Shows the bookshelves of other players on the game screen.
     * Resets the specified player's bookshelf grid and populates it with the items from their bookshelf.
     * Each item is represented by an image on the grid.
     *
     * @param localBookshelfs      The map of local bookshelves, where the key is the player name and the value is their bookshelf.
     */
    private void showOtherBookshelfs(LocalBookshelf localBookshelfs) {
        int index = 1;
        GridPane gridPane = new GridPane();
        Text text = new Text();

        String nameClientPlayer = gui.getClient().getName();
        String nameBookshelfPlayer = localBookshelfs.name;

        for(String s : modelView.getLocalBookshelfs().keySet()) {
            if (!s.equals(nameClientPlayer)) {
                switch (index) {
                    case 1 -> {
                        gridPane = bookshelf1Grid;
                        text = bookshelf1name;
                    }
                    case 2 -> {
                        gridPane = bookshelf2Grid;
                        text = bookshelf2name;
                    }
                    case 3 -> {
                        gridPane = bookshelf3Grid;
                        text = bookshelf3name;
                    }
                }
                if (s.equals(nameBookshelfPlayer))
                    break;
                index++;
            }
        }
        text.setText(nameBookshelfPlayer);
        resetPlayersBookshelfs(gridPane);
        for (int i=0; i<nRowBookshelf; i++){
            for(int j=0; j<nColumnBookshelf; j++){
                Item item = localBookshelfs.bookshelf[i][j];
                if(item != null){
                    URL url = getClass().getResource(item.getImagePath());
                    if(url != null){
                        ImageView imageView = new ImageView();
                        Image image = gui.getSceneHandler().getImage(item.getImagePath());
                        imageView.setImage(image);
                        imageView.setFitWidth(23.5);
                        imageView.setFitHeight(23.6);
                        gridPane.add(imageView, j, i);
                    }
                }
            }
        }
    }

    /**
     * Shows the game board on the game screen.
     * Resets the board grid and populates it with the items from the game board.
     * Each item is represented by an image on the grid.
     */
    public void showBoard() {
        LocalBoard localBoard = modelView.getLocalBoard();
        resetBoard();

        for (int i = 0; i < nRowBoard; i++) {
                for (int j = 0; j < nColumnBoard; j++) {
                    Item item = localBoard.board[i][j];
                    if (item != null) {
                        ImageView imageView = new ImageView();
                        Image image = gui.getSceneHandler().getImage(item.getImagePath());
                        imageView.setImage(image);
                        imageView.setFitWidth(45);
                        imageView.setFitHeight(45);
                        boardGrid.add(imageView, j, nRowBoard - 1 - i); //inserisci colonna, riga

                        /*URL url = getClass().getResource(item.getImagePath());
                        if (url != null) {
                            ImageView imageView = new ImageView();
                            Image image = new Image(url.toString());
                            imageView.setImage(image);
                            imageView.setFitWidth(45);
                            imageView.setFitHeight(45);
                            boardGrid.add(imageView, j, nRowBoard-1-i); //inserisci colonna, riga
                        }*/
                    }
                }
        }
    }

    /**
     * Shows the hand of the player on the game screen.
     * Removes any existing hand images and populates the hand slots with the items from the local player's hand.
     * Each item is represented by an image in the corresponding hand slot.
     */
    public void showHand() {
        removeHandImages();
        //handClickCount = 0;
        LocalHand localHand = modelView.getLocalHand();

        if (true) {
            if (localHand.size > 0) {
                URL url = getClass().getResource(localHand.hand[0].getImagePath());
                if (url != null) {
                    Image image = new Image(url.toString());
                    hand1.setImage(image);
                }
            }
            if (localHand.size > 1) {
                URL url = getClass().getResource(localHand.hand[1].getImagePath());
                if (url != null) {
                    Image image = new Image(url.toString());
                    hand2.setImage(image);
                }
            }
            if (localHand.size > 2) {
                URL url = getClass().getResource(localHand.hand[2].getImagePath());
                if (url != null) {
                    Image image = new Image(url.toString());
                    hand3.setImage(image);
                }
            }
        }
    }

    /**
     * Shows the arrow buttons on the game screen based on the current hand size.
     * Determines whether there is space left in each hand slot and sets the visibility of the arrow buttons accordingly.
     *
     * @param handSize The current size of the hand.
     */
    public void showArrows(int handSize){
        if (noSpaceLeft(0,handSize)){ arrow1.setVisible(true); }
        if (noSpaceLeft(1,handSize)){ arrow2.setVisible(true); }
        if (noSpaceLeft(2,handSize)){ arrow3.setVisible(true); }
        if (noSpaceLeft(3,handSize)){ arrow4.setVisible(true); }
        if (noSpaceLeft(4,handSize)){ arrow5.setVisible(true); }
    }

    /**
     * Shows the scoring tokens on the game screen.
     * Sets the images of the common scoring tokens and each player's scoring tokens based on the game model data.
     */
    public void showTokens() {
        //URL url_2 = getClass().getResource("/Images/scoring_tokens/scoring_2.jpg");
        //URL url_4 = getClass().getResource("/Images/scoring_tokens/scoring_4.jpg");
        //URL url_6 = getClass().getResource("/Images/scoring_tokens/scoring_6.jpg");
        //URL url_8 = getClass().getResource("/Images/scoring_tokens/scoring_8.jpg");

        //c'è un problema, quando viene preso il secondo token dalla common da "Index 0 out of bounds for length 0"

        switch (modelView.getCommonCards().get(0).tokenList.get(0).getValue()){
            case 2 -> common1token.setImage(this.gui.getSceneHandler().getToken(0));
            case 4 -> common1token.setImage(this.gui.getSceneHandler().getToken(1));
            case 6 -> common1token.setImage(this.gui.getSceneHandler().getToken(2));
            case 8 -> common1token.setImage(this.gui.getSceneHandler().getToken(3));
        }
        switch (modelView.getCommonCards().get(1).tokenList.get(0).getValue()){
            case 2 -> common2token.setImage(this.gui.getSceneHandler().getToken(0));
            case 4 -> common2token.setImage(this.gui.getSceneHandler().getToken(1));
            case 6 -> common2token.setImage(this.gui.getSceneHandler().getToken(2));
            case 8 -> common2token.setImage(this.gui.getSceneHandler().getToken(3));
        }

        int numPlayer = modelView.getLocalPlayerList().size();
        for(LocalPlayer p : modelView.getLocalPlayerList()){
            if(p.token1 != null){
                if(Objects.equals(p.name, gui.getClient().getName())){
                    switch (p.token1.getValue()){
                        case 2 -> token1.setImage(this.gui.getSceneHandler().getToken(0));
                        case 4 -> token1.setImage(this.gui.getSceneHandler().getToken(1));
                        case 6 -> token1.setImage(this.gui.getSceneHandler().getToken(2));
                        case 8 -> token1.setImage(this.gui.getSceneHandler().getToken(3));
                    }
                }
                else if(numPlayer>=2 && Objects.equals(p.name, bookshelf1name.getText())){
                    switch (p.token1.getValue()){
                        case 2 -> bookshelf1token1.setImage(this.gui.getSceneHandler().getToken(0));
                        case 4 -> bookshelf1token1.setImage(this.gui.getSceneHandler().getToken(1));
                        case 6 -> bookshelf1token1.setImage(this.gui.getSceneHandler().getToken(2));
                        case 8 -> bookshelf1token1.setImage(this.gui.getSceneHandler().getToken(3));
                    }
                }
                else if(numPlayer>=3 && Objects.equals(p.name, bookshelf2name.getText())){
                    switch (p.token1.getValue()){
                        case 2 -> bookshelf2token1.setImage(this.gui.getSceneHandler().getToken(0));
                        case 4 -> bookshelf2token1.setImage(this.gui.getSceneHandler().getToken(1));
                        case 6 -> bookshelf2token1.setImage(this.gui.getSceneHandler().getToken(2));
                        case 8 -> bookshelf2token1.setImage(this.gui.getSceneHandler().getToken(3));
                    }
                }
                else if(numPlayer==4 && Objects.equals(p.name, bookshelf3name.getText())){
                    switch (p.token1.getValue()){
                        case 2 -> bookshelf3token1.setImage(this.gui.getSceneHandler().getToken(0));
                        case 4 -> bookshelf3token1.setImage(this.gui.getSceneHandler().getToken(1));
                        case 6 -> bookshelf3token1.setImage(this.gui.getSceneHandler().getToken(2));
                        case 8 -> bookshelf3token1.setImage(this.gui.getSceneHandler().getToken(3));
                    }
                }
            }
            if(p.token2 != null){
                if(Objects.equals(p.name, gui.getClient().getName())){
                    switch (p.token2.getValue()){
                        case 2 -> token2.setImage(this.gui.getSceneHandler().getToken(0));
                        case 4 -> token2.setImage(this.gui.getSceneHandler().getToken(1));
                        case 6 -> token2.setImage(this.gui.getSceneHandler().getToken(2));
                        case 8 -> token2.setImage(this.gui.getSceneHandler().getToken(3));
                    }
                }
                else if(numPlayer>=2 && Objects.equals(p.name, bookshelf1name.getText())){
                    switch (p.token2.getValue()){
                        case 2 -> bookshelf1token2.setImage(this.gui.getSceneHandler().getToken(0));
                        case 4 -> bookshelf1token2.setImage(this.gui.getSceneHandler().getToken(1));
                        case 6 -> bookshelf1token2.setImage(this.gui.getSceneHandler().getToken(2));
                        case 8 -> bookshelf1token2.setImage(this.gui.getSceneHandler().getToken(3));
                    }
                }
                else if(numPlayer>=3 && Objects.equals(p.name, bookshelf2name.getText())){
                    switch (p.token2.getValue()){
                        case 2 -> bookshelf2token2.setImage(this.gui.getSceneHandler().getToken(0));
                        case 4 -> bookshelf2token2.setImage(this.gui.getSceneHandler().getToken(1));
                        case 6 -> bookshelf2token2.setImage(this.gui.getSceneHandler().getToken(2));
                        case 8 -> bookshelf2token2.setImage(this.gui.getSceneHandler().getToken(3));
                    }
                }
                else if(numPlayer==4 && Objects.equals(p.name, bookshelf3name.getText())){
                    switch (p.token2.getValue()){
                        case 2 -> bookshelf3token2.setImage(this.gui.getSceneHandler().getToken(0));
                        case 4 -> bookshelf3token2.setImage(this.gui.getSceneHandler().getToken(1));
                        case 6 -> bookshelf3token2.setImage(this.gui.getSceneHandler().getToken(2));
                        case 8 -> bookshelf3token2.setImage(this.gui.getSceneHandler().getToken(3));
                    }
                }
            }
        }
    }

    /**
     * Shows the end game token on the game screen.
     * Sets the image of the end game token for the local player and each player's bookshelf based on the game model data.
     */
    public void showEndGameToken() {
        URL url_end = getClass().getResource("/Images/scoring_tokens/end_game.jpg");
        boardtoken1.setImage(null);
        int numPlayer = modelView.getLocalPlayerList().size();
        for(LocalPlayer p : modelView.getLocalPlayerList()){
            if(Objects.equals(p.name, gui.getClient().getName()) && p.endGameToke != null){
                endGameToken.setImage(new Image(url_end.toString()));
            }
            else if(numPlayer>=2 && Objects.equals(p.name, bookshelf1name.getText()) && p.endGameToke != null){
                bookshelf1endGameToken.setImage(new Image(url_end.toString()));
            }
            else if(numPlayer>=3 && Objects.equals(p.name, bookshelf2name.getText()) && p.endGameToke != null){
                bookshelf2endGameToken.setImage(new Image(url_end.toString()));
            }
            else if(numPlayer==4 && Objects.equals(p.name, bookshelf3name.getText()) && p.endGameToke != null){
                bookshelf3endGameToken.setImage(new Image(url_end.toString()));
            }
        }
    }

    /* UPDATING  */

    /**
     * Resets the images of the board grid by setting them to null.
     */
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
    private void resetPlayersBookshelfs(GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if(node instanceof ImageView)
                ((ImageView) node).setImage(null);
        }
    }

    /**
     * Removes the images from the hand.
     */
    public void removeHandImages(){
        hand1.setImage(null);
        hand2.setImage(null);
        hand3.setImage(null);
    }

    /**
     * Sets the turn for the specified player by enabling/disabling grids and updating visibility.
     *
     * @param name The name of the player whose turn it is.
     */
    public void setTurn(String name){

        updateCurrentPlayer(name);

        if(! name.equals(gui.getClient().getName())){
            bookshelfGrid.setDisable(true);
            boardGrid.setDisable(true);
            HandVisible = false;
        }
        else if(name.equals(gui.getClient().getName())){
            bookshelfGrid.setDisable(false);
            boardGrid.setDisable(false);
            HandVisible = true;
            imagesHighlighted = 0;
        }

    }

    private void updateCurrentPlayer(String name) {
        tableView.setRowFactory(new Callback<TableView<LocalPlayer>, TableRow<LocalPlayer>>() {
            @Override
            public TableRow<LocalPlayer> call(TableView<LocalPlayer> tableView) {
                return new TableRow<LocalPlayer>() {
                    @Override
                    protected void updateItem(LocalPlayer p, boolean empty) {
                        super.updateItem(p, empty);
                        if (!empty && Objects.equals(p.getName(), name)) {
                            getStyleClass().add("highlighted-row1");
                        } else {
                            getStyleClass().add("highlighted-row2");
                        }
                    }
                };
            }
        });
    }

    /* IMAGES HANDLING */

    /**
     * Checks if the specified ImageView is empty.
     *
     * @param imageView The ImageView to check.
     * @return {@code true} if the ImageView is empty (contains no image), {@code false} otherwise.
     */
    public boolean isImageViewEmpty(ImageView imageView) {
        return imageView.getImage() == null;
    }

    /**
     * Retrieves the common goal card file path based on the specified type.
     *
     * @param type The type of the common image.
     * @return The file path for the common image.
     */
    public String getCommonPathByType(int type) {
        return "/Images/common/" + (type) + ".jpg";
    }

    /**
     * Retrieves the personal file path based on the specified number.
     *
     * @param n The number of the personal image.
     * @return The file path for the personal image.
     */
    public String getPersonalByType(int n) {
        return "/Images/personal/" + n + ".png";
    }

    /* CHECKING */

    /**
     * Checks if the specified coordinates can be caught.
     *
     * @param coordinates The coordinates to check.
     * @return {@code true} if the coordinates are catchable, {@code false} otherwise.
     */
    public boolean isCatchable(Coordinates coordinates)  {
        int row = coordinates.getRow();
        int column = coordinates.getColumn();
        LocalBoard localBoard = modelView.getLocalBoard();
        LocalHand localHand = modelView.getLocalHand();

        if (localHand.coordinatesList.size() > 3) {
            showLocalNotification(TOOMANYITEMS);
            return false;
        }
        if ((row < 0 || row > 8) || (column < 0 || column > 8)) {
            showLocalNotification(OUTOFBOARD);
            return false;
        }
        if (localBoard.board[row][column] == null) {
            showLocalNotification(EMPTYSLOT);
            return false;
        }

        if (row == 0 || row == 8 || column == 0 || column == 8){
            return checkNewCoordinates(coordinates);
        }
        if (localBoard.board[row - 1][column] == null && !localHand.coordinatesList.contains(new Coordinates(row - 1, column))) {
            return checkNewCoordinates(coordinates);
        }
        else if (localBoard.board[row + 1][column] == null && !localHand.coordinatesList.contains(new Coordinates(row + 1, column))) {
            return checkNewCoordinates(coordinates);
        }
        else if (localBoard.board[row][column - 1] == null && !localHand.coordinatesList.contains(new Coordinates(row,column - 1))) {
            return checkNewCoordinates(coordinates);
        }
        else if (localBoard.board[row][column + 1] == null && !localHand.coordinatesList.contains(new Coordinates(row, column + 1))) {
            return checkNewCoordinates(coordinates);
        }
        else {
            showLocalNotification(NOTCATCHABLE);
            return false;
        }
    }

    private boolean checkNewCoordinates (Coordinates coordinates) {
        LocalHand localHand = modelView.getLocalHand();
        if (localHand.coordinatesList.isEmpty())
            return true;

        boolean checkNear = false;
        boolean checkSameColumn = true;
        boolean checkSameRow = true;
        int row = coordinates.getRow();
        int column = coordinates.getColumn();

        for (Coordinates c : localHand.coordinatesList) {
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

    /**
     * Checks if there is no space left in the specified column of the bookshelf.
     *
     * @param column    The column index to check.
     * @param handSize  The size of the hand.
     * @return {@code true} if there is no space left in the column, {@code false} otherwise.
     */
    public boolean noSpaceLeft(int column, int handSize) {
        String name = gui.getClient().getName();
        LocalBookshelf localBookshelf = modelView.getLocalBookshelfs().get(name);
        int counterSpace = 0;
        if (localBookshelf.bookshelf != null) {
            for (int i = (nRowBookshelf - 1); i >= 0; i--) { // (nRow-1) because we count also the row 0
                if (localBookshelf.bookshelf[i][column] == null)
                    counterSpace++;
            }
        }
        return (counterSpace >= handSize);
    }

    /* NOTIFICATION METHODS */

    /**
     * Shows a local notification message based on the specified notification type.
     *
     * @param notificationsType The type of the notification.
     */
    public void showLocalNotification(NotificationsType notificationsType) {
        switch (notificationsType){
            case TOOMANYITEMS -> gameNotifications.getItems().add("❮ERROR❯ too many items selected!");
            case OUTOFBOARD -> gameNotifications.getItems().add("❮ERROR❯ out of Board slot!");
            case EMPTYSLOT -> gameNotifications.getItems().add("❮ERROR❯ empty slot!");
            case NOTCATCHABLE -> gameNotifications.getItems().add("❮ERROR❯ not catchable!");
        }
        notificationsLenght = notificationsLenght + 1;
        gameNotifications.scrollTo(notificationsLenght);
    }

    /**
     * Shows a global notification message based on the specified notification type, name, coordinates, list, and column.
     *
     * @param notificationsType The type of the notification.
     * @param name              The name associated with the notification.
     * @param coordinates       The coordinates associated with the notification.
     * @param list              The list associated with the notification.
     * @param column            The column associated with the notification.
     */
    public void showGlobalNotification(NotificationsType notificationsType, String name, Coordinates coordinates, ArrayList<Integer> list, int column) {
        switch (notificationsType){
            case PICK -> gameNotifications.getItems().add("❮ACTION❯ " + name + ": PICK, coord. " + coordinates.toString());
            case UNDO -> gameNotifications.getItems().add("❮ACTION❯ " + name + ": UNDO ");
            case ORDER -> gameNotifications.getItems().add("❮ACTION❯ " + name + ": ORDER with " + list.toString());
            case PUT -> gameNotifications.getItems().add("❮ACTION❯ " + name + ": PUT, column " + column);
            case LASTROUND -> gameNotifications.getItems().add("❮INFO❯ " + name + " has finished his bookshelf!");
        }
        notificationsLenght = notificationsLenght + 1;
        gameNotifications.scrollTo(notificationsLenght);
    }

    /**
     * Shows a global notification message based on the specified exception.
     */
    public void showExceptionNotification(String e) {
        gameNotifications.getItems().add("❮ERROR❯ " + e);
        notificationsLenght = notificationsLenght + 1;
        gameNotifications.scrollTo(notificationsLenght);
    }

    /**
     * Updates the TableView with the current local player information.
     */
    public void showTableView() {
        ObservableList<LocalPlayer> data = tableView.getItems();
        data.setAll(modelView.getLocalPlayerList());
        tableView.refresh();
    }

    /* CHAT METHODS */

    /**
     * Sends a chat message based on the entered message and receiver.
     *
     * @param event The action event associated with sending the message.
     */
    public void sendMessage(ActionEvent event) {
        String message = chatMessage.getText();
        chatMessage.clear();
        if(Objects.equals(receiver.getText(), "")){
            gui.sendMessage(message, null);
        } else {
            String receiverName = receiver.getText();
            gui.sendMessage(message, receiverName);
        }
    }

    /**
     * Displays a chat message in the chat field based on the provided ChatMessage and name.
     *
     * @param chatMessage The chat message to display.
     * @param name        The name associated with the message.
     */
    public void displayMessage(ChatMessage chatMessage, String name) {
        if (chatMessage.receiver == null && !Objects.equals(chatMessage.sender, name)){
            chatField.getItems().add("❮TO ALL❯ " + chatMessage.sender + ": " + chatMessage.message);
        } else if (chatMessage.receiver == null && Objects.equals(chatMessage.sender, name)) {
            chatField.getItems().add("❮YOU TO ALL❯ " + chatMessage.message);
        } else if (chatMessage.receiver != null && Objects.equals(chatMessage.sender, name)){
            chatField.getItems().add("❮YOU TO " + chatMessage.receiver + "❯ " + chatMessage.message);
        } else {
            chatField.getItems().add("❮TO YOU❯ " + chatMessage.sender + ": " + chatMessage.message);
        }

        chatLenght = chatLenght + 1;
        chatField.scrollTo(chatLenght);
    }

    /* Switch Buttons
    public void onChat(javafx.event.ActionEvent actionEvent){
        this.gui.switchStage(Command.CHAT);
    }
    public void onBookshelfs(javafx.event.ActionEvent actionEvent) {
        this.gui.switchStage(Command.SHOW_BOOKSHELFS);
    }
    */

    /* OTHER FEATURES */

    private void setEffectNull(){
        hand1.setEffect(null);
        hand2.setEffect(null);
        hand3.setEffect(null);
    }
    private void setArrows(){
        URL url = getClass().getResource("/Images/misc/arrow.png");
        Image arrow = new Image(url.toString());
        arrow1.setImage(arrow);
        arrow2.setImage(arrow);
        arrow3.setImage(arrow);
        arrow4.setImage(arrow);
        arrow5.setImage(arrow);
        setArrowsInvisible();
        setTransition(arrow1);
        setTransition(arrow2);
        setTransition(arrow3);
        setTransition(arrow4);
        setTransition(arrow5);

    }
    private void setArrowsInvisible(){
        arrow1.setVisible(false);
        arrow2.setVisible(false);
        arrow3.setVisible(false);
        arrow4.setVisible(false);
        arrow5.setVisible(false);
    }
    private void setTransition(ImageView arrowImageView){
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), arrowImageView);
        translateTransition.setByY(15); // Sposta di 15 unità verticalmente
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        translateTransition.setAutoReverse(true); // Imposta l'animazione al contrario
        translateTransition.play(); // Avvia l'animazione
    }
    private void showSelectedItem(ImageView imageview){
        if (isImageViewEmpty(hand1)){
            hand1.setImage(imageview.getImage());
        }
        else if (isImageViewEmpty(hand2)){
            hand2.setImage(imageview.getImage());
        }
        else hand3.setImage(imageview.getImage());
    }
    private ImageView getImageViewFromGridPane(GridPane gridPane, int rowIndex, int columnIndex) {
        ImageView imageView = null;

        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == rowIndex && GridPane.getColumnIndex(node) == columnIndex && node instanceof ImageView) {
                imageView = (ImageView) node;

                break;
            }
        }

        return imageView;
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
    @Override
    public GUI getGui() {
        return gui;
    }
}


