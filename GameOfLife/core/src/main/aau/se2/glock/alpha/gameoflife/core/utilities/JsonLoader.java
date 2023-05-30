package aau.se2.glock.alpha.gameoflife.core.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class JsonLoader {

    public JsonLoader() {

    }

    /**
     * @return
     */
    public String loadJsonFile(String filename) {
        FileHandle fileHandle = Gdx.files.internal(filename);
        return fileHandle.readString();
    }
}
