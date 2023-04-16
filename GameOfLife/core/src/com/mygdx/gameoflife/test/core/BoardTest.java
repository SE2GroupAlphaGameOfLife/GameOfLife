package com.mygdx.gameoflife.test.core;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.mygdx.gameoflife.src.core.Board;
import com.mygdx.gameoflife.src.core.GameField;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class BoardTest {
    private Board board;

    @Before
    public void setUp() {
        board = Board.getInstance();
    }

    @Test
    public void testGetInstance() {
        assertNotNull(board);
    }

    @Test
    public void testGetGameFields() {
        List<GameField> gameFields = board.getGameFields();
        assertNotNull(gameFields);
        assertEquals(16, gameFields.size());
    }
}
