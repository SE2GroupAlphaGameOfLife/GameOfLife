package aau.se2.glock.alpha.gameoflife.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.gamecards.Card;
import aau.se2.glock.alpha.gameoflife.core.gamecards.Event;
import aau.se2.glock.alpha.gameoflife.core.gamecards.EventData;
import aau.se2.glock.alpha.gameoflife.core.gamecards.Stack;
import aau.se2.glock.alpha.gameoflife.core.jobs.JobData;
import aau.se2.glock.alpha.gameoflife.core.utilities.IO.JsonFileReader;

public class EventCardsTest {

    Card c1, c2, c3, c4, c5;

    Event e1, e2, e3, e4, e5;

    Stack s1;
    ArrayList<Card> cardList;
    ArrayList<Event> eventList1;
    EventData f1;


    @Before
    public void setup() throws IOException {
        c1 = new Card();
        c2 = new Card();
        c3 = new Card();
        c4 = new Card();
        c5 = new Card();

        cardList = new ArrayList<>();
        eventList1 = new ArrayList<>();

        String relativePath = "GameOfLife/assets/Events.json";
        String absolutePath = Paths.get("../../").toAbsolutePath() + "/" + relativePath;
        byte[] bytes = Files.readAllBytes(Paths.get(absolutePath));
        String jobsString = new String(bytes);

        f1 = new EventData(jobsString);
        s1 = new Stack(jobsString);
        e1 = f1.getEventList().get(0);
        e2 = f1.getEventList().get(1);
        e3 = f1.getEventList().get(2);
        e4 = f1.getEventList().get(3);
        e5 = f1.getEventList().get(4);
    }

    /**
     * Test for EventData method FillData.
     */
    @Test
    public void testFillData() {
        assertEquals(100, f1.getEventList().size());
    }

    /**
     * Test for EventData method FillCardList.
     */
    @Test
    public void testFillCardList() {
        f1.fillCardList();
        assertEquals(f1.getEventList().size() / 4, f1.getCardList().size());
    }

    @Test
    public void testGetEvent() {
        c1.fillEvents();
        c1.setEvent(0, e1);
        c1.setEvent(1, e2);
        c1.setEvent(2, e3);
        c1.setEvent(3, e4);

        assertEquals(e1, c1.getEvent(0));


    }

    @Test
    public void testFillEvents() {
        //f1.fillCardList();
        //assertEquals(4,c1.fillEvents.size());
    }

    @Test
    public void testGetTopCard() {
        c1.fillEvents();
        c1.setEvent(0, e1);
        cardList.add(c1);
        s1.wipeStack();
        s1.addCards(cardList);

        s1.getTopCard();
        assertEquals(c1, s1.getTopCard());
    }

    @Test
    public void testMixCards() {
        c1.fillEvents();
        c2.fillEvents();
        c3.fillEvents();
        c4.fillEvents();
        c5.fillEvents();

        c1.setEvent(0, e1);
        c2.setEvent(0, e2);
        c3.setEvent(0, e3);
        c4.setEvent(0, e4);
        c5.setEvent(0, e5);

        cardList.add(c1);
        cardList.add(c2);
        cardList.add(c3);
        cardList.add(c4);
        cardList.add(c5);

        s1.addCards(cardList);
        String compare = testMethodMixCards(s1);
        s1.mixCards();
        System.out.println(testMethodMixCards(s1));
        assertNotEquals(compare, testMethodMixCards(s1));
    }

    public String testMethodMixCards(Stack stack) {
        String result = "";
        for (int i = 0; i < 5; i++) {
            result += stack.getTopCard().getEvent(0).getText();
        }
        return result;
    }

    @Test
    public void testGetAndSetCash() {
        assertEquals(0, e1.getCash());
        e1.setCash(200);
        assertEquals(200, e1.getCash());
    }

    @Test
    public void testGetAndSetLp() {
        assertEquals(200, e1.getLp());
        e1.setLp(1000);
        assertEquals(1000, e1.getLp());
    }

    @Test
    public void testSetText() {
        assertEquals("Du liest ein Lexikon von A bis Z durch. Weil du danach ziemlich schlau bist, erhÃ¤ltst du 200 LP.", e1.getText());
        e1.setText("Erhalte 200 LP.");
        e1.setLp(200);
        assertEquals("Erhalte 200 LP.", e1.getText());
    }
}
