package aau.se2.glock.alpha.gameoflife.core.logic;

import java.util.ArrayList;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.core.Board;
import aau.se2.glock.alpha.gameoflife.core.GameField;

public class LogicalBoard {
    Board board;

    ArrayList<LogicalField> listOfLields = new ArrayList<LogicalField>();

    public LogicalBoard(Board board){
        this.board = board;
        fillFields();
    }

    private void fillFields(){
        List<GameField> fields = board.getGameFields();
        for (GameField field:fields) {
            //TODO add LogicalField in Gamefield
           // field.getLogicalField;
        }
    }

}
