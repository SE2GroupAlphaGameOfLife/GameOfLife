package aau.se2.glock.alpha.gameoflife.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import aau.se2.glock.alpha.gameoflife.core.gamecards.Card;
import aau.se2.glock.alpha.gameoflife.core.gamecards.Event;
import aau.se2.glock.alpha.gameoflife.core.gamecards.EventData;
import aau.se2.glock.alpha.gameoflife.core.gamecards.Stack;

public class EventCardsTest {

    Card c1,c2,c3,c4,c5;

    Event e1, e2, e3, e4, e5;
    Stack s1;
    ArrayList <Card> cardList;
    ArrayList <Event> eventList1;
    EventData f1;


    @Before
    public void setup(){
    c1 = new Card();
    c2 = new Card();
    c3 = new Card();
    c4 = new Card();
    c5 = new Card();

    cardList = new ArrayList<>();
    eventList1 = new ArrayList<>();

    s1 = new Stack();

    e1 = new Event(100,0,"Erhalte 100 LP.");
    e2 = new Event(200,0,"Erhalte 200 LP.");
    e3 = new Event(0,1000,"Erhalte € 1.000.");
    e4 = new Event(0,2000,"Erhalte € 2.000.");
    e5 = new Event(300,0,"Erhalte 300 LP.");

    f1 = new EventData();


    }

    /**
     * Test for EventData method FillData.
     */
    @Test
    public void testFillData(){
        assertEquals(0,f1.eventList.size());
        f1.fillEventList();
        assertEquals(100,f1.eventList.size());
    }

    /**
     * Test for EventData method FillCardList.
     */
    @Test
    public void testFillCardList(){
        f1.fillEventList();
        f1.fillCardList();
        assertEquals(f1.eventList.size()/4,f1.cardList.size());
    }

    @Test
    public void testGetEvent(){
        c1.fillEvents();
        c1.setEvent(0,e1);
        c1.setEvent(1,e2);
        c1.setEvent(2,e3);
        c1.setEvent(3,e4);

        assertEquals(e1,c1.getEvent(0));


    }

    @Test
    public void testFillEvents(){
        f1.fillEventList();
        f1.fillCardList();
        //assertEquals(4,c1.fillEvents.size());
    }

    @Test
    public void testGetTopCard(){
        c1.fillEvents();
        c1.setEvent(0,e1);
        cardList.add(c1);
        s1.addCards(cardList);

        s1.getTopCard();
        assertEquals(c1,s1.getTopCard());
    }

    @Test
    public void testMixCards(){
        c1.fillEvents();
        c2.fillEvents();
        c3.fillEvents();
        c4.fillEvents();
        c5.fillEvents();

        c1.setEvent(0,e1);
        c2.setEvent(0,e2);
        c3.setEvent(0,e3);
        c4.setEvent(0,e4);
        c5.setEvent(0,e5);

        cardList.add(c1);
        cardList.add(c2);
        cardList.add(c3);
        cardList.add(c4);
        cardList.add(c5);

        s1.addCards(cardList);
        String compare = testmethodeMix(s1);
        s1.mixCards();
        System.out.println(testmethodeMix(s1));
        assertNotEquals(compare, testmethodeMix(s1));
    }

    public String testmethodeMix(Stack stack){
        String result = "";
        for (int i = 0; i < 5; i++) {
            result += stack.getTopCard().getEvent(0).getText();
        }
        return result;
    }

    @Test
    public void testAddCards(){

    }
}
