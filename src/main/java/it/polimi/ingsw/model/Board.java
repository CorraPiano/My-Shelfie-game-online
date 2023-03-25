package it.polimi.ingsw.model;
import it.polimi.ingsw.model.commoncard.CommonGoalCard;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private Item [][] livingRoom; //board
    private BagItem bagItem;
    private BagCommon bagCommon;
    private int itemsIn;

    private int [][] mask = {{0,0,0,3,4,0,0,0,0},
                             {0,0,0,1,1,4,0,0,0},
                             {0,0,3,1,1,1,3,0,0},
                             {0,4,1,1,1,1,1,1,3},
                             {4,1,1,1,1,1,1,1,4},
                             {3,1,1,1,1,1,1,4,0},
                             {0,0,3,1,1,1,3,0,0},
                             {0,0,0,4,1,1,0,0,0},
                             {0,0,0,0,4,3,0,0,0}
    };
    private Token endGameToken;
    private List<Token> tokens;
    private CommonGoalCard commonCard1,commonCard2;
    private int numOfPlayers;
    public Board() {
        initializeBoard();
        tokenSetUp();
        drawBoard();
    }
    public void initializeBoard() {
        this.livingRoom = new Item[9][9];
        this.bagCommon = new BagCommon();
        this.bagItem = new BagItem();
        //this.commonCard1 = bagCommon.drawCommonGoalCard();
        //this.commonCard2 = bagCommon.drawCommonGoalCard();
        //this.numOfPlayers = Gameplay.getNumPlayers();
        this.tokens = new ArrayList<Token>();
        this.endGameToken = new Token(1);
    }
    public void tokenSetUp(){
        if ( numOfPlayers == 2) {
        tokens.add(new Token(8));
        tokens.add(new Token(4));
    } //
        else if ( numOfPlayers == 3 ){
        tokens.add(new Token(8));
        tokens.add(new Token(6));
        tokens.add(new Token(4));
    }
        else if ( numOfPlayers == 4 ) {
        tokens.add(new Token(8));
        tokens.add(new Token(6));
        tokens.add(new Token(4));
        tokens.add(new Token(2));// token 2-4-6-8, mask
    }
    }
    public void drawBoard(){
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                if (mask[i][j] == 1 || mask[i][j] == numOfPlayers){
                    livingRoom[i][j] = bagItem.drawItem();
                    itemsIn = itemsIn++;
                }
            }
        }
    }
    public boolean checkRefill() {
        if (numOfPlayers == 2 && itemsIn == 28) {return true;}

        else if (numOfPlayers == 3 && itemsIn == 36) {return true;}

        else if (numOfPlayers == 4 && itemsIn == 44) {return true;}

        return false;
    }
    public void refillBoard() {
        if (checkRefill()){
            drawBoard();
        }
    }
    public Item getBoardItem (Coordinates[] coordinates) { return null; };

}
