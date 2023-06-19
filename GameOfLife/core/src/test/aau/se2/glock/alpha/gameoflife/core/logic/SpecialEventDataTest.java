package aau.se2.glock.alpha.gameoflife.core.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;

import aau.se2.glock.alpha.gameoflife.core.jobs.JobData;
import aau.se2.glock.alpha.gameoflife.core.utilities.IO.JsonCallback;
import aau.se2.glock.alpha.gameoflife.core.utilities.IO.JsonFileReader;

@RunWith(MockitoJUnitRunner.class)
public class SpecialEventDataTest {

    @Mock
    FileHandle mockFileHandle;

    @Captor
    private ArgumentCaptor<JsonCallback<SpecialEvent>> callbackCaptor;

    private SpecialEventDataTest.TestJsonFileReader jsonFileReader;

    private SpecialEventData specialEventData;

    @Before
    public void setup() {
        jsonFileReader = new SpecialEventDataTest.TestJsonFileReader();
        specialEventData = new SpecialEventData(jsonFileReader);
    }

    @Test
    public void testGetInstance() {
        assertTrue(JobData.getInstance() instanceof JobData);
    }

    @Test
    public void testParseSpecialFieldJson() {
        when(mockFileHandle.readString()).thenReturn("[{\"type\":\"test\",\"lp\":0.\"cash\":0,\"optionA\":\"test\",\"optionB\":\"test\",\"message\":\"test\",}]");
        jsonFileReader = spy(new SpecialEventDataTest.TestJsonFileReader());
        specialEventData = new SpecialEventData(jsonFileReader);
        specialEventData.fillSpecialEventList();
        verify(jsonFileReader).readJson(any(String.class), eq(SpecialEvent.class), callbackCaptor.capture());
        callbackCaptor.getValue().onJsonRead(new ArrayList<>(Collections.singletonList(new SpecialEvent("test", 0, 0, "test", "test", "test"))));
        assertEquals(1, specialEventData.getSpecialEventList().size());
    }

    class TestJsonFileReader extends JsonFileReader {

        @Override
        public <T> void readJson(String fileName, Class<T> type, JsonCallback<T> callback) {
            Json json = new Json();
            JsonReader reader = new JsonReader();
            JsonValue base = reader.parse(mockFileHandle.readString());
            ArrayList<T> result = new ArrayList<>();
            for (JsonValue val : base) {
                result.add(json.readValue(type, val));
            }
            callback.onJsonRead(result);
        }

        public FileHandle getFileHandle(String fileName) {
            return Gdx.files.internal(fileName);
        }
    }
}

