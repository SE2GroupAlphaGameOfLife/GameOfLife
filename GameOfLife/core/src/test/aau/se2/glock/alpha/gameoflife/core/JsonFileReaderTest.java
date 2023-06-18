package aau.se2.glock.alpha.gameoflife.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.files.FileHandle;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.core.utilities.IO.JsonCallback;
import aau.se2.glock.alpha.gameoflife.core.utilities.IO.JsonFileReader;

public class JsonFileReaderTest {

    @Mock
    private FileHandle mockFileHandle;

    private JsonFileReader jsonFileReader;

    @Before
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Mock the reader behavior
        String mockJson = "[{\"mock\": \"data\"}]";
        Reader stringReader = new StringReader(mockJson);

        when(mockFileHandle.reader("UTF-8")).thenReturn(stringReader);

        // Set up JsonFileReader with mock FileHandle
        jsonFileReader = new JsonFileReader() {
            @Override
            public FileHandle getFileHandle(String fileName) {
                return mockFileHandle;
            }
        };
    }

    @Test
    public void testReadJson() {
        List<MockClass> result = new ArrayList<>();
        JsonCallback<MockClass> callback = result::addAll;

        jsonFileReader.readJson("mockFile", MockClass.class, callback);

        assertEquals(1, result.size());
        assertEquals("data", result.get(0).getMock());
    }

    @Test
    public void testGetFileHandle() {
        FileHandle result = jsonFileReader.getFileHandle("mockFile");

        assertNotNull(result);
        assertSame(mockFileHandle, result);
    }

    public static class MockClass {
        private String mock;

        public MockClass() {
        }

        public String getMock() {
            return mock;
        }

        public void setMock(String mock) {
            this.mock = mock;
        }
    }

}
