package aau.se2.glock.alpha.gameoflife.core.utilities.IO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

public class JsonFileReader {

    /**
     * For testing only!
     */
    public JsonFileReader(){

    }

    public <T> void readJson(String fileName, Class<T> type, JsonCallback<T> callback) {
        Json json = new Json();
        JsonReader reader = new JsonReader();
        JsonValue base = reader.parse(getFileHandle(fileName));

        ArrayList<T> result = new ArrayList<>();
        for (JsonValue val : base) {
            result.add(json.readValue(type, val));
        }

        callback.onJsonRead(result);
    }

    // New method that could be overridden
    public FileHandle getFileHandle(String fileName) {
        return Gdx.files.internal(fileName);
    }
}
