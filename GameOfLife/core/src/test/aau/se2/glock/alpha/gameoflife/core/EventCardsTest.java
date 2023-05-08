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

    Card c1;
    Event e1, e2, e3, e4;
    Stack s1;
    ArrayList <Card> cardList;
    ArrayList <Event> eventList1;
    EventData f1;


    @Before
    public void setup(){
    c1 = new Card();
    cardList = new ArrayList<>();
    eventList1 = new ArrayList<>();
    s1 = new Stack();
    e1 = new Event(100,0,"Erhalte 100 LP.");
    e2 = new Event(200,0,"Erhalte 200 LP.");
    e3 = new Event(0,1000,"Erhalte € 1.000.");
    e4 = new Event(0,2000,"Erhalte € 2.000.");
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
        f1.fillEventList();
        f1.fillCardList();
        c1.fillEvents();
        //assertEquals(e1,c1.getEvent(0));
    }

    @Test
    public void testFillEvents(){
        f1.fillEventList();
        f1.fillCardList();
        //assertEquals(4,c1.fillEvents.size());
    }

    @Test
    public void testGetTopCard(){

    }

    @Test
    public void testMixCards(){
    c1.addEvent(0,e1);
    c1.addEvent(1,e2);
    c1.addEvent(2,e3);
    c1.addEvent(3,e4);
    cardList.add(c1);
    s1.mixCards();
    //assertNotEquals(e1,c1.getEvent(0));

    }

    @Test
    public void testAddCards(){

    }
}
