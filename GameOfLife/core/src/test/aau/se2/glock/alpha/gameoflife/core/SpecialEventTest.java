package aau.se2.glock.alpha.gameoflife.core;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import aau.se2.glock.alpha.gameoflife.core.logic.SpecialEvent;

public class SpecialEventTest {
    private SpecialEvent specialEvent;

    @Before
    public void setup() {
        specialEvent = new SpecialEvent();
    }

    @Test
    public void testConstructor() {
        assertTrue(specialEvent instanceof SpecialEvent);
    }
}
