package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.model.CommonGoalCard;
import it.polimi.ingsw.model.DataCard;

import java.util.ArrayList;
import java.util.Map;

public class ViewHandler {
    private CLI cli = new CLI();

    //da fare la funzione che stampa il LOGO

    public void showGame(LocalBoard localBoard, Map<String, LocalBookshelf> localBookshelfs, ArrayList<LocalCommonCard> commonCards, DataCard dataCard, ArrayList<LocalPlayer> localPlayerList) {
        StringBuilder game = new StringBuilder();

        String[] board = cli.showBoard(localBoard).toString().split("\n");
        String[] commonCard1 = cli.showCommonGoalCards(commonCards.get(0)).toString().split("\n");
        String[] commonCard2 = cli.showCommonGoalCards(commonCards.get(1)).toString().split("\n");
        String[] personalCard = cli.showPersonalCard(dataCard).toString().split("\n");
        for(int i=0; i<board.length; i++){
            game.append(board[i] + commonCard1[i] + commonCard2[i] + personalCard[i] + "\n");
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

    public void showNewTurn(LocalBoard localBoard, Map<String, LocalBookshelf> localBookshelfs, ArrayList<LocalCommonCard> commonCards, DataCard dataCard, ArrayList<LocalPlayer> localPlayerList) {
        StringBuilder game = new StringBuilder();

        String[] board = cli.showBoard(localBoard).toString().split("\n");
        String[] commonCard1 = cli.showCommonGoalCards(commonCards.get(0)).toString().split("\n");
        String[] commonCard2 = cli.showCommonGoalCards(commonCards.get(1)).toString().split("\n");
        String[] personalCard = cli.showPersonalCard(dataCard).toString().split("\n");
        for(int i=0; i<board.length; i++){
            game.append(board[i] + commonCard1[i] + commonCard2[i] + personalCard[i] + "\n");
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

        System.out.println("\n─────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
        System.out.println(game);
    }

    public void showBoad(LocalBoard board) {
        System.out.println(cli.showBoard(board));
    }
    public void showBookshelf(LocalBookshelf bookshelf) {
        System.out.println(cli.showBookshelf(bookshelf));
    }
    public void showHand(LocalHand hand) {
        System.out.println(cli.showHand(hand));
    }

    //Metodi che potrebbero servire
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
