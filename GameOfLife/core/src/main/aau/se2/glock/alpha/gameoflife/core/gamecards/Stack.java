package aau.se2.glock.alpha.gameoflife.core.gamecards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stack {

    private List<Card> cardList = new ArrayList<>();

    private int countCard = 0;

    public Card getTopCard(){
        if(countCard>=cardList.size()){
            countCard = 0;
            mixCards();
        }
        return cardList.get(countCard++);
    }

    public void mixCards(){
        Collections.shuffle(cardList);
    }

    public void addCards(List<Card> newCards){
        //TODO KARTE AUF STACK LEGEN
        for (int i = 0; i < newCards.size(); i++) {
            cardList.add(newCards.get(i));
        }
    }
}
