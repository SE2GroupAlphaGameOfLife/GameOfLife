package aau.se2.glock.alpha.gameoflife.core.jobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;

import java.util.ArrayList;
import java.util.Collections;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.utilities.IO.JsonCallback;
import aau.se2.glock.alpha.gameoflife.core.utilities.IO.JsonFileReader;

public class JobData {

    /**
     *
     */
    private static JobData INSTANCE;

    /**
     *
     */
    public ArrayList<Job> jobList;

    /**
     *
     */
    int countCard;

    /**
     *
     */
    private JsonFileReader jsonFileReader;

    /**
     * Needed for testing, if changed, please make sure that tests are running!
     * @param jsonString
     */
    private JobData(String jsonString) {
        // Parsing the json so we can use it
        JsonReader jsonReader = new JsonReader();
        JsonValue jsonValue = jsonReader.parse(jsonString);

        ArrayList<Job> jobs = new ArrayList<>();

        // Read the values and create a list of gameFields
        for (JsonValue jsonField : jsonValue) {
            ArrayList<Integer> nextPositions = new ArrayList<>();
            for (int i : jsonField.get("gehaltsListe").asIntArray()) {
                nextPositions.add(i);
            }

            Job job = new Job(jsonField.getString("Bezeichnung"), nextPositions);
            jobs.add(job);
        }
        jobList = jobs;

        this.countCard = 0;
    }

    public static JobData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JobData(new JsonFileReader().loadJsonFile("Jobs.json"));
        }
        return INSTANCE;
    }

    public static JobData getInstance(String jsonString) {
        if (INSTANCE == null) {
            INSTANCE = new JobData(jsonString);
        }
        return INSTANCE;
    }

    /**
     *
     */
    public void parseJobsJson() {
        try {
            this.jsonFileReader.readJson(GameOfLife.FILE_JOB_JSON, Job.class, new JsonCallback<Job>() {
                @Override
                public void onJsonRead(ArrayList<Job> result) {
                    jobList = result;
                }
            });
        } catch (SerializationException e) {
            Gdx.app.log("JobData", e.getMessage());
        }
    }

    /**
     * Returns any amount of jobs and increment the countCard.
     *
     * @return two different Jobs and increments tho countCard.
     */
    public Job[] getJobsToSelect(int amountOfJobs) {
        //amountOfJobs = amountOfJobs%20;
        Job[] jobs = new Job[amountOfJobs];

        for (int i = 0; i < jobs.length; i++) {
            jobs[i] = jobList.get(countCard++ % 20);
        }

        return jobs;
    }

    public Job getOneJob() {
        mixCards();
        return jobList.get(countCard++);
    }

    /**
     * Mixes jobList.
     */
    public void mixCards() {
        Collections.shuffle(jobList);
    }

    /**
     * @return
     */
    public ArrayList<Job> getJobList() {
        return jobList;
    }
}
