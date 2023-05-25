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
     * Needed for Testing (mock)
     *
     * @param jsonFileReader
     * Mocked object of JsonFileReader class
     */
    public JobData(JsonFileReader jsonFileReader){
        this.jsonFileReader = jsonFileReader;
    }

    public JobData() {
        this.jsonFileReader = new JsonFileReader();
        this.countCard = 0;
        this.jobList = new ArrayList<Job>();
    }

    /**
     *
     */
    public void parseJobsJson() {
        try {
            this.jsonFileReader.readJson(GameOfLife.fileJobJson, Job.class, new JsonCallback<Job>() {
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
     * Fills jobList with 20 Jobs.
     */
    public void fillJobList() {
        this.parseJobsJson();
        Gdx.app.log("JobData", "Read from JSON: " + this.jobList);
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
