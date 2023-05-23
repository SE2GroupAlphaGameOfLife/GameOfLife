package aau.se2.glock.alpha.gameoflife.core.utilities.IO;

import java.util.ArrayList;

public interface JsonCallback<T> {
    void onJsonRead(ArrayList<T> result);
}
