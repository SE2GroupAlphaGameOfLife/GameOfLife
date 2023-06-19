package aau.se2.glock.alpha.gameoflife.core.jobs;

import java.util.ArrayList;

/**
 *
 */
public class Job {

    /**
     *
     */
    private String Bezeichnung;
    /**
     *
     */
    private ArrayList<Integer> gehaltsListe;

    /**
     *
     */
    private int gehaltsStufe = 0;

    /**
     * Needed for JSON deserialization
     */
    public Job() {
    }

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
     * @return job
     * description.
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
     * @return
     */
    public ArrayList<Integer> getGehaltsListe() {
        return gehaltsListe;
    }

    /**
     * Increases pay grade until you reach the maximum grade.
     */
    public void befoerderung() throws Exception {
        if (gehaltsStufe < gehaltsListe.size() - 1) {
            gehaltsStufe++;
        } else {
            System.out.println("Error: highest possible job level reached.");
            throw new Exception("Highest possible job level reached");
        }
    }

    @Override
    public String toString() {
        return "Job{" +
                "Bezeichnung='" + Bezeichnung + '\'' +
                ", gehaltsListe=" + gehaltsListe +
                ", gehaltsStufe=" + gehaltsStufe +
                '}';
    }
}
