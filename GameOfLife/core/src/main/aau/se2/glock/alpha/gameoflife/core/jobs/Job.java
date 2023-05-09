package aau.se2.glock.alpha.gameoflife.core.jobs;

import java.util.ArrayList;

public class Job {
    String Bezeichnung;
    ArrayList<Integer> gehaltsListe;
    int gehaltsStufe = 0;

    /**
     * Creates a new job object with job description and pay grade list.
     *
     * @param bezeichnung  description of the job
     * @param gehaltsListe pay grade list with six payment levels
     */
    public Job(String bezeichnung, ArrayList<Integer> gehaltsListe) {
        Bezeichnung = bezeichnung;
        this.gehaltsListe = gehaltsListe;
    }

    /**
     * Returns job description.
     *
     * @return job description.
     */
    public String getBezeichnung() {
        return Bezeichnung;
    }

    /**
     * Returns pay grade.
     *
     * @return pay grade.
     */
    public int getGehaltsStufe() {
        return gehaltsStufe;
    }

    /**
     * Increases pay grade until you reach the maximum grade.
     */
    public void befoerderung() {
        if (gehaltsStufe < gehaltsListe.size() - 1) {
            gehaltsStufe++;
        } else {
            System.out.println("Error: highest possible job level reached.");
        }
    }

}
