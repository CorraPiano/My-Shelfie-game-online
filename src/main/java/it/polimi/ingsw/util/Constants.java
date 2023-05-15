package it.polimi.ingsw.util;

public class Constants {

    public static final int nRowBoard = 9;
    public static final int nColumnBoard = 9;
    public static final int nRowBookshelf = 6;
    public static final int nColumnBookshelf = 5;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PINK = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String DEFAULT_BACKGROUND = "";
    public static final String BLACK_BACKGROUND = "\u001B[40m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";
    public static final String PINK_BACKGROUND = "\u001B[45m";
    public static final String CYAN_BACKGROUND = "\u001B[46m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";

    public static final String BROWN_BACKGROUND = "\u001B[48;5;94m";

    public static void main(String[] args) {
        System.out.println(BROWN_BACKGROUND + "   ");
    }


    /*
    public static final String DEFAULT_BACKGROUND = "";
    public static final String BLACK_BACKGROUND = "\u001B[100m";
    public static final String GREEN_BACKGROUND = "\u001B[102m";
    public static final String YELLOW_BACKGROUND = "\u001B[103m";
    public static final String BLUE_BACKGROUND = "\u001B[104m";
    public static final String PINK_BACKGROUND = "\u001B[105m";
    public static final String CYAN_BACKGROUND = "\u001B[106m";
    public static final String WHITE_BACKGROUND = "\u001B[107m";
     */

    public static final String LOGO1  =
            " ╔╦╗┬ ┬  ╔═╗┬ ┬┌─┐┬  ┌─┐┬┌─┐\n" +
            " ║║║└┬┘  ╚═╗├─┤├┤ │  ├┤ │├┤ \n" +
            " ╩ ╩ ┴   ╚═╝┴ ┴└─┘┴─┘└  ┴└─┘\n";

    public static final String LOGO2  =
            "888b     d888             .d8888b.  888               888  .d888 d8b          \n" +
            "8888b   d8888            d88P  Y88b 888               888 d88P\"  Y8P          \n" +
            "88888b.d88888            Y88b.      888               888 888                 \n" +
            "888Y88888P888 888  888    \"Y888b.   88888b.   .d88b.  888 888888 888  .d88b.  \n" +
            "888 Y888P 888 888  888       \"Y88b. 888 \"88b d8P  Y8b 888 888    888 d8P  Y8b \n" +
            "888  Y8P  888 888  888         \"888 888  888 88888888 888 888    888 88888888 \n" +
            "888   \"   888 Y88b 888   Y88b  d88P 888  888 Y8b.     888 888    888 Y8b.     \n" +
            "888       888  \"Y88888    \"Y8888P\"  888  888  \"Y8888  888 888    888  \"Y8888  \n" +
            "                   888                                                          \n" +
            "              Y8b d88P                                                        \n" +
            "               \"Y88P\"";

    public static final String LOGO3  =
            "███╗   ███╗██╗   ██╗  ███████╗██╗  ██╗███████╗██╗     ███████╗██╗███████╗\n" +
            "████╗ ████║╚██╗ ██╔╝  ██╔════╝██║  ██║██╔════╝██║     ██╔════╝██║██╔════╝\n" +
            "██╔████╔██║ ╚████╔╝   ███████╗███████║█████╗  ██║     █████╗  ██║█████╗  \n" +
            "██║╚██╔╝██║  ╚██╔╝    ╚════██║██╔══██║██╔══╝  ██║     ██╔══╝  ██║██╔══╝  \n" +
            "██║ ╚═╝ ██║   ██║     ███████║██║  ██║███████╗███████╗██║     ██║███████╗\n" +
            "╚═╝     ╚═╝   ╚═╝     ╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝╚═╝     ╚═╝╚══════╝\n" +
            "                                                                         ";


    public static final String commonCard1 =
            " ┌─────────────────┐ \n" +
            " │                 │ \n" +
            " │                 │ \n" +
            " │       " + BLACK_BACKGROUND + " = " + ANSI_RESET + "       │ \n" +
            " │       " + BLACK_BACKGROUND + " = " + ANSI_RESET + "       │ \n" +
            " │        x6       │ \n" +
            " │                 │ \n" +
            " └─────────────────┘ \n";

    public static final String commonCard2 =
            " ┌─────────────────┐ \n" +
            " │                 │ \n" +
            " │    " + BLACK_BACKGROUND + " = " + ANSI_RESET + "          │ \n" +
            " │    " + BLACK_BACKGROUND + " = " + ANSI_RESET + "          │ \n" +
            " │    " + BLACK_BACKGROUND + " = " + ANSI_RESET + "  x4      │ \n" +
            " │    " + BLACK_BACKGROUND + " = " + ANSI_RESET + "          │ \n" +
            " │                 │ \n" +
            " └─────────────────┘ \n";

    public static final String commonCard3 =
            " ┌─────────────────┐ \n" +
            " │                 │ \n" +
            " │ " + BLACK_BACKGROUND + " = " + ANSI_RESET + " -  -  - " + BLACK_BACKGROUND + " = " + ANSI_RESET + " │ \n" +
            " │  |           |  │ \n" +
            " │  |           |  │ \n" +
            " │  |           |  │ \n" +
            " │ " + BLACK_BACKGROUND + " = " + ANSI_RESET + " -  -  - " + BLACK_BACKGROUND + " = " + ANSI_RESET + " │ \n" +
            " └─────────────────┘ \n";

    public static final String commonCard4 =
            " ┌─────────────────┐ \n" +
            " │                 │ \n" +
            " │                 │ \n" +
            " │      " + BLACK_BACKGROUND + " = = " + ANSI_RESET + "      │ \n" +
            " │      " + BLACK_BACKGROUND + " = = " + ANSI_RESET + "      │ \n" +
            " │       x2        │ \n" +
            " │                 │ \n" +
            " └─────────────────┘ \n";

    public static final String commonCard5 =
            " ┌─────────────────┐ \n" +
            " │ " + BLACK_BACKGROUND + "   " + ANSI_RESET + "             │ \n" +
            " │ " + BLACK_BACKGROUND + "   " + ANSI_RESET + "             │ \n" +
            " │ " + BLACK_BACKGROUND + "   " + ANSI_RESET + "   max 3 " + BLACK_BACKGROUND + " ≠ " + ANSI_RESET + " │ \n" +
            " │ " + BLACK_BACKGROUND + "   " + ANSI_RESET + "             │ \n" +
            " │ " + BLACK_BACKGROUND + "   " + ANSI_RESET + "   x3        │ \n" +
            " │ " + BLACK_BACKGROUND + "   " + ANSI_RESET + "             │ \n" +
            " └─────────────────┘ \n";

    public static final String commonCard6 =
            " ┌─────────────────┐ \n" +
            " │                 │ \n" +
            " │    " + BLACK_BACKGROUND + " = " + ANSI_RESET + "   " + BLACK_BACKGROUND + " = " + ANSI_RESET + "    │ \n" +
            " │                 │ \n" +
            " │ " + BLACK_BACKGROUND + " = " + ANSI_RESET + "   " + BLACK_BACKGROUND + " = " + ANSI_RESET + "   " + BLACK_BACKGROUND + " = " + ANSI_RESET + " │ \n" +
            " │                 │ \n" +
            " │ " + BLACK_BACKGROUND + " = " + ANSI_RESET + "   " + BLACK_BACKGROUND + " = " + ANSI_RESET + "   " + BLACK_BACKGROUND + " = " + ANSI_RESET + " │ \n" +
            " └─────────────────┘ \n";

    public static final String commonCard7 =
            " ┌─────────────────┐ \n" +
            " │                 │ \n" +
            " │ " + BLACK_BACKGROUND + " = " + ANSI_RESET + "             │ \n" +
            " │    " + BLACK_BACKGROUND + " = " + ANSI_RESET + "          │ \n" +
            " │       " + BLACK_BACKGROUND + " = " + ANSI_RESET + "       │ \n" +
            " │          " + BLACK_BACKGROUND + " = " + ANSI_RESET + "    │ \n" +
            " │             " + BLACK_BACKGROUND + " = " + ANSI_RESET + " │ \n" +
            " └─────────────────┘ \n";

    public static final String commonCard8 =
            " ┌─────────────────┐ \n" +
            " │                 │ \n" +
            " │ " + BLACK_BACKGROUND + "               " + ANSI_RESET + " │ \n" +
            " │        x4       │ \n" +
            " │                 │ \n" +
            " │    max 3 " + BLACK_BACKGROUND + " ≠ " + ANSI_RESET + "    │ \n" +
            " │                 │ \n" +
            " └─────────────────┘ \n";

    public static final String commonCard9 =
            " ┌─────────────────┐ \n" +
            " │    " + BLACK_BACKGROUND + " ≠ " + ANSI_RESET + "          │ \n" +
            " │    " + BLACK_BACKGROUND + " ≠ " + ANSI_RESET + "          │ \n" +
            " │    " + BLACK_BACKGROUND + " ≠ " + ANSI_RESET + "          │ \n" +
            " │    " + BLACK_BACKGROUND + " ≠ " + ANSI_RESET + "  x2      │ \n" +
            " │    " + BLACK_BACKGROUND + " ≠ " + ANSI_RESET + "          │ \n" +
            " │    " + BLACK_BACKGROUND + " ≠ " + ANSI_RESET + "          │ \n" +
            " └─────────────────┘ \n";

    public static final String commonCard10 =
            " ┌─────────────────┐ \n" +
            " │                 │ \n" +
            " │                 │ \n" +
            " │ " + BLACK_BACKGROUND + " ≠  ≠  ≠  ≠  ≠ " + ANSI_RESET + " │ \n" +
            " │        x2       │ \n" +
            " │                 │ \n" +
            " │                 │ \n" +
            " └─────────────────┘ \n";

    public static final String commonCard11 =
            " ┌─────────────────┐ \n" +
            " │                 │ \n" +
            " │    " + BLACK_BACKGROUND + " = " + ANSI_RESET + "   " + BLACK_BACKGROUND + " = " + ANSI_RESET + "    │ \n" +
            " │       " + BLACK_BACKGROUND + " = " + ANSI_RESET + "       │ \n" +
            " │    " + BLACK_BACKGROUND + " = " + ANSI_RESET + "   " + BLACK_BACKGROUND + " = " + ANSI_RESET + "    │ \n" +
            " │                 │ \n" +
            " │                 │ \n" +
            " └─────────────────┘ \n";

    public static final String commonCard12 =
            " ┌─────────────────┐ \n" +
            " │                 │ \n" +
            " │ " + BLACK_BACKGROUND + "   " + ANSI_RESET + "             │ \n" +
            " │ " + BLACK_BACKGROUND + "      " + ANSI_RESET + "          │ \n" +
            " │ " + BLACK_BACKGROUND + "         " + ANSI_RESET + "       │ \n" +
            " │ " + BLACK_BACKGROUND + "            " + ANSI_RESET + "    │ \n" +
            " │ " + BLACK_BACKGROUND + "               " + ANSI_RESET + " │ \n" +
            " └─────────────────┘ \n";

    public static final String token4 =
            "┌───┐ \n" +
            "│ 4 │ \n" +
            "└───┘ \n" +
            "      \n" +
            "      \n" +
            "      \n" +
            "      \n" +
            "      \n";

    public static final String token6 =
            "┌───┐ \n" +
            "│ 6 │ \n" +
            "└───┘ \n" +
            "      \n" +
            "      \n" +
            "      \n" +
            "      \n" +
            "      \n";

    public static final String token8 =
            "┌───┐ \n" +
            "│ 8 │ \n" +
            "└───┘ \n" +
            "      \n" +
            "      \n" +
            "      \n" +
            "      \n" +
            "      \n";
}
