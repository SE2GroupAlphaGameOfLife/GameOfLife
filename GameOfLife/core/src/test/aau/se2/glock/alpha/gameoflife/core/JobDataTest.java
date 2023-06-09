package aau.se2.glock.alpha.gameoflife.core;

import aau.se2.glock.alpha.gameoflife.core.jobs.Job;
import aau.se2.glock.alpha.gameoflife.core.jobs.JobData;
import aau.se2.glock.alpha.gameoflife.core.utilities.IO.JsonCallback;
import aau.se2.glock.alpha.gameoflife.core.utilities.IO.JsonFileReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JobDataTest {

    @Mock
    FileHandle mockFileHandle;

    @Captor
    private ArgumentCaptor<JsonCallback<Job>> callbackCaptor;

    private TestJsonFileReader jsonFileReader;

    private JobData jobData;

    @Before
    public void setup() {
        jsonFileReader = new TestJsonFileReader();
        jobData = new JobData(jsonFileReader);
    }

    @Test
    public void testGetInstance(){
        assertTrue(JobData.getInstance() instanceof JobData);
    }

    @Test
    public void testParseJobsJson() {
        when(mockFileHandle.readString()).thenReturn("[{\"Bezeichnung\":\"Test\",\"gehaltsListe\":[100,200,300]}]");
        jsonFileReader = spy(new TestJsonFileReader());
        jobData = new JobData(jsonFileReader);
        jobData.fillJobList();
        verify(jsonFileReader).readJson(any(String.class), eq(Job.class), callbackCaptor.capture());
        callbackCaptor.getValue().onJsonRead(new ArrayList<>(Collections.singletonList(new Job("Test", new ArrayList<>(Arrays.asList(100,200,300))))));
        assertEquals(1, jobData.getJobList().size());
    }

    @Test
    public void testGetJobsToSelect() {
        ArrayList<Job> jobs = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            jobs.add(new Job("Test " + i, new ArrayList<>(Arrays.asList(i*100, i*200, i*300))));
        }
        jobData.getJobList().addAll(jobs);
        Job[] selectedJobs = jobData.getJobsToSelect(3);
        assertEquals(3, selectedJobs.length);
        assertEquals("Test 2", selectedJobs[2].getBezeichnung());
    }

    @Test
    public void testMixCards() {
        ArrayList<Job> jobs = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            ArrayList<Integer> salaryList = new ArrayList<>(Arrays.asList(i*100, i*200, i*300, i*400, i*500, i*600));
            jobs.add(new Job("TestJob " + i, salaryList));
        }
        jobData.getJobList().addAll(jobs);
        ArrayList<Job> copyOfJobs = new ArrayList<>(jobData.getJobList());

        // Keep shuffling until the order changes
        do {
            jobData.mixCards();
        } while (jobData.getJobList().equals(copyOfJobs));

        assertEquals(copyOfJobs.size(), jobData.getJobList().size());

        // Check that lists contain the same elements
        for(Job job: copyOfJobs){
            assertTrue(jobData.getJobList().contains(job));
        }
    }


    class TestJsonFileReader extends JsonFileReader {

        @Override
        public <T> void readJson(String fileName, Class<T> type, JsonCallback<T> callback) {
            Json json = new Json();
            JsonReader reader = new JsonReader();
            JsonValue base = reader.parse(mockFileHandle.readString());
            ArrayList<T> result = new ArrayList<>();
            for (JsonValue val : base) {
                result.add(json.readValue(type, val));
            }
            callback.onJsonRead(result);
        }

        public FileHandle getFileHandle(String fileName) {
            return Gdx.files.internal(fileName);
        }
    }
}
