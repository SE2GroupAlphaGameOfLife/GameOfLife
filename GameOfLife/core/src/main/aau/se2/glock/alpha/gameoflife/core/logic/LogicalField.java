package aau.se2.glock.alpha.gameoflife.core.logic;

import aau.se2.glock.alpha.gameoflife.core.GameField;
import aau.se2.glock.alpha.gameoflife.core.gamecards.Card;
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
    private String type;

    private int section;

    /**
     *
     */
    private GameField field;

    /**
     * @param field
     */
    public LogicalField(GameField field) {
        this.field = field;
        this.type = "";
        this.section = 0;
        if(type.equals("empty")||type.equals("intersection")){
            this.isSpecial = false;
        }else{
            this.isSpecial = true;
            buildSpecialEvent();
        }
    }

    /**
     * @return
     */
    public Event getEvent() {
        System.out.println("EVENT:!!!!! ");
        if (this.isSpecial) {
           return this.specialEvent;
        } else {
            Card c = Stack.getINSTANCE().getTopCard();
            return c.getEvent(section);
        }
    }
    private void buildSpecialEvent(){
        this.specialEvent = new SpecialEvent(type);

    }

    public void setSpecial(boolean b) {
        this.isSpecial = b;
    }

    public int getSection() {
        return  this.section;
    }

    public void setSection(int i) {
        this.section = i;
    }
}