package com.example.gameoflife.jobs;

import android.util.Log;

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

    public void befoerderung()throws Exception{
        if(gehaltsStufe < gehaltsListe.size()-1){
            gehaltsStufe++;
        }else{
            //TODO Exception werfen
            Exception e = new Exception();
                Log.w("DEBUG",e);
        }
    }

    public void fillGehaltsList() {

        gehaltsListe.add(5000);
        gehaltsListe.add(60000);
        gehaltsListe.add(80000);
        gehaltsListe.add(90000);
        gehaltsListe.add(150000);
        gehaltsListe.add(300000);
        gehaltsListe.add(600000);
        gehaltsListe.add(5000);
    }

    Job j1 = new Job("Politiker",gehaltsListe);
    Job j2 = new Job("Sportler",gehaltsListe);
    Job j3 = new Job("Schauspieler",gehaltsListe);


}
