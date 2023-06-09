package aau.se2.glock.alpha.gameoflife.core;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import aau.se2.glock.alpha.gameoflife.core.gamecards.Event;
import aau.se2.glock.alpha.gameoflife.core.gamecards.Stack;
import aau.se2.glock.alpha.gameoflife.core.logic.LogicalField;

public class LogicalFieldTest {
    LogicalField logicalField;

    @Mock
    GameField mockField;


    @Before
    public void setup() throws IOException {
        String relativePath = "GameOfLife/assets/Events.json";
        String absolutePath = Paths.get("../../").toAbsolutePath() + "/" + relativePath;
        byte[] bytes = Files.readAllBytes(Paths.get(absolutePath));
        String eventsString = new String(bytes);

        Stack.getINSTANCE(eventsString);
        logicalField = new LogicalField(mockField);
    }

    @Test
    public void testGetEvent() {
        Event e = logicalField.getEvent();
        assertNotNull(e);

    }

}

