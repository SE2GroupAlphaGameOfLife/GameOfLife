package aau.se2.glock.alpha.gameoflife.core;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import aau.se2.glock.alpha.gameoflife.core.gamecards.Event;

public class EventTest {

    private Event event;

    @Before
    public void setup() {
        event = new Event(5, 500, "Test Event");
    }

    @Test
    public void testConstructor() {
        assertEquals(5, event.getLp());
        assertEquals(500, event.getCash());
        assertEquals("Test Event", event.getText());
    }

    @Test
    public void testSetLp() {
        event.setLp(10);
        assertEquals(10, event.getLp());
    }

    @Test
    public void testSetCash() {
        event.setCash(1000);
        assertEquals(1000, event.getCash());
    }

    @Test
    public void testSetText() {
        event.setText("Updated Event");
        assertEquals("Updated Event", event.getText());
    }

    @Test
    public void testToString() {
        String expected = "Event{lp=5, cash=500, text='Test Event'}";
        assertEquals(expected, event.toString());
    }
}
