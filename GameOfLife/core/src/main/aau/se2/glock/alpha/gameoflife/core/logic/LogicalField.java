package aau.se2.glock.alpha.gameoflife.core.logic;

import aau.se2.glock.alpha.gameoflife.core.GameField;
import aau.se2.glock.alpha.gameoflife.core.gamecards.Card;
import aau.se2.glock.alpha.gameoflife.core.gamecards.Event;
import aau.se2.glock.alpha.gameoflife.core.gamecards.Stack;

/**
 *
 */
public class LogicalField {

    /**
     *
     */
    private boolean isSpecial;

    /**
     *
     */
    private SpecialEvent specialEvent; //is null if field NOT special

    /**
     *
     */
    private boolean hasPlayer;

    /**
     *
     */
    private int position;

    /**
     *
     */
    private int type;

    /**
     *
     */
    private GameField field;

    /**
     * @param field
     */
    public LogicalField(GameField field) {
        this.field = field;
    }

    /**
     * @return
     */
    public Event getEvent() {
        if (this.isSpecial) {
            return null;
            //TODO Return Special event when they have been impemented
        } else {
            Card c = Stack.getINSTANCE().getTopCard();
            return c.getEvent(type);
        }
    }
}