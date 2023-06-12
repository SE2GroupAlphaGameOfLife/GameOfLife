package aau.se2.glock.alpha.gameoflife.core.logic;

import com.badlogic.gdx.utils.SerializationException;

import java.util.ArrayList;

import aau.se2.glock.alpha.gameoflife.core.jobs.Job;
import aau.se2.glock.alpha.gameoflife.core.utilities.IO.JsonCallback;

public class SpecialEventData {
    public void parseSpecialEventsJson() {
        try {
            this.jsonFileReader.readJson(this.jobDataJson, Job.class, new JsonCallback<Job>() {
                @Override
                public void onJsonRead(ArrayList<Job> result) {
                    jobList = result;
                }
            });
        } catch (SerializationException e) {
            //Gdx.app.log("JobData", e.getMessage());
        }
    }
}
