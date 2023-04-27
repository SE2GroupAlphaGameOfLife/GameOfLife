package aau.se2.glock.alpha.gameoflife.core.jobs;

import java.util.ArrayList;
import java.util.Arrays;

public class Job {
    public Job(String bezeichnung, ArrayList<Integer> gehaltsListe) {
        Bezeichnung = bezeichnung;
        this.gehaltsListe = gehaltsListe;
    }

    String Bezeichnung;
    ArrayList<Integer> gehaltsListe;
    int gehaltsStufe = 0;

    public String getBezeichnung() {
        return Bezeichnung;
    }

    public int getGehaltsStufe() {
        return gehaltsStufe;
    }

    public void befoerderung(){
        if (gehaltsStufe < gehaltsListe.size() - 1) {
            gehaltsStufe++;
        } //else {
           // Exception e;
            //System.out.println(e);
       // }
    }

}
