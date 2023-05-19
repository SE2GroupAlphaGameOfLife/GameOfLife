package aau.se2.glock.alpha.gameoflife.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.core.utilities.JsonLoader;

public class BoardTest {
    private Board board;

    @Before
    public void setUp() throws IOException {
        String relativePath = "GameOfLife/assets/gameboard.json";
        String absolutePath = Paths.get("../../").toAbsolutePath().toString() + "/" + relativePath;
        byte[] bytes = Files.readAllBytes(Paths.get(absolutePath));
        String boardString = new String(bytes);

        JsonLoader jsonLoader = mock(JsonLoader.class);
        when(jsonLoader.loadJsonFile()).thenReturn(boardString);
        Board.getInstance(jsonLoader.loadJsonFile());
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
        assertEquals(140, gameFields.size());
    }
}
