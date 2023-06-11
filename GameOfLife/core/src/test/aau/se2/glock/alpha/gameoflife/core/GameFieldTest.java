package aau.se2.glock.alpha.gameoflife.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.math.Vector2;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.core.logic.LogicalField;

public class GameFieldTest {
    private GameField gameField;
    private Vector2 position;
    private List<Integer> nextIndices;

    @Before
    public void setUp() {
        position = new Vector2(1, 1);
        nextIndices = new ArrayList<>(Arrays.asList(2, 3, 4));
        gameField = new GameField(position, nextIndices, "");
    }

    @Test
    public void testGetPosition() {
        assertEquals(position, gameField.getPosition());
    }

    @Test
    public void testGetNextIndices() {
        assertEquals(nextIndices, gameField.getIndexOfNextGameFields());
    }

    @Test
    public void testGetGamefield(){
        assertNotNull(gameField.getLogicalField());
    }
}