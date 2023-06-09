package aau.se2.glock.alpha.gameoflife.core;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import aau.se2.glock.alpha.gameoflife.core.jobs.Job;

public class JobTest {

    private Job job;
    private ArrayList<Integer> gehaltsListe;

    @Before
    public void setup() {
        gehaltsListe = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            gehaltsListe.add(i * 1000);
        }
        job = new Job("Engineer", gehaltsListe);
    }

    @Test
    public void testGetBezeichnung() {
        assertEquals("Engineer", job.getBezeichnung());
    }

    @Test
    public void testGetGehaltsStufe() {
        assertEquals(0, job.getGehaltsStufe());
    }

    @Test
    public void testGetGehaltsListe() {
        assertEquals(gehaltsListe, job.getGehaltsListe());
    }

    @Test
    public void testBefoerderung() {
        try {
            for (int i = 0; i < 5; i++) {
                job.befoerderung();
            }
        } catch (Exception e) {
            fail("Exception shouldn't be thrown here.");
        }
        assertEquals(5, job.getGehaltsStufe());
    }

    @Test
    public void testBefoerderungException() {
        try {
            for (int i = 0; i < 7; i++) {
                job.befoerderung();
            }
            fail("Exception should have been thrown.");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Highest possible job level reached"));
        }
    }

    @Test
    public void testToString() {
        String expected = "Job{Bezeichnung='Engineer', gehaltsListe=" + gehaltsListe.toString() + ", gehaltsStufe=0}";
        assertEquals(expected, job.toString());
    }
}
