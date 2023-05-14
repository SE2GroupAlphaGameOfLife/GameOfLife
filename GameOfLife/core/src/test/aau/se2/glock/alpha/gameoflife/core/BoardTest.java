package aau.se2.glock.alpha.gameoflife.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class BoardTest {
    private Board testBoard;
    @Before
    public void setUp() {
        testBoard = new Board();
    }

    @Test
    public void testGetInstance() {
        assertNotNull(testBoard);
    }

    @Test
    public void testGetGameFields() {
        List<GameField> gameFields = testBoard.getGameFields();
        assertNotNull(gameFields);
        assertEquals(140, gameFields.size());
    }
}
