package aau.se2.glock.alpha.gameoflife.core;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import aau.se2.glock.alpha.gameoflife.core.gamecards.NormalEvent;

public class NormalEventTest {

    private NormalEvent normalEvent;

    @Before
    public void setup() {
        normalEvent = new NormalEvent(5, 500, "Test Event");
    }

    @Test
    public void testConstructor() {
        assertEquals(5, normalEvent.getLp());
        assertEquals(500, normalEvent.getCash());
        assertEquals("Test Event", normalEvent.getText());
    }

    @Test
    public void testSetLp() {
        normalEvent.setLp(10);
        assertEquals(10, normalEvent.getLp());
    }

    @Test
    public void testSetCash() {
        normalEvent.setCash(1000);
        assertEquals(1000, normalEvent.getCash());
    }

    @Test
    public void testSetText() {
        normalEvent.setText("Updated Event");
        assertEquals("Updated Event", normalEvent.getText());
    }

    @Test
    public void testToString() {
        String expected = "Event{lp=5, cash=500, text='Test Event'}";
        assertEquals(expected, normalEvent.toString());
    }
}
