package aau.se2.glock.alpha.gameoflife.core;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import aau.se2.glock.alpha.gameoflife.core.gamecards.Event;
import aau.se2.glock.alpha.gameoflife.core.logic.LogicalField;

public class LogicalFieldTest {
    LogicalField logicalField;

    @Mock
    GameField mockField;


    @Before
    public void setup() {
        logicalField = new LogicalField(mockField);
    }

    @Test
    public void testGetEvent() {
        Event e = logicalField.getEvent();
        assertNotNull(e);

    }

}

