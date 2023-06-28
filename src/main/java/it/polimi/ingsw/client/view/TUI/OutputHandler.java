package it.polimi.ingsw.client.view.TUI;

import it.polimi.ingsw.client.ClientPhase;
import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.model.DataCard;
import it.polimi.ingsw.model.GameMode;
import java.util.ArrayList;
import java.util.Map;

import static it.polimi.ingsw.model.GameMode.EXPERT;
import static it.polimi.ingsw.util.Constants.*;

/** <p> This class require a ModelView and can be used to print the various scene of the game according to the ModelView</p>
 **/
public class OutputHandler {

    private ModelView modelView;
    private final TUIPrinter TUIprinter;

    public OutputHandler(){
        this.modelView = null;
        this.TUIprinter = new TUIPrinter();
    }

    public void bindModelView(ModelView modelView){
        this.modelView = modelView;
    }

    /** <p> print nicely the list of avaible gameplay received in input</p>
     **/
    public void showList(ArrayList<LocalGame> gameslist){
        System.out.println();
        if(gameslist.isEmpty()) {
            showInformation("there are not avaiable gameplay!");
            //System.out.println(ANSI_YELLOW + "<<INFOMATION>> " + ANSI_RESET + "there are not avaiable gameplay!"+ANSI_RESET);
            showInformation("You can create a new gameplay with the CREATE command :");
            //System.out.println(ANSI_YELLOW + "<<INFOMATION>> " + ANSI_RESET + "You can create a new gameplay with the CREATE command :"+ANSI_RESET);
        }
        else {
            showInformation("Here is the list of the avaiable gameplays:");
            //System.out.println(ANSI_YELLOW + "<<INFOMATION>> " + ANSI_RESET + "Here is the list of the avaiable gameplays:" + ANSI_RESET);
            for (LocalGame lg : gameslist) {
                System.out.print("\t\t\t"+" -> ");
                System.out.print("\t"+ANSI_CYAN + "GameID: " + ANSI_RESET + lg.gameID + ", ");
                System.out.print("  "+ANSI_CYAN  + "gamemode: " + ANSI_RESET + lg.gameMode + ", ");
                System.out.print("  "+ANSI_CYAN + "N° players: " + ANSI_RESET + lg.maxPerson + ", ");
                System.out.print("  "+ANSI_CYAN  + "N° actual players: " + ANSI_RESET + lg.currPerson + " ");
                System.out.print("  "+ANSI_RESET + "\n");
            }
            showInformation("You can join one of this game with the JOIN command");
            //System.out.println("\n"+ANSI_YELLOW + "<<INFOMATION>>" + ANSI_RESET + "You can join one of this game with the JOIN command" + ANSI_RESET);
        }
        System.out.println();
    }

    /** <p> print the intro of home page </p>
     **/
    public void showHomeIntro() {
        //➤
        //System.out.println(BROWN_FOREGROUND + "\n───────────────────────────────── ❮❰♦❱❯ ─────────────────────────────────" + ANSI_RESET);
        System.out.println(BROWN_FOREGROUND + "\n───────────────────────────────── << >> ─────────────────────────────────" + ANSI_RESET);

        System.out.println(BROWN_FOREGROUND + MYSHELFIE_LOBBY + ANSI_RESET + "\n");
        System.out.println(
                ANSI_YELLOW + "<INSTRUCTION❯> " + ANSI_RESET + "Here you can the use the following commands with their format:\n" +
                        "              -> CREATE <username> <gamemode> <number_of_players>: to create a new game\n" +
                        "                        (EASY -> gamemode = 0, EXPERT -> gamemode = 1)\n" +
                        "              -> JOIN <username> <gameID>: to join an existing game\n" +
                        "              -> LIST: to have the list of the current free games");
    }

    /** <p> print the intro of the game page </p>
     **/
    public void showGameIntro(){
        //System.out.println(BROWN_FOREGROUND + "\n\n─────────────────────────────────────────────────── ❮❰♦❱❯ ───────────────────────────────────────────────────\n" + ANSI_RESET);
        System.out.println(BROWN_FOREGROUND + "\n\n─────────────────────────────────────────────────── << >> ───────────────────────────────────────────────────\n" + ANSI_RESET);

        System.out.println(BROWN_FOREGROUND + LOGO6);
        System.out.println("Player, it’s time to reorganize your library and put your favorite objects back in place!\n" +
                "Will you be able to do it faster than other players, satisfying the required patterns?\n" +
                "Pick items from the board, insert them in your bookshelf, making them fall from the top,\n" +
                "while trying to recreate the patterns of your personal or common target.\n" + ANSI_RESET);
    }

    /** <p> print the information about the commonGoalCard, if they exist.
     *  Instead print nothing. </p>
     **/
    public void showCommonCards(){
        GameMode gameMode = modelView.getGameMode();
        if(gameMode.equals(GameMode.EXPERT)) {
            System.out.println(BROWN_FOREGROUND + "Here are the current common cards and their description:\n" + ANSI_RESET);
            showCommonCard(modelView.getCommonCards().get(0));
            showCommonCard(modelView.getCommonCards().get(1));
        } else {
            System.out.println(BROWN_FOREGROUND + "There are no CommonGoalCards in EASY game-mode"+ ANSI_RESET);
        }
    }

    private void showCommonCard(LocalCommonCard localCommonCard) {
        String description = TUIprinter.getCommonCardDescriptionString(localCommonCard);
        StringBuilder output = new StringBuilder();
        String[] commonCard = TUIprinter.getCommonCardImageString(localCommonCard).split("\n");
        String[] cardDescription = description.split("\n");
        String str;
        for(int i=0; i<commonCard.length; i++){
            if(i<cardDescription.length){
                str = ANSI_RESET + commonCard[i] + BROWN_FOREGROUND + cardDescription[i] + ANSI_RESET + "\n";
            } else {
                str = commonCard[i] + "\n";
            }
            output.append(str);
        }
        System.out.println(output);
    }

    public void showByeBye(){
        showInformation("Thank You for playing!");
        showInformation("See you soon!");
        //System.out.println(ANSI_YELLOW + "<<GOODBYE>> " + ANSI_RESET + "Thank You for playing!");
        //System.out.println(ANSI_YELLOW + "<<GOODBYE>> " + ANSI_RESET + "See you soon!");
    }


    /** <p> Print the main scene of the game according to the data insider ModelView. Are printed:</p>
     *  <ul>
     *       <li> the list of player with their points </li>
     *       <li> the board </li>
     *       <li> the commonGoalCards </li>
     *       <li> the own personalGoalCard </li>
     *       <li> all the bookshelfs </li>
     *  </ul>
     **/
    public void showGame() {
        //recover data from ModelView
        ArrayList<LocalPlayer> localPlayerList = modelView.getLocalPlayerList();
        Map<String, LocalBookshelf> localBookshelfs = modelView.getLocalBookshelfs();
        LocalBoard localBoard = modelView.getLocalBoard();
        ArrayList<LocalCommonCard> commonCards = modelView.getCommonCards();
        DataCard dataCard = modelView.getDataCard();
        GameMode gameMode = modelView.getGameMode();

        //decoration
        //System.out.println(BROWN_FOREGROUND + "\n\n─────────────────────────────────────────────────── ❮❰♦❱❯ ───────────────────────────────────────────────────" + ANSI_RESET);
        System.out.println(BROWN_FOREGROUND + "\n\n─────────────────────────────────────────────────── << >> ───────────────────────────────────────────────────" + ANSI_RESET);

        //player list
        showPlayerTable(localPlayerList);
        //board anc cards
        showBoardAndCards(localBoard,commonCards,dataCard, gameMode);
        //bookshelf
        showBookshelfs( localPlayerList,  localBookshelfs);
        System.out.println();
    }

    private void showPlayerTable(ArrayList<LocalPlayer> localPlayerList){
        //calculate size
        int lenName=0; int lenState=0;
        for (LocalPlayer p : localPlayerList){
            if(p.name.length()>lenName)
                lenName=p.name.length();
            if(p.playerState.toString().length()>lenState)
                lenState=p.playerState.toString().length();
        }
        if(lenName>40)
            lenName=40;
        lenName = lenName+6;
        lenState = lenState+4;

        //print of the table
        String str; int i=0;
        System.out.println(" ┌"+"─".repeat(lenName+lenState+35+9)+"┐");
        str =  " │"+putSpace(8) + ANSI_YELLOW + "NAME" + putSpace(lenName-4) + "PUNTI" + putSpace(3) + "STATO";
        str = str + putSpace(lenState-5) + "TOKEN1" + putSpace(3) + "TOKEN2" + ANSI_RESET + putSpace(4+9)+"│";
        System.out.println(str);
        for (LocalPlayer p : localPlayerList) {
            String name = p.name;
            if(name.length()>40)
                name = p.name.substring(0,36)+"...";
            String color = ANSI_RESET;
            if(modelView.getCurrentPlayer().equals(name))
                color = ANSI_CYAN;
            i++;
            str =  " │" + putSpace(4) + ANSI_YELLOW + i + ")" + ANSI_RESET ;
            str = str + putSpace(2) + color + name + ANSI_RESET;
            str = str + putSpace(lenName-name.length()) + color + p.points + ANSI_RESET;
            str = str + putSpace(8-String.valueOf(p.points).length()) + color + p.playerState + ANSI_RESET;
            int ps =  p.playerState.toString().length();
            if(p.token1!=null)
                str = str + putSpace(lenState-ps+2) + color + p.token1.getValue() + ANSI_RESET;
            else
                str = str + putSpace(lenState-ps) + color  + "  " + "x" + ANSI_RESET;
            if(p.token2!=null)
                str = str + putSpace(6) + color + "  " + p.token2.getValue() + ANSI_RESET;
            else
                str = str + putSpace(6) + color + "  " + "x" + ANSI_RESET;
            if(modelView.getLocalName().equals(p.name))
                str = str + putSpace(5) + ANSI_YELLOW+ "<- YOU" + putSpace(5) + ANSI_RESET +"│";
            else
                str = str + putSpace(7+9) +"│";
            System.out.println(str);
        }
        System.out.println(" └"+"─".repeat(lenName+lenState+35+9)+"┘");
    }

    private void showBoardAndCards(LocalBoard localBoard,ArrayList<LocalCommonCard> commonCards,DataCard dataCard,GameMode gameMode) {
        StringBuilder output = new StringBuilder();
        String[] board = TUIprinter.getBoardString(localBoard).split("\n");
        String[] personalCard = TUIprinter.getPersonalCardString(dataCard).split("\n");
        if (gameMode.equals(EXPERT)){
            String[] commonCard1 = TUIprinter.getCommonCardString(commonCards.get(0)).split("\n");
            String[] commonCard2 = TUIprinter.getCommonCardString(commonCards.get(1)).split("\n");
            for(int i=0; i<board.length; i++){
                String str = board[i] + commonCard1[i] + commonCard2[i] + personalCard[i] + "\n";
                output.append(str);
            }
        } else{
            for(int i=0; i<board.length; i++){
                String str=board[i] + personalCard[i] + "\n";
                output.append(str);
            }
        }
        System.out.println(output);
    }

    private void showBookshelfs(ArrayList<LocalPlayer> localPlayerList, Map<String, LocalBookshelf> localBookshelfs){
        StringBuilder output = new StringBuilder();
        int i,j;
        //bookshelfs
        String[][] library = new String[localBookshelfs.size()][];
        for(i=0; i<localBookshelfs.size(); i++){
            String name = localPlayerList.get(i).name;
            library[i] = TUIprinter.getBookshelfString(localBookshelfs.get(name)).split("\n");
        }
        for(j=0; j<library[0].length; j++){
            for(i=0; i<localBookshelfs.size(); i++){
                output.append(library[i][j]);
            }
            output.append("\n");
        }

        System.out.print(output);
    }

    /**  show the end game statistic and classification
     **/
    public void showEndGame() {
        //recover data from ModelView
        Map<String, LocalBookshelf> localBookshelfs = modelView.getLocalBookshelfs();
        ArrayList<LocalPlayer> localPlayerList = modelView.getLocalPlayerList();

        //decoration
        //System.out.println(BROWN_FOREGROUND + "\n\n────────────────────────────────────────── ❮❰STATISTICS & PODIUM❱❯ ──────────────────────────────────────────" + ANSI_RESET);
        System.out.println(BROWN_FOREGROUND + "\n\n────────────────────────────────────────── << STATISTICS & PODIUM >> ──────────────────────────────────────────" + ANSI_RESET);

        //classification
        showPodium();
        //players list
        showPlayerTable(localPlayerList);
        //boolshelfs
        showBookshelfs(localPlayerList,localBookshelfs);
        //personal goal card
        showPersonalCard(localPlayerList);
    }

    private void showPersonalCard(ArrayList<LocalPlayer> localPlayerList){
        StringBuilder output = new StringBuilder();
        int i,j;
        //bookshelfs
        String[][] personalCard = new String[localPlayerList.size()][];
        for(i=0; i<localPlayerList.size(); i++){
            DataCard dataCard = new DataCard(localPlayerList.get(i).numPersonalCard);
            personalCard[i] = TUIprinter.getPersonalCardString(dataCard).split("\n");
        }
        for(j=0; j<personalCard[1].length; j++){
            for(i=0; i<localPlayerList.size(); i++){
                output.append(personalCard[i][j]);
            }
            output.append("\n");
        }
        System.out.println(output);
    }

    private void showPodium(){
        ArrayList<LocalPlayer> localPlayers = modelView.getLocalPlayerList();

        //devo scrivere vittoria a chi ha vinto
        StringBuilder output = new StringBuilder();
        String winnerName = localPlayers.get(0).name;
        int winnerPointsVariable = localPlayers.get(0).points;
        int winnerPoints = localPlayers.get(0).points;
        int digitsCounter = 0;

        if(winnerPointsVariable == 0){
            digitsCounter = 1;
        } else {
            while (winnerPointsVariable >= 1) {
                winnerPointsVariable = winnerPointsVariable / 10;
                digitsCounter++;
            }
        }

        String c = "─";
        String repeated = c.repeat(winnerName.length() + 13 + digitsCounter);
        String str = "─┐\n" + "│ The winner is " + ANSI_CYAN + winnerName + ANSI_RESET + " with " + winnerPoints + " points │\n" + "└───────────────";

        output.append("┌───────────────");
        output.append(repeated);
        output.append(str);
        output.append(repeated);
        output.append("─┘\n");

        //devo fare una classifica con gli altri giocatori (secondo terzo quarto posto)

        //" ➤"
        str = " -> The second position goes to " + ANSI_CYAN + localPlayers.get(1).name + ANSI_RESET + " with " + localPlayers.get(1).points + " points\n";
        output.append(str);
        //output.append(" ➤ The second position goes to " + ANSI_CYAN + localPlayers.get(1).name + ANSI_RESET + " with " + localPlayers.get(1).points + " points\n");
        if(localPlayers.size()>2){
            str = " -> The third position goes to " + ANSI_CYAN + localPlayers.get(2).name + ANSI_RESET + " with " + localPlayers.get(2).points + " points\n";
            output.append(str);
            //output.append(" ➤ The third position goes to " + ANSI_CYAN + localPlayers.get(2).name + ANSI_RESET + " with " + localPlayers.get(2).points + " points\n");
        }
        if(localPlayers.size() == 4){
            str = " -> The fourth position goes to " + ANSI_CYAN + localPlayers.get(3).name + ANSI_RESET + " with " + localPlayers.get(3).points + " points\n";
            output.append(str);
            //output.append(" ➤ The fourth position goes to " + ANSI_CYAN + localPlayers.get(3).name + ANSI_RESET + " with " + localPlayers.get(3).points + " points\n");
        }
        System.out.println(output + "\n");

    }

    /**  show the board, the bookshelf of the username passed as argument and the hand of the current ModelView
     *
     **/
    public void showCurrentAction(String name){
        LocalBoard localBoard = modelView.getLocalBoard();
        LocalBookshelf localBookshelf = modelView.getLocalBookshelfs().get(name);
        LocalHand localHand = modelView.getLocalHand();

        StringBuilder output = new StringBuilder();
        String[] board = TUIprinter.getBoardString(localBoard).split("\n");
        String[] library = TUIprinter.getBookshelfString(localBookshelf).split("\n");
        String hand = TUIprinter.getHandString(localHand);
        String str;
        for(int i=0; i<board.length; i++){
            if(i==3) {
                str = board[i] + library[i - 2] + "  " + hand;
            }
            else if (i>2) {
                str = board[i] + library[i-2] + "\n";
            }
            else {
                str = board[i] + "\n";
            }
            output.append(str);
        }
        System.out.println(output);
    }

    /**  show the bookshelf of the username passed as argument of the current ModelView
     *
     **/
    public void showBookshelf(String name) {
        LocalBookshelf bookshelf = modelView.getLocalBookshelfs().get(name);
        System.out.println(TUIprinter.getBookshelfString(bookshelf)+"\n");
    }

    public void showHelp(ClientPhase clientPhase){
        switch (clientPhase) {
            case HOME -> {
                // " ➤ "
                System.out.println(BROWN_FOREGROUND + "\nYou can start a new game or join an existing one" + ANSI_RESET);
                System.out.println(BROWN_FOREGROUND + "Here is the list of the commands:\n" +
                        " -> " + ANSI_GREEN + "LIST" + BROWN_FOREGROUND + ": use this command to receive the list of the avaiable matches \n" +
                        " -> " + ANSI_GREEN + "CREATE [name] [gamemode] [number players]" + BROWN_FOREGROUND + ": use this command to create a match.\n"+
                                " \t\t The number of players should be between 2 and 4. The possible gamemodes are: \n" +
                                " \t\t - 'O' or 'EASY' for playing without common goal card \n"+
                                " \t\t - '1' or 'EXPERT' for playing with common goal card \n"+
                        " -> " + ANSI_GREEN + "JOIN [name] [game ID]" + BROWN_FOREGROUND + ": use this command to join an existing game. You can get the game ID with LIST command \n" +
                        " -> " + ANSI_GREEN + "RECONNECT [name] [game ID]" + BROWN_FOREGROUND + ": use this command to reconnect to the game in which you were playing \n" +
                        " -> " + ANSI_GREEN + "EXIT" + BROWN_FOREGROUND + ": use this command to close the app \n" +
                        " -> " + ANSI_GREEN + "HELP" + BROWN_FOREGROUND + ": use this command for a description of the avaiable commands \n" + ANSI_RESET);
            }
            case LOBBY -> {
                System.out.println(BROWN_FOREGROUND + "\nYou are waiting for the other players" + ANSI_RESET);
                System.out.println(BROWN_FOREGROUND + "Here is the list of the commands:\n" +
                        " -> " + ANSI_GREEN + "LEAVE" + BROWN_FOREGROUND + ": with this command you will end the game FOR ALL THE PLAYERS \n" +
                        " -> " + ANSI_GREEN + "HELP" + BROWN_FOREGROUND + ": use this command for a description of the avaiable commands \n" + ANSI_RESET);
            }
            case GAME -> {
                System.out.println(BROWN_FOREGROUND + "\nYou are playing the game" + ANSI_RESET);
                System.out.println(BROWN_FOREGROUND + "Here is the list of the commands:\n" +
                        " -> " + ANSI_GREEN + "PICK [row] [column]" + BROWN_FOREGROUND + ": use this command in order to take an Item from the board\n" +
                        " -> " + ANSI_GREEN + "PUT [column]" + BROWN_FOREGROUND + ": if you have some Items in your hand you can put them in the bookshelf\n" +
                        " -> " + ANSI_GREEN + "ORDER [int] {int} {int} " + BROWN_FOREGROUND + ": the items in the hand will be put according with their position in the list\n" +
                        " -> " + ANSI_GREEN + "UNDO" + BROWN_FOREGROUND + ": if you realized that you made a mistake in taking the item you can redo the action\n" +
                        " -> " + ANSI_GREEN + "COMMON" + BROWN_FOREGROUND + ": use this command tu see the description of the current common cards\n" +
                        " -> " + ANSI_GREEN + "CHAT" + BROWN_FOREGROUND + ": with this command you can open the chat\n" +
                        " -> " + ANSI_GREEN + "LEAVE" + BROWN_FOREGROUND + ": with this command you will leave the game \n" +
                        " -> " + ANSI_GREEN + "HELP" + BROWN_FOREGROUND + ": use this command for a description of the avaiable commands \n" + ANSI_RESET);
            }
            case MATCH_RECONNECTION -> {
                System.out.println(BROWN_FOREGROUND + "\nYou have lost the connection! We are trying to reconnect you to the game" + ANSI_RESET);
                System.out.println(BROWN_FOREGROUND + "Here is the list of the commands:\n" +
                        " -> " + ANSI_GREEN + "EXIT" + BROWN_FOREGROUND + ": with this command you will stop the attempt to reconnect and close the app\n" +
                        " -> " + ANSI_GREEN + "HELP" + BROWN_FOREGROUND + ": use this command for a description of the avaiable commands \n" + ANSI_RESET);
            }
            case HOME_RECONNECTION -> {
                System.out.println(BROWN_FOREGROUND + "\nYou have lost the connection! We are trying to reconnect you to the server" + ANSI_RESET);
                System.out.println(BROWN_FOREGROUND + "Here is the list of the commands:\n" +
                        " -> " + ANSI_GREEN + "EXIT" + BROWN_FOREGROUND + ": with this command you will stop the attempt to reconnect and close the app \n" +
                        " -> " + ANSI_GREEN + "HELP" + BROWN_FOREGROUND + ": use this command for a description of the avaiable commands \n" + ANSI_RESET);
            }
            case CHAT -> {
                System.out.println(BROWN_FOREGROUND + "\nHere you can write to the other players" + ANSI_RESET);
                System.out.println(BROWN_FOREGROUND + "Everything you write will be sent to all the player after pushing ENTER\n" +
                        "Here is the list of the commands, which have to begin with '/':\n" +
                        " -> " + ANSI_GREEN + "/SEND [player name] [message]" + BROWN_FOREGROUND + ": send a message to a specific player\n" +
                        " -> " + ANSI_GREEN + "/BROADCAST  [message]" + BROWN_FOREGROUND + ": send a message to everyone \n" +
                        " -> " + ANSI_GREEN + "/CLOSE " + BROWN_FOREGROUND + ": use this command to close the chat \n" +
                        " -> " + ANSI_GREEN + "/HELP" + BROWN_FOREGROUND + ": use this command for a description of the avaiable commands \n" + ANSI_RESET);
            }
        }
    }

    public void showInformation(String content){
        //System.out.println(ANSI_YELLOW + "❮"+header+"❯ " + ANSI_RESET + content);
        System.out.println(ANSI_YELLOW + "<INFORMATION> " + ANSI_RESET + content);
    }
    public void showError(String content){
        //System.out.println(ANSI_YELLOW + "❮"+header+"❯ " + ANSI_RESET + content);
        System.out.println(ANSI_PINK + "<ERROR> " + ANSI_RESET + content);
    }
    public void showInstruction(String content){
        //System.out.println(ANSI_YELLOW + "❮"+header+"❯ " + ANSI_RESET + content);
        System.out.println(ANSI_YELLOW + "<INSTRUCTION> " + ANSI_RESET + content);
    }
    public void showInformation(String name, String content){
        //System.out.println(ANSI_YELLOW + "❮"+header+"❯ " + ANSI_CYAN + name + ANSI_RESET + ": " + content);
        if(modelView.getLocalName().equals(name))
            System.out.println(ANSI_YELLOW + "<INFORMATION> " + ANSI_CYAN + "you " + ANSI_RESET + content);
        else
            System.out.println(ANSI_YELLOW + "<INFORMATION> " + ANSI_CYAN + name+" " + ANSI_RESET + content);
    }

    public void showChat(){
        //System.out.println(BROWN_FOREGROUND + "\n\n───────────────────────────────────────────────── ❮❰ CHAT ❱❯ ─────────────────────────────────────────────────\n" + ANSI_RESET);
        System.out.println(BROWN_FOREGROUND + "\n\n───────────────────────────────────────────────── << CHAT >> ─────────────────────────────────────────────────\n" + ANSI_RESET);
        System.out.println(BROWN_FOREGROUND + "Here you can chat with the other players:\n" +
                " -> " + "for a public message simply write in the console \n" + BROWN_FOREGROUND +
                " -> " + ANSI_GREEN + "/SEND [player name] [message]" + BROWN_FOREGROUND + " for a private message use\n" +
                " -> " + ANSI_GREEN + "/CLOSE" + BROWN_FOREGROUND + " to close the chat\n" +
                " -> " + ANSI_GREEN + "/HELP" + BROWN_FOREGROUND + " to show the command list\n" + ANSI_RESET);
        for(ChatMessage message: modelView.getChatMessageList()) {
            showChatMessage(message);
        }
    }

    public void showChatMessage(ChatMessage message){
        if (message.all)
            showPublicChatMessage(message.sender, message.message);
        else
            showPrivateChatMessage(message.sender, message.receiver, message.message);
    }

    public void showPrivateChatMessage(String sender, String receiver, String message){
        if(modelView.getLocalName().equals(sender))
            System.out.println(ANSI_YELLOW + "<TO "+  receiver +"> " + ANSI_RESET + ": " + ANSI_GREEN + message + ANSI_RESET);
        else
            System.out.println(ANSI_YELLOW + "<FROM "+  sender +"> " + ANSI_RESET + ": " + ANSI_GREEN + message + ANSI_RESET);
    }

    public void showPublicChatMessage(String sender, String message){
        if(modelView.getLocalName().equals(sender))
            System.out.println(ANSI_YELLOW + "<TO ALL> " + ANSI_CYAN + "you" + ANSI_RESET + ": " + ANSI_GREEN + message + ANSI_RESET);
        else
            System.out.println(ANSI_YELLOW + "<TO ALL> " + ANSI_CYAN + sender + ANSI_RESET + ": " + ANSI_GREEN + message + ANSI_RESET);
    }

    public void showSetupInstruction(){
        System.out.println(ANSI_YELLOW + "<INSTRUCTION> " + ANSI_RESET + "Select the communication protocol you are going to use:");
        System.out.println("               0 - RMI (Remote Method Invocation)");
        System.out.println("               1 - TCP (Transmission Control Protocol)");
        System.out.println("              Type the number of the desired option");
    }
    private String putSpace(int len){
        return " ".repeat(len);
    }

}
