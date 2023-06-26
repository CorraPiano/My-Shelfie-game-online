package it.polimi.ingsw.client.view.TUI;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.client.localModel.*;
import static it.polimi.ingsw.util.Constants.*;

public class TUIPrinter {

    /** <p> return a string that rapresent the TUI rapresentation of the passed Board </p>
     */
    public String getBoardString(LocalBoard board) {
        StringBuilder table = new StringBuilder();

        table.append("\n");
        for (int i = nRowBoard - 1; i > -2; i--) {
            switch (i) {
               /*case 8 -> table.append(" ₈ ");
                case 7 -> table.append(" ₇ ");
                case 6 -> table.append(" ₆ ");
                case 5 -> table.append(" ₅ ");
                case 4 -> table.append(" ₄ ");
                case 3 -> table.append(" ₃ ");
                case 2 -> table.append(" ₂ ");
                case 1 -> table.append(" ₁ ");
                case 0 -> table.append(" ₀ ");*/
                case 8 -> table.append(" 8 ");
                case 7 -> table.append(" 7 ");
                case 6 -> table.append(" 6 ");
                case 5 -> table.append(" 5 ");
                case 4 -> table.append(" 4 ");
                case 3 -> table.append(" 3 ");
                case 2 -> table.append(" 2 ");
                case 1 -> table.append(" 1 ");
                case 0 -> table.append(" 0 ");
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
                        /*case 0 -> table.append("    ₀ ");
                        case 1 -> table.append(" ₁ ");
                        case 2 -> table.append(" ₂ ");
                        case 3 -> table.append(" ₃ ");
                        case 4 -> table.append(" ₄ ");
                        case 5 -> table.append(" ₅ ");
                        case 6 -> table.append(" ₆ ");
                        case 7 -> table.append(" ₇ ");
                        case 8 -> table.append(" ₈ ");*/
                        case 0 -> table.append("    0 ");
                        case 1 -> table.append(" 1 ");
                        case 2 -> table.append(" 2 ");
                        case 3 -> table.append(" 3 ");
                        case 4 -> table.append(" 4 ");
                        case 5 -> table.append(" 5 ");
                        case 6 -> table.append(" 6 ");
                        case 7 -> table.append(" 7 ");
                        case 8 -> table.append(" 8 ");
                    }
                }
            }
            table.append(ANSI_RESET + "  \n");
        }
        table.append("              Board             \n");
        return table.toString();
    }

    /** <p> return a string that rapresent the TUI rapresentation of the passed Bookshelf </p>
     */
    public String getBookshelfString(LocalBookshelf bookshelf) {
        StringBuilder library = new StringBuilder();

        library.append("\n");
        for (int i = nRowBookshelf - 1; i > -3; i--) {
            switch (i) {
                /*case 5 -> library.append("   ₅");
                case 4 -> library.append("   ₄");
                case 3 -> library.append("   ₃");
                case 2 -> library.append("   ₂");
                case 1 -> library.append("   ₁");
                case 0 -> library.append("   ₀");*/
                case 5 -> library.append("   5");
                case 4 -> library.append("   4");
                case 3 -> library.append("   3");
                case 2 -> library.append("   2");
                case 1 -> library.append("   1");
                case 0 -> library.append("   0");
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
                        /*case 0 -> library.append("       ⁰ ");
                        case 1 -> library.append("  ¹ ");
                        case 2 -> library.append("  ² ");
                        case 3 -> library.append("  ³ ");
                        case 4 -> library.append("  ⁴  ");*/
                        case 0 -> library.append("       0 ");
                        case 1 -> library.append("  1 ");
                        case 2 -> library.append("  2 ");
                        case 3 -> library.append("  3 ");
                        case 4 -> library.append("  4  ");
                    }
                }
            }
            library.append(ANSI_RESET + "\n");
        }
        String str = bookshelf.name + "'s Bookshelf";
        if(str.length()>22)
            str = " ".repeat(4) +str.substring(0,18)+"...";
        else
            str = " ".repeat(4) + str + " ".repeat(23 - str.length() - 1);;
        library.append(str);
        return library.toString();
    }

    /** <p> return a string that rapresent the TUI rapresentation of the passed Hand </p>
     */
    public String getHandString(LocalHand hand) {
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
        return string.toString();
    }

    /** <p> return a string that rapresent the TUI rapresentation of the passed DataCard </p>
     */
    public String getPersonalCardString(DataCard dataCard) {
        StringBuilder card = new StringBuilder();
        Coordinates coordinates = new Coordinates();

        card.append("\n\n");
        for (int i = nRowBookshelf; i > -3; i--) {
            switch (i) {
               /* case 5 -> card.append("  ₅");
                case 4 -> card.append("  ₄");
                case 3 -> card.append("  ₃");
                case 2 -> card.append("  ₂");
                case 1 -> card.append("  ₁");
                case 0 -> card.append("  ₀");*/
                case 5 -> card.append("  5");
                case 4 -> card.append("  4");
                case 3 -> card.append("  3");
                case 2 -> card.append("  2");
                case 1 -> card.append("  1");
                case 0 -> card.append("  0");
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
                    } else {
                        card.append(ANSI_RESET + "   " + DEFAULT_BACKGROUND);
                    }
                    if(j == nColumnBookshelf - 1) {
                        card.append(ANSI_RESET + "│ ");
                    } else {
                        card.append(ANSI_RESET + "│");
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
                        /*case 0 -> card.append("      ⁰ ");
                        case 1 -> card.append("  ¹ ");
                        case 2 -> card.append("  ² ");
                        case 3 -> card.append("  ³ ");
                        case 4 -> card.append("  ⁴  ");*/
                        case 0 -> card.append("      0 ");
                        case 1 -> card.append("  1 ");
                        case 2 -> card.append("  2 ");
                        case 3 -> card.append("  3 ");
                        case 4 -> card.append("  4  ");
                    }
                }
            }
            card.append(ANSI_RESET + "\n");
        }
        card.append("      PersonalGoalCard     \n");
        return card.toString();
    }

    /** <p> return a string that rapresent the TUI rapresentation of the passed CommonGoalCard with its tokens</p>
     */
    public String getCommonCardString(LocalCommonCard localCommonCard) {
        StringBuilder commonCard = new StringBuilder();
        commonCard.append("\n\n                           \n");

        //card image
        String card = getCommonCardImageString(localCommonCard);
        String[] cardResult  = card.split("\n");

        //token
        String token = switch (localCommonCard.tokenList.get(0).getValue()) {
            case 2 -> token2;
            case 4 -> token4;
            case 6 -> token6;
            case 8 -> token8;
            default -> "";
        };
        String[] tokenResult  = token.split("\n");

        //generate string
        String string;
        for(int i=0; i<cardResult.length || i<tokenResult.length; i++) {
            string = cardResult[i] + tokenResult[i] + "\n";
            commonCard.append(string);
        }
        commonCard.append("    CommonGoalCard         \n");
        return commonCard.toString();
    }

    /** <p> return a  TUI rapresentation of the image of the passed CommonGoalCard</p>
     */
    public String getCommonCardImageString(LocalCommonCard localCommonCard) {
        String card = "";

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

    /** <p> return a  TUI rapresentation of the description of the passed CommonGoalCard</p>
     */
    public String getCommonCardDescriptionString(LocalCommonCard localCommonCard){
        return switch (localCommonCard.type) {
            case 0 -> """

                    Six groups each containing at least
                    2 tiles of the same type (not necessarily
                    in the depicted shape).
                    The tiles of one group can be different
                    from those of another group.""";
            case 1 -> """

                    Four groups each containing at least
                    4 tiles of the same type (not necessarily
                    in the depicted shape).
                    The tiles of one group can be different
                    from those of another group.""";
            case 2 -> """

                    Four tiles of the same type in the four
                    corners of the bookshelf.""";
            case 3 -> """

                    Two groups each containing 4 tiles of
                    the same type in a 2x2 square. The tiles
                    of one square can be different from
                    those of the other square.""";
            case 4 -> """

                    Three columns each formed by 6 tiles
                    of maximum three different types. One
                    column can show the same or a different
                    combination of another column.""";
            case 5 -> """

                    Eight tiles of the same type. There’s no
                    restriction about the position of these
                    tiles.""";
            case 6 -> """

                    Five tiles of the same type forming a
                    diagonal.""";
            case 7 -> """

                    Four lines each formed by 5 tiles of
                    maximum three different types. One
                    line can show the same or a different
                    combination of another line.""";
            case 8 -> """

                    Two columns each formed by 6
                    different types of tiles.""";
            case 9 -> """

                    Two lines each formed by 5 different
                    types of tiles. One line can show the
                    same or a different combination of the
                    other line.""";
            case 10 -> """
                                            
                    Five tiles of the same type forming an X.""";
            case 11 -> """

                    Five columns of increasing or decreasing
                    height. Starting from the first column on
                    the left or on the right, each next column
                    must be made of exactly one more tile.
                    Tiles can be of any type.""";
            default -> "";
        };
    }

}
