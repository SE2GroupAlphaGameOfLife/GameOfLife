package aau.se2.glock.alpha.gameoflife.core.gamecards;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import aau.se2.glock.alpha.gameoflife.core.gamecards.Card;
import aau.se2.glock.alpha.gameoflife.core.gamecards.Event;

public class CardTest {

    private Card card;

    @Before
    public void setup() {
        card = new Card();
    }

    @Test
    public void testFillEvents() {
        card.fillEvents();
        assertEquals(4, card.getEvents().size());
        for (int i = 0; i < 4; i++) {
            assertEquals("", card.getEvent(i).getText());
            assertEquals(0, card.getEvent(i).getLp());
            assertEquals(0, card.getEvent(i).getCash());
        }
    }

    @Test
    public void testSetEvent() {
        Event event = new Event(10, 200, "Test Event");
        card.fillEvents();
        card.setEvent(1, event);
        assertEquals("Test Event", card.getEvent(1).getText());
        assertEquals(10, card.getEvent(1).getLp());
        assertEquals(200, card.getEvent(1).getCash());
    }

    @Test
    public void testGetEvent() {
        card.fillEvents();
        Event event = new Event(10, 200, "Test Event");
        card.setEvent(2, event);
        Event retrievedEvent = card.getEvent(2);
        assertEquals(event, retrievedEvent);
    }

    @Test
    public void testGetEventOutOfBounds() {
        card.fillEvents();
        Event event = card.getEvent(5);
        assertNull(event);
    }
}
