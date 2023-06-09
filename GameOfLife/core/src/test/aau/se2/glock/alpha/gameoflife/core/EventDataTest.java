package aau.se2.glock.alpha.gameoflife.core;

import aau.se2.glock.alpha.gameoflife.core.gamecards.Event;
import aau.se2.glock.alpha.gameoflife.core.gamecards.EventData;
import aau.se2.glock.alpha.gameoflife.core.utilities.IO.JsonCallback;
import aau.se2.glock.alpha.gameoflife.core.utilities.IO.JsonFileReader;

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

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EventDataTest {

    @Mock
    FileHandle mockFileHandle;

    @Captor
    private ArgumentCaptor<JsonCallback<Event>> callbackCaptor;

    private TestJsonFileReader jsonFileReader;

    private EventData eventData;

    @Before
    public void setup() {
        jsonFileReader = new TestJsonFileReader();
        eventData = new EventData(jsonFileReader);
    }

    @Test
    public void testParseEventsJson() {
        when(mockFileHandle.readString()).thenReturn("[{\"lp\":1,\"cash\":100,\"text\":\"Test\"}]");
        jsonFileReader = spy(new TestJsonFileReader());
        //doReturn(mockFileHandle).when(jsonFileReader).getFileHandle(any(String.class));
        eventData = new EventData(jsonFileReader);
        eventData.parseEventsJson();
        verify(jsonFileReader).readJson(any(String.class), eq(Event.class), callbackCaptor.capture());
        callbackCaptor.getValue().onJsonRead(new ArrayList<>(Collections.singletonList(new Event(1, 100, "Test"))));
        assertEquals(1, eventData.getEventList().size());
    }

    @Test
    public void testFillEventList() {
        List<Event> mockEvents = new ArrayList<>();
        mockEvents.add(new Event(1, 100, "Test"));
        when(mockFileHandle.readString()).thenReturn("[{\"lp\":1,\"cash\":100,\"text\":\"Test\"}]");
        jsonFileReader = spy(new TestJsonFileReader());
        //doReturn(mockFileHandle).when(jsonFileReader).getFileHandle(any(String.class));
        eventData = new EventData(jsonFileReader);
        eventData.parseEventsJson();
        verify(jsonFileReader).readJson(any(String.class), eq(Event.class), callbackCaptor.capture());
        callbackCaptor.getValue().onJsonRead((ArrayList<Event>) mockEvents);
        eventData.fillEventList();
        assertEquals(1, eventData.getEventList().size());
    }


    @Test
    public void testFillCardList() {
        ArrayList<Event> events = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            events.add(new Event(i, i*100, "Test " + i));
        }
        eventData.getEventList().addAll(events);
        eventData.fillCardList();
        assertEquals(1, eventData.getCardList().size());
        assertEquals(300, eventData.getCardList().get(0).getEvent(3).getCash());
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