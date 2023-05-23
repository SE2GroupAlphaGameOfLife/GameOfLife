package aau.se2.glock.alpha.gameoflife.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import aau.se2.glock.alpha.gameoflife.core.jobs.Job;
import aau.se2.glock.alpha.gameoflife.core.jobs.JobData;

public class JobCardsTest {

    Job j1, j2, j3, j4;
    JobData data1;

    @Before
    public void setup() {
        j1 = new Job("Schauspieler", new ArrayList<>(Arrays.asList(5000, 20000, 30000, 60000, 90000, 300000, 1500000)));
        j2 = new Job("Sportler", new ArrayList<>(Arrays.asList(5000, 20000, 35000, 65000, 90000, 300000, 1400000)));
        j3 = new Job("Landwirt", new ArrayList<>(Arrays.asList(5000, 30000, 50000, 100000, 150000, 300000, 1000000)));
        j4 = new Job("Schönheitsberater", new ArrayList<>(Arrays.asList(5000, 20000, 30000, 40000, 50000, 60000, 700000)));
        data1 = new JobData();
    }

    @Test
    public void testFillJobList() {
        assertEquals(0, data1.jobList.size());
        data1.fillJobList();
        assertEquals(20, data1.jobList.size());
    }

    @Test
    public void testMixCards() {
        data1.fillJobList();
        String compare = getBezeichnung(data1);
        data1.mixCards();
        System.out.println(getBezeichnung(data1));
        assertNotEquals(compare, getBezeichnung(data1));

    }

    @Test
    public void testGet2JobsToSelect(){
        data1.fillJobList();
        assertEquals(2,data1.getJobsToSelect(2).length);
    }

    @Test
    public void testGetOneJob(){
        data1.fillJobList();
        assertEquals(1,data1.getJobsToSelect(1).length);
    }

    @Test
    public void testRobustnessOfJobsToSelect(){
        data1.fillJobList();
        assertEquals(21,data1.getJobsToSelect(21).length);
    }

    @Test
    public void testGetJobList(){
        assertEquals(0, data1.getJobList().size());
        data1.fillJobList();
        assertEquals(20, data1.getJobList().size());
    }

    @Test
    public void testGetGehaltsstufeUndBefoerderung() throws Exception {
        assertEquals(0,j1.getGehaltsStufe());
        j1.befoerderung();
        assertEquals(1,j1.getGehaltsStufe());
    }

    /*
    TODO Klammern entfernen wenn language level höher als 7
    @Test
    public void testBefoerderungMaxStufe(){
        Assert.assertThrows(Exception.class, ()->j1.befoerderung());
    }
     */


    @Test

    public void testGetGehaltsListe() {
        assertEquals(7, j1.getGehaltsListe().size());
        int gehalt = j1.getGehaltsListe().get(6);
        assertEquals(1500000,gehalt);
    }

    public String getBezeichnung(JobData data) {
        String result = "";
        for (int i = 0; i < data.jobList.size(); i++) {
            result += data.jobList.get(i).getBezeichnung();

        }
        return result;
    }



}
