package it.polimi.ingsw.client.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.client.localModel.*;

import java.util.ArrayList;
import java.util.HashMap;

import static it.polimi.ingsw.util.Constants.*;

public class CLI {

    // Metodi
    public void showLogo() {
        System.out.println(LOGO1);
    }

    public void showBoard(localBoard board) {
        StringBuilder table = new StringBuilder();

        table.append("\nTavolo di gioco: \n");
        for (int i = nRowBoard - 1; i > -2; i--) {
            switch (i) {
                case 8 -> table.append(" ₈ ");
                case 7 -> table.append(" ₇ ");
                case 6 -> table.append(" ₆ ");
                case 5 -> table.append(" ₅ ");
                case 4 -> table.append(" ₄ ");
                case 3 -> table.append(" ₃ ");
                case 2 -> table.append(" ₂ ");
                case 1 -> table.append(" ₁ ");
                case 0 -> table.append(" ₀ ");
            }
            for (int j = 0; j < nColumnBoard; j++) {
                if (i >= 0) {
                    if (board.board[i][j] == null) {
                        table.append(ANSI_RESET + BLACK_BACKGROUND + "   ");
                    } else {
                        switch (board.board[i][j].getType().getValue()) {
                            case 0 -> table.append(GREEN_BACKGROUND + "   ");
                            case 1 -> table.append(YELLOW_BACKGROUND + "   ");
                            case 2 -> table.append(BLUE_BACKGROUND + "   ");
                            case 3 -> table.append(PINK_BACKGROUND + "   ");
                            case 4 -> table.append(CYAN_BACKGROUND + "   ");
                            case 5 -> table.append(WHITE_BACKGROUND + "   ");
                        }
                    }
                } else {
                    switch (j) {
                        case 0 -> table.append("    ₀ ");
                        case 1 -> table.append(" ₁ ");
                        case 2 -> table.append(" ₂ ");
                        case 3 -> table.append(" ₃ ");
                        case 4 -> table.append(" ₄ ");
                        case 5 -> table.append(" ₅ ");
                        case 6 -> table.append(" ₆ ");
                        case 7 -> table.append(" ₇ ");
                        case 8 -> table.append(" ₈ ");
                    }
                }
            }
            table.append(ANSI_RESET + "\n");
        }
        System.out.println(table);
    }

    public void showBookshelf(localBookshelf bookshelf) {
        StringBuilder library = new StringBuilder();

        library.append("libreria di " +bookshelf.name+ "\n");
        for (int i = nRowBookshelf - 1; i > -3; i--) {
            for (int j = 0; j < nColumnBookshelf; j++) {
                if (i >= 0) {
                    if (j == 0) {
                        library.append(ANSI_RESET + "   │");
                    }
                    if (bookshelf.bookshelf[i][j] == null) {
                        library.append(ANSI_RESET + DEFAULT_BACKGROUND + "   ");
                        library.append(ANSI_RESET + "│");
                    } else {
                        switch (bookshelf.bookshelf[i][j].getType().getValue()) {
                            case 0 -> library.append(GREEN_BACKGROUND + "   ");
                            case 1 -> library.append(YELLOW_BACKGROUND + "   ");
                            case 2 -> library.append(BLUE_BACKGROUND + "   ");
                            case 3 -> library.append(PINK_BACKGROUND + "   ");
                            case 4 -> library.append(CYAN_BACKGROUND + "   ");
                            case 5 -> library.append(WHITE_BACKGROUND + "   ");
                        }
                        library.append(ANSI_RESET + "│");
                    }
                } else if (i == -1) {
                    if (j == 0) {
                        library.append("   └───┴");
                    } else if (j == 4) {
                        library.append("───┘");
                    } else {
                        library.append("───┴");
                    }
                } else {
                    switch (j) {
                        case 0 -> library.append("     ⁰ ");
                        case 1 -> library.append("  ¹ ");
                        case 2 -> library.append("  ² ");
                        case 3 -> library.append("  ³ ");
                        case 4 -> library.append("  ⁴ ");
                    }
                }
            }
            library.append(ANSI_RESET + "\n");
        }
        System.out.println(library);
    }

    public void showHand(localHand hand) {
        StringBuilder string = new StringBuilder();
        string.append("\nmano: ");
        for(Item item: hand.hand) {
            switch (item.getType().getValue()) {
                case 0 -> string.append(GREEN_BACKGROUND + "   " + ANSI_RESET + " ");
                case 1 -> string.append(YELLOW_BACKGROUND + "   " + ANSI_RESET + " ");
                case 2 -> string.append(BLUE_BACKGROUND + "   " + ANSI_RESET + " ");
                case 3 -> string.append(PINK_BACKGROUND + "   " + ANSI_RESET + " ");
                case 4 -> string.append(CYAN_BACKGROUND + "   " + ANSI_RESET + " ");
                case 5 -> string.append(WHITE_BACKGROUND + "   " + ANSI_RESET + " ");
            }
        }
        string.append("\n");
        System.out.println(string);
    }

    public void showPersonalCard(DataCard dataCard) {
        StringBuilder card = new StringBuilder();
        Coordinates coordinates = new Coordinates();
        HashMap<Coordinates, Integer> personalMap = new HashMap<>();

        card.append("La tua PersonalGoalCard: \n");
        for (int i = nRowBookshelf; i > -2; i--) {
            for (int j = 0; j < nColumnBookshelf; j++) {

                coordinates.setRow(i);
                coordinates.setColumn(j);

                if (i >= 0 && i != nRowBookshelf) {
                    if (j == 0) {
                        card.append(ANSI_RESET + "  " + BLACK_BACKGROUND + " │");
                    }
                    if (dataCard.getCard().get(coordinates) != null) {
                        switch (dataCard.getCard().get(coordinates)) {
                            case 0 -> card.append(GREEN_BACKGROUND + "   ");
                            case 1 -> card.append(YELLOW_BACKGROUND + "   ");
                            case 2 -> card.append(BLUE_BACKGROUND + "   ");
                            case 3 -> card.append(PINK_BACKGROUND + "   ");
                            case 4 -> card.append(CYAN_BACKGROUND + "   ");
                            case 5 -> card.append(WHITE_BACKGROUND + "   ");
                        }
                        if(j == nColumnBookshelf - 1) {
                            card.append(BLACK_BACKGROUND + "│ ");
                        } else {
                            card.append(BLACK_BACKGROUND + "│");
                        }
                    } else {
                        card.append(BLACK_BACKGROUND + "   " + DEFAULT_BACKGROUND);
                        if(j == nColumnBookshelf - 1) {
                            card.append(BLACK_BACKGROUND + "│ ");
                        } else {
                            card.append(BLACK_BACKGROUND + "│");
                        }
                    }
                } else if (i == nRowBookshelf) {
                    if (j == 0) {
                        card.append(DEFAULT_BACKGROUND + "  " + BLACK_BACKGROUND + " ┌───┬");
                    } else if (j == 4) {
                        card.append(BLACK_BACKGROUND + "───┐ ");
                    } else {
                        card.append(BLACK_BACKGROUND + "───┬" + DEFAULT_BACKGROUND);
                    }
                } else {
                    if (j == 0) {
                        card.append(DEFAULT_BACKGROUND + "  " + BLACK_BACKGROUND + " └───┴");
                    } else if (j == 4) {
                        card.append(BLACK_BACKGROUND + "───┘ ");
                    } else {
                        card.append(BLACK_BACKGROUND + "───┴" + DEFAULT_BACKGROUND);
                    }
                }
            }
            card.append(ANSI_RESET + "\n");

        }
        System.out.println(card);
    }

    public void showPlayers(ArrayList<localPlayer> playerList) {
        System.out.println("\nEcco i giocatori: ");
        for(localPlayer p:playerList) {
            System.out.print(p.name + " ");
            if(p.firstPlayerSeat)
                System.out.print("*\n");
            else
                System.out.print("\n");
        }
        System.out.print("\n");
    }

    // Questa funzione dovrebbe mostrare a video le due CommonCard corrette
    //Guarda la funzione split
    public void showCommonGoalCards1(CommonGoalCard commonGoalCard) {
        switch (commonGoalCard.getType()) {
            case 1 -> System.out.println(commonCard1);
            case 2 -> System.out.println(commonCard2);
            case 3 -> System.out.println(commonCard3);
            case 4 -> System.out.println(commonCard4);
            case 5 -> System.out.println(commonCard5);
            case 6 -> System.out.println(commonCard6);
            case 7 -> System.out.println(commonCard7);
            case 8 -> System.out.println(commonCard8);
            case 9 -> System.out.println(commonCard9);
            case 10 -> System.out.println(commonCard10);
            case 11 -> System.out.println(commonCard11);
            case 12 -> System.out.println(commonCard12);
        }
    }

    public void showCommonGoalCards(CommonGoalCard commonGoalCard) {
        String card = new String();
        String token = new String();
        StringBuilder commonCard = new StringBuilder();

        switch (commonGoalCard.getType()) {
            case 0 -> card = commonCard1;
            case 1 -> card = commonCard2;
            case 2 -> card = commonCard3;
            case 3 -> card = commonCard4;
            case 4 -> card = commonCard5;
            case 5 -> card = commonCard6;
            case 6 -> card = commonCard7;
            case 7 -> card = commonCard8;
            case 8 -> card = commonCard9;
            case 9 -> card = commonCard10;
            case 10 -> card = commonCard11;
            case 11 -> card = commonCard12;
        }
        String[] cardResult  = card.split("\n");

        int index = commonGoalCard.showToken().size();
        switch (commonGoalCard.showToken().get(index-1).getValue()) {
            case 4 -> token = token4;
            case 6 -> token = token6;
            case 8 -> token = token8;
        }
        String[] tokenResult  = token.split("\n");

        for(int i=0; i<cardResult.length || i<tokenResult.length; i++) {
            commonCard.append(cardResult[i] + tokenResult[i] + "\n");
        }
        System.out.println(commonCard);
    }

    //Da vedere se aggiungerla come funzione
    public void showCommonCard(int Index) {
        switch (Index) {
            case 1 -> System.out.println(commonCard1);
            case 2 -> System.out.println(commonCard2);
            case 3 -> System.out.println(commonCard3);
            case 4 -> System.out.println(commonCard4);
            case 5 -> System.out.println(commonCard5);
            case 6 -> System.out.println(commonCard6);
            case 7 -> System.out.println(commonCard7);
            case 8 -> System.out.println(commonCard8);
            case 9 -> System.out.println(commonCard9);
            case 10 -> System.out.println(commonCard10);
            case 11 -> System.out.println(commonCard11);
            case 12 -> System.out.println(commonCard12);
        }
    }
}
