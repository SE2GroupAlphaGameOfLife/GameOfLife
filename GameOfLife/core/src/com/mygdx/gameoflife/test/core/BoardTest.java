package com.mygdx.gameoflife.test.core;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.mygdx.gameoflife.src.core.GameField;
import com.mygdx.gameoflife.test.mock.TestBoard;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class BoardTest{
    private TestBoard testBoard;

    @Before
    public void setUp() {
        testBoard = new TestBoard();
    }

    @Test
    public void testGetInstance() {
        assertNotNull(testBoard);
    }

    @Test
    public void testGetGameFields() {
        List<GameField> gameFields = testBoard.getGameFields();
        assertNotNull(gameFields);
        assertEquals(34, gameFields.size());
    }
}
