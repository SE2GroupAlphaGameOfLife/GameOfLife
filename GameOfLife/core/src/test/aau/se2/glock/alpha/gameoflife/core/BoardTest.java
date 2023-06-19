package aau.se2.glock.alpha.gameoflife.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.core.utilities.IO.JsonCallback;
import aau.se2.glock.alpha.gameoflife.core.utilities.IO.JsonFileReader;

public class BoardTest {
    @Mock
    private JsonFileReader jsonFileReader;

    private Board board;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Board.setInstance(null);
        this.board = Board.getInstance(jsonFileReader);
    }

    @Test
    public void testParseJobsJson() {
        GameField mockField = mock(GameField.class);
        when(mockField.getType()).thenReturn("intersection");
        List<GameField> mockFields = new ArrayList<>();
        mockFields.add(mockField);

        doAnswer(invocation -> {
            ((JsonCallback<GameField>) invocation.getArgument(2)).onJsonRead((ArrayList<GameField>) mockFields);
            return null;
        }).when(jsonFileReader).readJson(anyString(), eq(GameField.class), any(JsonCallback.class));

        board.parseJobsJson();
        List<GameField> gameFields = board.getGameFields();
        assertNotNull(gameFields);
        assertFalse(gameFields.isEmpty());
        assertSame(mockField, gameFields.get(0));
    }
}
