package aau.se2.glock.alpha.gameoflife.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import aau.se2.glock.alpha.gameoflife.core.jobs.Job;
import aau.se2.glock.alpha.gameoflife.core.jobs.JobData;

public class JobCardsTest {

    Job j1;
    JobData data1;

    @Before
    public void setup() {
        j1 = new Job("Schauspieler", new ArrayList<>(Arrays.asList(5000, 20000, 30000, 60000, 90000, 300000, 1500000)));
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
        //assertEquals(j1,data1.jobList.get(0));
        data1.mixCards();
        assertNotEquals(j1, data1.jobList.get(0));
    }

    @Test
    public void testGet2JobsToSelect() {


    }

}
