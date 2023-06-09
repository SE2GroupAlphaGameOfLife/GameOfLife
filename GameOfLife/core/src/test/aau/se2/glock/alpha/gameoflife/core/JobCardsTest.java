package aau.se2.glock.alpha.gameoflife.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import aau.se2.glock.alpha.gameoflife.core.jobs.Job;
import aau.se2.glock.alpha.gameoflife.core.jobs.JobData;
import aau.se2.glock.alpha.gameoflife.core.utilities.IO.JsonFileReader;

public class JobCardsTest {

    /*
    Job job1;
    JobData data1;

    @Before
    public void setup() throws IOException {
        String relativePath = "GameOfLife/assets/Jobs.json";
        String absolutePath = Paths.get("../../").toAbsolutePath().toString() + "/" + relativePath;
        byte[] bytes = Files.readAllBytes(Paths.get(absolutePath));
        String jobsString = new String(bytes);

        JsonFileReader jsonLoader = mock(JsonFileReader.class);
        when(jsonLoader.loadJsonFile("Jobs.json")).thenReturn(jobsString);
        JobData.getInstance(jsonLoader.loadJsonFile("Jobs.json"));
        data1 = JobData.getInstance();
    }

    @Test
    public void testFillJobList() {
        assertEquals(20, data1.jobList.size());
    }

    @Test
    public void testMixCards() {
        String compare = getBezeichnung(data1);
        data1.mixCards();
        System.out.println(getBezeichnung(data1));
        assertNotEquals(compare, getBezeichnung(data1));

    }

    @Test
    public void testGet2JobsToSelect() {
        assertEquals(2, data1.getJobsToSelect(2).length);
    }

    @Test
    public void testGetOneJob() {
        assertEquals(1, data1.getJobsToSelect(1).length);
    }

    @Test
    public void testRobustnessOfJobsToSelect() {
        assertEquals(21, data1.getJobsToSelect(21).length);
    }

    @Test
    public void testGetJobList() {
        assertEquals(20, data1.getJobList().size());
    }

    @Test
    public void testGetGehaltsstufeUndBefoerderung() throws Exception {
        job1 = data1.getJobList().get(0);
        assertEquals(0, job1.getGehaltsStufe());
        job1.befoerderung();
        assertEquals(1, job1.getGehaltsStufe());
    }

    /*
    TODO Klammern entfernen wenn language level höher als 7
    @Test
    public void testBefoerderungMaxStufe(){
        Assert.assertThrows(Exception.class, ()->j1.befoerderung());
    }
     */

/*
    @Test
    public void testGetGehaltsListe() {
        /*assertEquals(7, j1.getGehaltsListe().size());
        int gehalt = j1.getGehaltsListe().get(6);
        assertEquals(1500000,gehalt);*/
    //}

/*
    public String getBezeichnung(JobData data) {
        String result = "";
        for (int i = 0; i < data.jobList.size(); i++) {
            result += data.jobList.get(i).getBezeichnung();

        }
        return result;
    }
    */
}
