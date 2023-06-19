package aau.se2.glock.alpha.gameoflife.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.core.jobs.Job;
import aau.se2.glock.alpha.gameoflife.core.utilities.PlayerColor;

public class PlayerTest {
    private Player player;

    @Mock
    private Board board;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Board.setInstance(board);

        Gdx.files = mock(Files.class);
        FileHandle mockFileHandle = mock(FileHandle.class);
        when(Gdx.files.internal(anyString())).thenReturn(mockFileHandle);
    }

    @Test
    public void testRollTheDice() {
        player = new Player("testUser", true);

        int roll = player.rollTheDice();
        assertTrue(roll >= 1 && roll <= 10);
    }

    @Test
    public void testEmptyConstructor() {
        assertTrue(new Player() instanceof Player);
    }

    @Test
    public void testChooseDirection() {
        Player player = new Player("TestPlayer", false);
        player.setPosition(0);
        player.setMoveCount(2);

        List<GameField> gameFields = new ArrayList<>();

        GameField currentField = new GameField();
        currentField.addIndexOfNextGameField(1);
        currentField.addIndexOfNextGameField(2);
        gameFields.add(currentField);

        GameField nextField1 = new GameField();
        nextField1.addIndexOfNextGameField(3);
        gameFields.add(nextField1);

        GameField nextField2 = new GameField();
        nextField2.addIndexOfNextGameField(4);
        gameFields.add(nextField2);

        when(board.getGameFields()).thenReturn(gameFields);

        player.chooseDirection(0);

        assertEquals(1, player.getPosition());
        assertEquals(1, player.getMoveCount());

        player.chooseDirection(0);

        assertEquals(3, player.getPosition());
        assertEquals(0, player.getMoveCount());
    }

    @Test
    public void testMakeMove() {
        // Create mock instances of the GameField class
        GameField currentFieldMock = mock(GameField.class);
        GameField nextFieldMock = mock(GameField.class);

        // Create a list to hold the next game field indices
        List<Integer> nextGameFields = new ArrayList<>();
        nextGameFields.add(1); // Add a next game field index to the list

        // Set up the behavior of the currentFieldMock
        when(currentFieldMock.getIndexOfNextGameFields()).thenReturn(nextGameFields);

        // Set up the behavior of the nextFieldMock
        List<Integer> nextGameFieldsForNextField = new ArrayList<>();
        nextGameFieldsForNextField.add(2); // Add a valid next game field index to the list
        when(nextFieldMock.getIndexOfNextGameFields()).thenReturn(nextGameFieldsForNextField);

        // Create an instance of the Player class
        Player player = new Player("TestPlayer", false);
        player.setPosition(0);
        player.setMoveCount(1); // Decrease the move count to 1

        // Set up the behavior of the Board class
        Board boardMock = mock(Board.class);
        List<GameField> gameFieldsMock = Arrays.asList(currentFieldMock, nextFieldMock);
        when(boardMock.getGameFields()).thenReturn(gameFieldsMock);

        // Replace the actual Board instance with the mock instance
        Board.setInstance(boardMock);

        // Call the makeMove() method
        boolean result = player.makeMove();

        // Verify that the player's position and moveCount are updated correctly
        assertEquals(1, player.getPosition());
        assertEquals(0, player.getMoveCount()); // Ensure the move count is decreased to 0

        // Verify that the makeMove() method returns true
        assertTrue(result);
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
        assertEquals(PlayerColor.BLUE, testPlayer.getColor());
        assertTrue(testPlayer.isHost());
        assertTrue(testPlayer.hasTurn());
        assertTrue(testPlayer.isOnline());
        assertTrue(testPlayer.isJoning());

        testPlayer.setUsername("User");
        testPlayer.setPosition(99);
        testPlayer.setMoney(99);
        testPlayer.setLifepoints(99);
        testPlayer.setAge(99);
        testPlayer.setMoveCount(99);
        testPlayer.setIsHost(false);
        testPlayer.setHasTurn(false);
        testPlayer.setOnline(false);
        testPlayer.setJoning(false);
        testPlayer.setId(99);
        testPlayer.setColor(PlayerColor.BLUE);

        assertEquals("User", testPlayer.getUsername());
        assertEquals(99, testPlayer.getPosition());
        assertEquals(99, testPlayer.getMoney());
        assertEquals(99, testPlayer.getLifepoints());
        assertEquals(99, testPlayer.getAge());
        assertEquals(99, testPlayer.getMoveCount());
        assertFalse(testPlayer.isHost());
        assertFalse(testPlayer.hasTurn());
        assertFalse(testPlayer.isOnline());
        assertFalse(testPlayer.isJoning());
        assertEquals(99, testPlayer.getId());
        assertEquals(PlayerColor.BLUE, testPlayer.getColor());
    }

    @Test
    public void testSetGender() {
        Player player = new Player();
        player.toString();

        // Test setting gender to Male
        player.setGender("Male");
        assertEquals("Male", player.getGender());

        // Test setting gender to Female
        player.setGender("Female");
        assertEquals("Female", player.getGender());

        // Test setting gender to Non-Binary
        player.setGender("Non-Binary");
        assertEquals("Non-Binary", player.getGender());
    }

    @Test
    public void testSetCurrentJob() {
        Player player = new Player();

        // Test setting current job to a valid job
        Job job = new Job();
        player.setCurrentJob(job);
        assertEquals(job, player.getCurrentJob());

        // Test setting current job to null (no job)
        player.setCurrentJob(null);
        assertNull(player.getCurrentJob());
    }

    /*@Test
    public void testGetEvent() {
        // Create a player instance
        Player player = new Player("John", false);

        // Create mock objects for Board and GameField
        Board board = mock(Board.class);
        GameField gameField = mock(GameField.class);

        // Set up the game field with a logical field containing an event
        LogicalField logicalField = new LogicalField(gameField);
        Event event = new Event(10, 100, "Test Event");
        logicalField.setSpecial(false); // Assuming the logical field is not special
        //gameField.setLogicalField(logicalField);

        // Configure the mock objects' behavior
        when(board.getGameFields()).thenReturn(Collections.singletonList(gameField));

        // Set the player's position to the game field
        player.setPosition(0);

        // Get the event for the player's current position
        Event resultEvent = player.getEvent();

        // Assert that the result event is not null
        assertNotNull(resultEvent);

        // Assert that the result event matches the expected event
        assertEquals(event, resultEvent);

        // Assert that the player's money and lifepoints have been updated correctly
        assertEquals(100, player.getMoney());
        assertEquals(10, player.getLifepoints());

        // Verify the interactions with the mock objects
        verify(board).getGameFields();
        verify(gameField).getLogicalField();
    }*/
}