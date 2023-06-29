package it.polimi.ingsw.client.view.GUI.controllers;

import it.polimi.ingsw.client.localModel.LocalBookshelf;
import it.polimi.ingsw.client.localModel.LocalPlayer;
import it.polimi.ingsw.client.localModel.ModelView;
import it.polimi.ingsw.client.view.GUI.AlertBox;
import it.polimi.ingsw.client.view.GUI.Command;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.model.Item;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import static it.polimi.ingsw.util.Constants.*;

/**
 * The `EndController` class implements the `GUIController` interface and controls the end game screen in the GUI.
 * It manages the graphical elements such as images, texts, and grids displayed on the screen.
 */
public class EndController implements GUIController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView place1;
    @FXML
    private Text text1;
    @FXML
    private Text score1;
    @FXML
    private ImageView bookshelf1;
    @FXML
    private ImageView personal1;
    @FXML
    private GridPane bookshelfGrid1;
    @FXML
    private ImageView place2;
    @FXML
    private Text text2;
    @FXML
    private Text score2;
    @FXML
    private ImageView bookshelf2;
    @FXML
    private ImageView personal2;
    @FXML
    private GridPane bookshelfGrid2;
    @FXML
    private ImageView place3;
    @FXML
    private Text text3;
    @FXML
    private Text score3;
    @FXML
    private ImageView bookshelf3;
    @FXML
    private ImageView personal3;
    @FXML
    private GridPane bookshelfGrid3;
    @FXML
    private Pane pane3;
    @FXML
    private ImageView place4;
    @FXML
    private Text text4;
    @FXML
    private Text score4;
    @FXML
    private ImageView bookshelf4;
    @FXML
    private ImageView personal4;
    @FXML
    private GridPane bookshelfGrid4;
    @FXML
    private Pane pane4;

    //ATTRIBUTES
    private GUI gui;
    private ModelView modelView;

    public void init(){
        anchorPane.setOnMouseClicked((event) -> this.blockPane());
    }
    private void blockPane(){
        if(gui.imDisconnected){
            int status = AlertBox.errorData(gui.getPrimaryStage(), "Error", "Disconnected");
        }
    }

    /**
     * Updates the labels and graphical elements on the end game screen based on the current game state.
     * It retrieves the necessary information from the `ModelView` and updates the corresponding GUI components.
     */
    public void updateLabel() {
        this.modelView = gui.getClient().getModelView();
        int numPlayers = modelView.getLocalPlayerList().size();
        //System.out.println("--> Entrato in updateLabel");
        resetLabel();
        setPlaces(numPlayers);
        setNames(numPlayers);
        setScore(numPlayers);
        setBookshelfs(numPlayers);
        setPersonalCards(numPlayers);

    }

    private void setPlaces(int numPlayers){
        URL url_place1 = getClass().getResource("/Images/misc/1-removebg-preview.png");
        URL url_place2 = getClass().getResource("/Images/misc/2-removebg-preview.png");
        String style =
                "-fx-background-color: #B77C52;" +
                "-fx-border-color: rgb(60, 40, 14);" +
                "-fx-border-width: 3px;" +
                "-fx-border-radius: 20px;" +
                "-fx-background-radius: 20px;";
        place1.setImage(new Image(url_place1.toString()));
        place2.setImage(new Image(url_place2.toString()));
        if(numPlayers>=3){
            URL url_place3 = getClass().getResource("/Images/misc/3-removebg-preview.png");
            pane3.setStyle(style);
            place3.setImage(new Image(url_place3.toString()));
            if(numPlayers==4){
                URL url_place4 = getClass().getResource("/Images/misc/4-removebg-preview.png");
                pane4.setStyle(style);
                place4.setImage(new Image(url_place4.toString()));
            }
        }
    }

    private void setNames(int numPlayers) {
        text1.setText(modelView.getLocalPlayerList().get(0).name);
        text2.setText(modelView.getLocalPlayerList().get(1).name);
        if(numPlayers>=3){
            text3.setText(modelView.getLocalPlayerList().get(2).name);
        }
        if(numPlayers==4){
            text4.setText(modelView.getLocalPlayerList().get(3).name);
        }
    }

    private void setScore(int numPlayers) {
        score1.setText("Score: " + modelView.getLocalPlayerList().get(0).points);
        score2.setText("Score: " + modelView.getLocalPlayerList().get(1).points);
        if(numPlayers>=3){
            score3.setText("Score: " + modelView.getLocalPlayerList().get(2).points);
        }
        if(numPlayers==4){
            score4.setText("Score: " + modelView.getLocalPlayerList().get(3).points);
        }
    }

    private void setBookshelfs(int numPlayers) {
        URL url_bookshelf = getClass().getResource("/Images/boards/bookshelf.png");
        bookshelf1.setImage(new Image(url_bookshelf.toString()));
        showBookshelf(0);
        bookshelf2.setImage(new Image(url_bookshelf.toString()));
        showBookshelf(1);
        if(numPlayers>=3){
            bookshelf3.setImage(new Image(url_bookshelf.toString()));
            showBookshelf(2);
            if(numPlayers==4){
                bookshelf4.setImage(new Image(url_bookshelf.toString()));
                showBookshelf(3);
            }
        }
    }

    private void showBookshelf(int position) {
        String name = modelView.getLocalPlayerList().get(position).name;
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
                        imageView.setFitWidth(18.5);
                        imageView.setFitHeight(19);
                        switch (position){
                            case 0 -> bookshelfGrid1.add(imageView, j, i);
                            case 1 -> bookshelfGrid2.add(imageView, j, i);
                            case 2 -> bookshelfGrid3.add(imageView, j, i);
                            case 3 -> bookshelfGrid4.add(imageView, j, i);
                        }
                    }
                }
            }
        }
    }

    private void resetPlayersBookshelfs(GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if(node instanceof ImageView)
                ((ImageView) node).setImage(null);
        }
    }

    private void setPersonalCards(int numPlayers) {
        int num = 0;
        for(int i=0; i<numPlayers; i++){
            switch (i){
                case 0 -> {
                    num = modelView.getLocalPlayerList().get(0).getNumPersonalCard();
                    URL url_personal = getClass().getResource("/Images/personal/" + num + ".png");
                    personal1.setImage(new Image(url_personal.toString()));
                }
                case 1 -> {
                    num = modelView.getLocalPlayerList().get(1).getNumPersonalCard();
                    URL url_personal = getClass().getResource("/Images/personal/" + num + ".png");
                    personal2.setImage(new Image(url_personal.toString()));
                }
                case 2 -> {
                    num = modelView.getLocalPlayerList().get(2).getNumPersonalCard();
                    URL url_personal = getClass().getResource("/Images/personal/" + num + ".png");
                    personal3.setImage(new Image(url_personal.toString()));
                }
                case 3 -> {
                    num = modelView.getLocalPlayerList().get(3).getNumPersonalCard();
                    URL url_personal = getClass().getResource("/Images/personal/" + num + ".png");
                    personal4.setImage(new Image(url_personal.toString()));
                }
            }
        }
    }

    @FXML
    protected void onPlayAgainButton() {
        gui.switchStage(Command.START_GAME);
    }

    @FXML
    protected void onLeaveButton() {
        int status = AlertBox.exitRequest(gui.getPrimaryStage(), null, "Are you sure you want to exit?");
        if(status == 1){
            System.exit(0);
        }
        //gui.leaveGame();
        //gui.getClient().leaveGame();
        //System.out.println("--> onQuitButton");
    }

    public void resetLabel(){
        pane3.setStyle(null);
        pane4.setStyle(null);
        place3.setImage(null);
        place4.setImage(null);
        text3.setText("");
        text4.setText("");
        score3.setText("");
        score4.setText("");
        bookshelf3.setImage(null);
        bookshelf4.setImage(null);
        resetPlayersBookshelfs(bookshelfGrid1);
        resetPlayersBookshelfs(bookshelfGrid2);
        resetPlayersBookshelfs(bookshelfGrid3);
        resetPlayersBookshelfs(bookshelfGrid4);
        personal3.setImage(null);
        personal4.setImage(null);
    }

    //METHODS
    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
    @Override
    public GUI getGui() {
        return gui;
    }
}
