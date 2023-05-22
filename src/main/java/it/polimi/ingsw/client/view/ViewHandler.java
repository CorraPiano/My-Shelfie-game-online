package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.model.DataCard;
import it.polimi.ingsw.model.GameMode;

import java.util.ArrayList;
import java.util.Map;

import static it.polimi.ingsw.model.GameMode.EASY;
import static it.polimi.ingsw.model.GameMode.EXPERT;
import static it.polimi.ingsw.util.Constants.*;

public class ViewHandler {
    private CLI cli = new CLI();

    public void showIntro(){
        System.out.println(BROWN_FOREGROUND + LOGO6);
        System.out.println("Player, it’s time to reorganize your library and put your favorite objects back in place!\n" +
                "Will you be able to do it faster than other players, satisfying the required patterns?\n" +
                "Pick items from the board, insert them in your bookshelf, making them fall from the top,\n" +
                "while trying to recreate the patterns of your personal or common target.\n" + ANSI_RESET);
    }

    public void showCommonCards(LocalCommonCard localCommonCard) {
        StringBuilder commonDescription = new StringBuilder();
        String description = new String();
        switch(localCommonCard.type){
            case 0:
                description = "\nSix groups each containing at least\n" +
                        "2 tiles of the same type (not necessarily\n" +
                        "in the depicted shape).\n" +
                        "The tiles of one group can be different\n" +
                        "from those of another group.";
                break;
            case 1:
                description = "\nFour groups each containing at least\n" +
                        "4 tiles of the same type (not necessarily\n" +
                        "in the depicted shape).\n" +
                        "The tiles of one group can be different\n" +
                        "from those of another group.";
                break;
            case 2:
                description = "\nFour tiles of the same type in the four\n" +
                        "corners of the bookshelf.";
                break;
            case 3:
                description = "\nTwo groups each containing 4 tiles of\n" +
                        "the same type in a 2x2 square. The tiles\n" +
                        "of one square can be different from\n" +
                        "those of the other square.";
                break;
            case 4:
                description = "\nThree columns each formed by 6 tiles\n" +
                        "of maximum three different types. One\n" +
                        "column can show the same or a different\n" +
                        "combination of another column.";
                break;
            case 5:
                description = "\nEight tiles of the same type. There’s no\n" +
                        "restriction about the position of these\n" +
                        "tiles.";
                break;
            case 6:
                description = "\nFive tiles of the same type forming a\n" +
                        "diagonal.";
                break;
            case 7:
                description = "\nFour lines each formed by 5 tiles of\n" +
                        "maximum three different types. One\n" +
                        "line can show the same or a different\n" +
                        "combination of another line.";
                break;
            case 8:
                description = "\nTwo columns each formed by 6\n" +
                        "different types of tiles.";
                break;
            case 9:
                description = "\nTwo lines each formed by 5 different\n" +
                        "types of tiles. One line can show the\n" +
                        "same or a different combination of the\n" +
                        "other line.";
                break;
            case 10:
                description = "\nFive tiles of the same type forming an X.";
                break;
            case 11:
                description = "\nFive columns of increasing or decreasing\n" +
                        "height. Starting from the first column on\n" +
                        "the left or on the right, each next column\n" +
                        "must be made of exactly one more tile.\n" +
                        "Tiles can be of any type.";
                break;

        }
        String[] commonCard = cli.showOnlyCommon(localCommonCard).split("\n");
        String[] cardDescription = description.split("\n");
        for(int i=0; i<commonCard.length; i++){
            if(i<cardDescription.length){
                commonDescription.append(ANSI_RESET + commonCard[i] + BROWN_FOREGROUND + cardDescription[i] + ANSI_RESET + "\n");

            } else {
                commonDescription.append(commonCard[i] + "\n");

            }
        }
        System.out.println(commonDescription);
    }

    public void showGame(LocalBoard localBoard, Map<String, LocalBookshelf> localBookshelfs, ArrayList<LocalCommonCard> commonCards, DataCard dataCard, ArrayList<LocalPlayer> localPlayerList, GameMode gameMode) {
        StringBuilder game = new StringBuilder();

        String[] board = cli.showBoard(localBoard).toString().split("\n");
        String[] personalCard = cli.showPersonalCard(dataCard).toString().split("\n");

        if (gameMode.compareTo(EASY) == 1){
            String[] commonCard1 = cli.showCommonGoalCards(commonCards.get(0)).toString().split("\n");
            String[] commonCard2 = cli.showCommonGoalCards(commonCards.get(1)).toString().split("\n");
            for(int i=0; i<board.length; i++){
                game.append(board[i] + commonCard1[i] + commonCard2[i] + personalCard[i] + "\n");
            }
        } else{
            for(int i=0; i<board.length; i++){
                game.append(board[i] + personalCard[i] + "\n");
            }
        }

        String[][] library = new String[localBookshelfs.size()][];
        for(int i=0; i<localBookshelfs.size(); i++){
            library[i] = cli.showBookshelf(localBookshelfs.get(localPlayerList.get(i).name)).toString().split("\n");
        }
        for(int j=0; j<library[0].length; j++){
            for(int i=0; i<localBookshelfs.size(); i++){
                game.append(library[i][j]);
            }
            game.append("\n");
        }
        System.out.println(game);
    }

    public void showNewTurn(LocalBoard localBoard, Map<String, LocalBookshelf> localBookshelfs, ArrayList<LocalCommonCard> commonCards, DataCard dataCard, ArrayList<LocalPlayer> localPlayerList, GameMode gameMode) {
        StringBuilder game = new StringBuilder();

        String[] board = cli.showBoard(localBoard).toString().split("\n");
        String[] personalCard = cli.showPersonalCard(dataCard).toString().split("\n");

        if (gameMode.compareTo(EASY) == 1){
            String[] commonCard1 = cli.showCommonGoalCards(commonCards.get(0)).toString().split("\n");
            String[] commonCard2 = cli.showCommonGoalCards(commonCards.get(1)).toString().split("\n");
            for(int i=0; i<board.length; i++){
                game.append(board[i] + commonCard1[i] + commonCard2[i] + personalCard[i] + "\n");
            }
        } else{
            for(int i=0; i<board.length; i++){
                game.append(board[i] + personalCard[i] + "\n");
            }
        }

        String[][] library = new String[localBookshelfs.size()][];
        for(int i=0; i<localBookshelfs.size(); i++){
            library[i] = cli.showBookshelf(localBookshelfs.get(localPlayerList.get(i).name)).toString().split("\n");
        }
        for(int j=0; j<library[0].length; j++){
            for(int i=0; i<localBookshelfs.size(); i++){
                game.append(library[i][j]);
            }
            game.append("\n");
        }

        System.out.println(BROWN_FOREGROUND + "\n\n─────────────────────────────────────────────────────────────────────────────────────────────────────────────────" + ANSI_RESET);
        System.out.println(game);
    }

    public void showBookshelf(LocalBookshelf bookshelf) {
        System.out.println(cli.showBookshelf(bookshelf));
    }

    public void showBoardAndHand(LocalBoard localBoard, LocalHand localHand) {
        StringBuilder boardHand = new StringBuilder();

        String[] board = cli.showBoard(localBoard).toString().split("\n");
        for(int i=0; i<board.length; i++){
            if(i==2){
                StringBuilder hand = cli.showHand(localHand);
                boardHand.append(board[i] + hand );
            }
            else {
                boardHand.append(board[i] + "\n");
            }
        }
        System.out.println(boardHand);
    }



    //Metodi che potrebbero servire
    public void showBoard(LocalBoard board) {
        System.out.println(cli.showBoard(board));
    }
    public void showHand(LocalHand hand) {
        System.out.println(cli.showHand(hand));
    }
    public void showPlayers(ArrayList<LocalPlayer> playerList) {
        System.out.println(cli.showPlayers(playerList));
    }
    public void showCommonGoalCards(LocalCommonCard localCommonCard) {
        System.out.println(cli.showCommonGoalCards(localCommonCard));
    }
    public void showPersonalGoalCard(DataCard dataCard) {
        System.out.println(cli.showPersonalCard(dataCard));
    }

}
