package com.example.gameoflife.jobs;

import java.util.ArrayList;

public class Job {
    public Job(String bezeichnung, ArrayList<Integer> gehaltsListe) {
        Bezeichnung = bezeichnung;
        this.gehaltsListe = gehaltsListe;
    }

    String Bezeichnung;
    ArrayList<Integer> gehaltsListe = new ArrayList<Integer>();
    int gehaltsStufe = 0;

    public String getBezeichnung() {
        return Bezeichnung;
    }

    public int getGehaltsStufe() {
        return gehaltsStufe;
    }

    public void befoerderung(){
        if(gehaltsStufe < gehaltsListe.size()-1){
            gehaltsStufe++;
        }else{
            //TODO Exception werfen
        }
    }
}
