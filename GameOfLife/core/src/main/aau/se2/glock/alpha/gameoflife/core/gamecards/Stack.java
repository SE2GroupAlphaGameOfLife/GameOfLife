package aau.se2.glock.alpha.gameoflife.core.gamecards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stack {

    private List<Card> cardList = new ArrayList<>();

    private int countCard = 0;

    public static Stack INSTANCE;

    public static Stack getINSTANCE() {
        if(INSTANCE!=null){
        return INSTANCE;
        }else
            return INSTANCE = new Stack();
    }

    private Stack(){
        EventData eventData = new EventData();
        eventData.fillCardList();
        addCards(eventData.getCardList());
    }


    /**
     * Loops card list and returns top card.
     *
     * @return top card
     */
    public Card getTopCard(){
        if(countCard>=cardList.size()){
            countCard = 0;
            mixCards();
        }
        return cardList.get(countCard++);
    }



    /**
     * Mixes card stack.
     */
    public void mixCards(){
        Collections.shuffle(cardList);
    }

    /**
     * Add new cards to stack.
     *
     * @param newCards  new cards which will be added
     */
    public void addCards(List<Card> newCards){
        for (int i = 0; i < newCards.size(); i++) {
            cardList.add(newCards.get(i));
        }
    }
}
