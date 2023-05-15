package aau.se2.glock.alpha.gameoflife.core.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class JsonLoader {

    public JsonLoader(){

    }

    /**
     * @return
     */
    public String loadJsonFile() {
        FileHandle fileHandle = Gdx.files.internal("gameboard.json");
        return fileHandle.readString();
    }
}
