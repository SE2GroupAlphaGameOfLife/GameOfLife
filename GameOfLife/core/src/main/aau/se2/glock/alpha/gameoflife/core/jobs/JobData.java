package aau.se2.glock.alpha.gameoflife.core.jobs;

import com.badlogic.gdx.Gdx;
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

    private JobData(String jsonString) {
        this.jsonFileReader = new JsonFileReader();
        this.countCard = 0;
        this.jobList = new ArrayList<Job>();
        this.parseJobsJson();
        Gdx.app.log("JobData", "Read from JSON: " + this.jobList);
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
