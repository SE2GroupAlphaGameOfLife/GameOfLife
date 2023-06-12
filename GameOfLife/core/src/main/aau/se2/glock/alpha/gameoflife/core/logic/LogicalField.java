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
    private SpecialEvent specialEvent;

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
    public LogicalField(GameField field,String type) {
        this.field = field;
        this.type = type;
        this.section = 0;
        if(type.equals("empty")||type.equals("intersection")){
            this.isSpecial = false;
        }else{
            this.isSpecial = true;
            retrieveSpecialEvent();
        }
    }

    /**
     * @return
     */
    public Event getEvent() {
        if (this.isSpecial) {
            System.out.println("EVENT: "+specialEvent.toString());

           return this.specialEvent;
        } else {
            Card c = Stack.getINSTANCE().getTopCard();
            return c.getEvent(section);
        }
    }
    private void retrieveSpecialEvent(){
        SpecialEventHandler specialEventHandler = new SpecialEventHandler(type);
        this.specialEvent = specialEventHandler.getCorrectSpecialEvent();

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