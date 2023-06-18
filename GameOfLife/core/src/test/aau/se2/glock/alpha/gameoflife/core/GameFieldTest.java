package aau.se2.glock.alpha.gameoflife.core;

import com.badlogic.gdx.math.Vector2;

import org.junit.Before;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

  /*  @Test
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
        LogicalField f = new LogicalField(gameField,"empty");
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
        assertTrue(gameField.getPosition() == null);
        assertTrue(gameField.getIndexOfNextGameFields() instanceof ArrayList);
        assertTrue(gameField.getType() == null);
    }*/
}