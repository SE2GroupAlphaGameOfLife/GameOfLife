package aau.se2.glock.alpha.gameoflife.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.graphics.Color;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import aau.se2.glock.alpha.gameoflife.core.utilities.JsonLoader;


public class PlayerTest {
    private Player player;

    @Before
    public void setUp() throws IOException {
        String relativePath = "GameOfLife/assets/gameboard.json";
        String absolutePath = Paths.get("../../").toAbsolutePath().toString() + "/" + relativePath;
        byte[] bytes = Files.readAllBytes(Paths.get(absolutePath));
        String boardString = new String(bytes);

        JsonLoader jsonLoader = mock(JsonLoader.class);
        when(jsonLoader.loadJsonFile()).thenReturn(boardString);
        Board.getInstance(jsonLoader.loadJsonFile());
        player = new Player("testUser", true);
    }

    @Test
    public void testRollTheDice() {
        player = new Player("testUser", true);

        int roll = player.rollTheDice();
        assertTrue(roll >= 1 && roll <= 10);
    }

    @Test
    public void testEmptyConstructor() {
        assertNotNull(new Player());
    }

    @Test
    public void testChooseDirection() {
        player = new Player("testUser", true);

        player.setMoveCount(1);
        player.chooseDirection(0);
        assertEquals(1, player.getPosition());
    }

    @Test
    public void testMakeMove() {
        player = new Player("testUser", true);

        player.setMoveCount(1);

        player.makeMove();
        while (player.getMoveCount() > 0) {
            player.chooseDirection(0);
            player.makeMove();
        }

        assertEquals(1, player.getPosition());
        assertEquals(0, player.getMoveCount());

        player.rollTheDice();
        assertTrue(player.getMoveCount() > 0 && player.getMoveCount() < 10);

        player.makeMove();
        while (player.getMoveCount() > 0) {
            player.chooseDirection(0);
            player.makeMove();
        }

        assertEquals(0, player.getMoveCount());
    }

    @Test
    public void testGettersAndSetters() {
        Player testPlayer = new Player("testUser", true);
        assertEquals("testUser", testPlayer.getUsername());
        assertEquals(0, testPlayer.getPosition());
        assertEquals(10000, testPlayer.getMoney());
        assertEquals(0, testPlayer.getLifepoints());
        assertEquals(18, testPlayer.getAge());
        assertEquals(0, testPlayer.getMoveCount());
        assertEquals(0, testPlayer.getId());
        assertEquals(new Color(Color.rgb888(255, 0, 0)), testPlayer.getColor());
        assertTrue(testPlayer.isHost());
        assertTrue(testPlayer.isHasTurn());
        assertTrue(testPlayer.isOnline());
        assertTrue(testPlayer.isJoning());
        assertFalse(testPlayer.isHasCheated());
        assertEquals(0,testPlayer.getHasCheatedAtAge());

        testPlayer.setUsername("User");
        testPlayer.setPosition(99);
        testPlayer.setMoney(99);
        testPlayer.setLifepoints(99);
        testPlayer.setAge(99);
        testPlayer.setMoveCount(99);
        testPlayer.setHost(false);
        testPlayer.setHasTurn(false);
        testPlayer.setOnline(false);
        testPlayer.setJoning(false);
        testPlayer.setId(99);
        testPlayer.setColor(new Color(Color.rgb888(0, 255, 0)));
        testPlayer.setHasCheated(true);
        testPlayer.setHasCheatedAtAge(99);


        assertEquals("User", testPlayer.getUsername());
        assertEquals(99, testPlayer.getPosition());
        assertEquals(99, testPlayer.getMoney());
        assertEquals(99, testPlayer.getLifepoints());
        assertEquals(99, testPlayer.getAge());
        assertEquals(99, testPlayer.getMoveCount());
        assertFalse(testPlayer.isHost());
        assertFalse(testPlayer.isHasTurn());
        assertFalse(testPlayer.isOnline());
        assertFalse(testPlayer.isJoning());
        assertEquals(99, testPlayer.getId());
        assertEquals(new Color(Color.rgb888(0, 255, 0)), testPlayer.getColor());
        assertTrue(testPlayer.isHasCheated());
        assertEquals(99, testPlayer.getHasCheatedAtAge());
    }
}
