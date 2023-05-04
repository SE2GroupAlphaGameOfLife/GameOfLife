package aau.se2.glock.alpha.gameoflife.core.logic;

import aau.se2.glock.alpha.gameoflife.core.GameField;

public class LogicalField {

    boolean isSpecial;

    SpecialEvent specialEvent; //is null if field NOT special

    boolean hasPlayer;

    int position;

    GameField field;

    public LogicalField(GameField field){
        this.field = field;

    }







}
