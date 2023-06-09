package aau.se2.glock.alpha.gameoflife.core.gamecards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class Stack {

    /**
     *
     */
    public static Stack INSTANCE;

    /**
     *
     */
    private final List<Card> cardList = new ArrayList<>();

    /**
     *
     */
    private int countCard = 0;

    /**
     *
     */
    private Stack() {
        buildStack();
        mixCards();
    }

    /**
     * @return
     */
    public static Stack getINSTANCE() {
        if (INSTANCE != null) {
            return INSTANCE;
        } else
            return INSTANCE = new Stack();
    }

    /**
     * Loops card list and returns top card.
     *
     * @return top card
     */
    public Card getTopCard() {
        if (countCard >= cardList.size()) {
            countCard = 0;
            mixCards();
        }
        return cardList.get(countCard++);
    }

    /**
     * Fills Stack with new Cards
     */
    public void buildStack() {
        EventData eventData = new EventData();
        eventData.fillEventList();
        eventData.fillCardList();
        addCards(eventData.getCardList());
    }


    /**
     * Mixes card stack.
     */
    public void mixCards() {
        Collections.shuffle(cardList);
    }

    /**
     * Add new cards to stack.
     *
     * @param newCards new cards which will be added
     */
    public void addCards(List<Card> newCards) {
        for (int i = 0; i < newCards.size(); i++) {
            cardList.add(newCards.get(i));
        }
    }
}
