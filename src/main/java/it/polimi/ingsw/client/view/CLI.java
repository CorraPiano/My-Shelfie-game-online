package it.polimi.ingsw.client.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.client.localModel.*;

import java.util.ArrayList;
import java.util.HashMap;

import static it.polimi.ingsw.util.Constants.*;

public class CLI {

    // METHODS
    public StringBuilder showLogo() {
        StringBuilder logo = new StringBuilder();
        logo.append(LOGO1);
        return logo;
    }

    public StringBuilder showBoard(LocalBoard board) {
        StringBuilder table = new StringBuilder();

        table.append("\n");
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
            table.append(ANSI_RESET + "  \n");
        }
        table.append("              Board             \n");
        return table;
    }

    public StringBuilder showBookshelf(LocalBookshelf bookshelf) {
        StringBuilder library = new StringBuilder();

        library.append("\n");
        for (int i = nRowBookshelf - 1; i > -3; i--) {
            switch (i) {
                case 5 -> library.append("   ₅");
                case 4 -> library.append("   ₄");
                case 3 -> library.append("   ₃");
                case 2 -> library.append("   ₂");
                case 1 -> library.append("   ₁");
                case 0 -> library.append("   ₀");
            }
            for (int j = 0; j < nColumnBookshelf; j++) {
                if (i >= 0) {
                    if (j == 0) {
                        library.append(ANSI_RESET + " │");
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
                        library.append("     └───┴");
                    } else if (j == 4) {
                        library.append("───┘");
                    } else {
                        library.append("───┴");
                    }
                } else {
                    switch (j) {
                        case 0 -> library.append("       ⁰ ");
                        case 1 -> library.append("  ¹ ");
                        case 2 -> library.append("  ² ");
                        case 3 -> library.append("  ³ ");
                        case 4 -> library.append("  ⁴  ");
                    }
                }
            }
            library.append(ANSI_RESET + "\n");
        }
        library.append("      " + bookshelf.name + "'s Bookshelf   ");
        return library;
    }

    public StringBuilder showHand(LocalHand hand) {
        StringBuilder string = new StringBuilder();
        string.append("Hand: ");
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
        return string;
    }

    public StringBuilder showPersonalCard(DataCard dataCard) {
        StringBuilder card = new StringBuilder();
        Coordinates coordinates = new Coordinates();
        HashMap<Coordinates, Integer> personalMap = new HashMap<>();

        card.append("\n\n");
        for (int i = nRowBookshelf; i > -3; i--) {
            switch (i) {
                case 5 -> card.append("  ₅");
                case 4 -> card.append("  ₄");
                case 3 -> card.append("  ₃");
                case 2 -> card.append("  ₂");
                case 1 -> card.append("  ₁");
                case 0 -> card.append("  ₀");
            }
            for (int j = 0; j < nColumnBookshelf; j++) {

                coordinates.setRow(i);
                coordinates.setColumn(j);

                if (i >= 0 && i != nRowBookshelf) {
                    if (j == 0) {
                        card.append(ANSI_RESET + " │");
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
                            card.append(ANSI_RESET + "│ ");
                        } else {
                            card.append(ANSI_RESET + "│");
                        }
                    } else {
                        card.append(ANSI_RESET + "   " + DEFAULT_BACKGROUND);
                        if(j == nColumnBookshelf - 1) {
                            card.append(ANSI_RESET + "│ ");
                        } else {
                            card.append(ANSI_RESET + "│");
                        }
                    }
                } else if (i == nRowBookshelf) {
                    if (j == 0) {
                        card.append(DEFAULT_BACKGROUND + "  " + ANSI_RESET + "  ┌───┬");
                    } else if (j == 4) {
                        card.append(ANSI_RESET + "───┐ ");
                    } else {
                        card.append(ANSI_RESET + "───┬" + DEFAULT_BACKGROUND);
                    }
                } else if (i == -1){
                    if (j == 0) {
                        card.append(DEFAULT_BACKGROUND + "  " + ANSI_RESET + "  └───┴");
                    } else if (j == 4) {
                        card.append(ANSI_RESET + "───┘ ");
                    } else {
                        card.append(ANSI_RESET + "───┴" + DEFAULT_BACKGROUND);
                    }
                } else {
                    switch (j) {
                        case 0 -> card.append("      ⁰ ");
                        case 1 -> card.append("  ¹ ");
                        case 2 -> card.append("  ² ");
                        case 3 -> card.append("  ³ ");
                        case 4 -> card.append("  ⁴  ");
                    }
                }
            }
            card.append(ANSI_RESET + "\n");

        }
        card.append("      PersonalGoalCard     \n");
        return card;
    }

    public StringBuilder showPlayers(ArrayList<LocalPlayer> playerList) {
        StringBuilder players = new StringBuilder();
        players.append("\nEcco i giocatori: ");
        for(LocalPlayer p:playerList) {
            players.append(p.name + " ");
            if(p.firstPlayerSeat)
                players.append("*\n");
            else
                players.append("\n");
        }
        players.append("\n");
        return players;
    }

    public StringBuilder showCommonGoalCards(LocalCommonCard localCommonCard) {
        String card = new String();
        String token = new String();
        StringBuilder commonCard = new StringBuilder();
        commonCard.append("\n\n                           \n");

        switch (localCommonCard.type) {
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

        int index = localCommonCard.tokenList.size();
        switch (localCommonCard.tokenList.get(0).getValue()) {
            case 2 -> token = token2;
            case 4 -> token = token4;
            case 6 -> token = token6;
            case 8 -> token = token8;
        }
        String[] tokenResult  = token.split("\n");

        for(int i=0; i<cardResult.length || i<tokenResult.length; i++) {
            commonCard.append(cardResult[i] + tokenResult[i] + "\n");
        }
        commonCard.append("    CommonGoalCard         \n");
        return commonCard;
    }

    public String showOnlyCommon(LocalCommonCard localCommonCard) {
        String card = new String();

        switch (localCommonCard.type) {
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
        return card;
    }

}
