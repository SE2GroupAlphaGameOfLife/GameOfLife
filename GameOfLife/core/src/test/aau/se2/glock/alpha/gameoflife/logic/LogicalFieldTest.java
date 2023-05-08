package aau.se2.glock.alpha.gameoflife.logic;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import aau.se2.glock.alpha.gameoflife.core.GameField;
import aau.se2.glock.alpha.gameoflife.core.gamecards.Event;
import aau.se2.glock.alpha.gameoflife.core.gamecards.Stack;
import aau.se2.glock.alpha.gameoflife.core.logic.LogicalField;

public class LogicalFieldTest {
    LogicalField logicalField;

    @Mock
    GameField mockField;

    @Mock
    static Stack stackMock;

    @Before
    public void setup(){
        logicalField = new LogicalField(mockField);
    }

    @Test
    public void testGetEvent(){
        Event e = logicalField.getEvent();
        assertEquals("Du liest ein Lexikon von A bis Z durch. Weil du danach ziemlich schlau bist, erhältst du 200 LP.",e.getText());//Test for first event
    }

}
