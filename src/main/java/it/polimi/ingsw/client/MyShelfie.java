package it.polimi.ingsw.client;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.localModel.ModelView;

public class MyShelfie {
    public static void main( String[] args ) throws NotLinearPickException, LimitReachedPickException, NotCatchablePickException, EmptySlotPickException, OutOfBoardPickException {
        /*
        ModelView modelView = new ModelView();
        Board board = new Board(4, new Hand());
        Bookshelf bookshelf = new Bookshelf();
        Coordinates coordinates = new Coordinates();
        Item item1 = new Item(ItemType.GREEN);
        Item item2 = new Item(ItemType.PINK);
        Item item3 = new Item(ItemType.BLUE);
        Item item4 = new Item(ItemType.YELLOW);
        coordinates.setRow(1);
        coordinates.setColumn(3);


        board.drawBoardItems();
        board.getItem(coordinates);
        changeBoard(board.getLivingRoom(), modelView);

        bookshelf.putItem(item1, 2, 4);
        bookshelf.putItem(item2, 1, 4);
        bookshelf.putItem(item3, 0, 4);
        bookshelf.putItem(item4, 0, 2);
        bookshelf.putItem(item1, 1, 2);
        bookshelf.putItem(item4, 0, 3);


        changeBookshelf(bookshelf.getLibrary(), modelView);
        changeCommonGoalCard(modelView);


         */
    }


    /*
    ======================================================================
    Fare qua le funzioni che vengono chiamate dal listener, ovvero quelle
    che possono essere chiamate a seguito di un aggiornamento o modifica
    ======================================================================
     */

    /*
    public static void changeBoard(Item[][] board, ModelView miniModel) {
        // Settare in ModelView la nuova board
        miniModel.setBoard(board);

        // Valutare se è necessario stamparla a schermo o no
        // Stampa della board
        CLI cli = new CLI(miniModel);
        cli.showBoard();
    }

    public static void changeBookshelf(Item[][] bookshelf, ModelView miniModel) {
        // Bisogna settare in ModelView la nuova bookshelf
        miniModel.setBookshelf(bookshelf);

        // Bisogna stampare sicuramente la nuova bookshelf e anche la board perè sicuramente cambiata
        CLI cli = new CLI(miniModel);
        cli.showBoard();
        cli.showBookshelf();
    }

    public static void changeCommonGoalCard(ModelView miniModel) {
        // Bisogna settare in ModelView la nuova CommonGoalCard

        // Stamparla a schermo
        CLI cli = new CLI(miniModel);
        cli.showCommonCard(1);
        cli.showCommonCard(2);
        cli.showCommonCard(3);
        cli.showCommonCard(4);
        cli.showCommonCard(5);
        cli.showCommonCard(6);
        cli.showCommonCard(7);
        cli.showCommonCard(8);
        cli.showCommonCard(9);
        cli.showCommonCard(10);
        cli.showCommonCard(11);
        cli.showCommonCard(12);
    }


    */
}



