package aau.se2.glock.alpha.gameoflife.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.math.Vector2;

import org.junit.Before;
import org.junit.Test;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.core.logic.LogicalField;
import jdk.internal.net.http.common.Log;

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

    @Test
    public void testConstructorLogicalField(){
        LogicalField f = new LogicalField(gameField);
        gameField = new GameField(f);
        assertEquals(gameField.getLogicalField(), f);
    }

    @Test
    public void testAddIndexOfNextGameField(){
        assertEquals(gameField.getIndexOfNextGameFields().size(), 3);
        gameField.addIndexOfNextGameField(9);
        nextIndices.add(9);
        assertEquals(gameField.getIndexOfNextGameFields(), nextIndices);
    }

    @Test
    public void testDefaultConstructor(){
        gameField = new GameField();
        assertTrue(gameField.getLogicalField() instanceof LogicalField);
        assertTrue(gameField.getPosition() == null);
        assertTrue(gameField.getIndexOfNextGameFields() instanceof ArrayList);
        assertTrue(gameField.getType() == null);
    }
}