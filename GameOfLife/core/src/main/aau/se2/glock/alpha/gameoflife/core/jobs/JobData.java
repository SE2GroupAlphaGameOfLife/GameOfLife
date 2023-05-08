package aau.se2.glock.alpha.gameoflife.core.jobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class JobData {

    Job j1 = new Job("Schauspieler", new ArrayList<>(Arrays.asList(5000, 20000, 30000, 60000, 90000, 300000, 1500000)));
    Job j2 = new Job("Sportler", new ArrayList<>(Arrays.asList(5000, 20000, 35000, 65000, 90000, 300000, 1400000)));
    Job j3 = new Job("Landwirt", new ArrayList<>(Arrays.asList(5000, 30000, 50000, 100000, 150000, 300000, 1000000)));
    Job j4 = new Job("Schönheitsberater", new ArrayList<>(Arrays.asList(5000, 20000, 30000, 40000, 50000, 60000, 700000)));
    Job j5 = new Job("Zirkusartist", new ArrayList<>(Arrays.asList(5000, 70000, 80000, 90000, 100000, 110000, 130000)));
    Job j6 = new Job("Koch", new ArrayList<>(Arrays.asList(5000, 25000, 35000, 50000, 750000, 400000, 900000)));
    Job j7 = new Job("Musiker", new ArrayList<>(Arrays.asList(5000, 30000, 50000, 100000, 1050000, 300000, 1500000)));
    Job j8 = new Job("Künstler", new ArrayList<>(Arrays.asList(5000, 80000, 120000, 140000, 160000, 180000, 220000)));
    Job j9 = new Job("Politiker", new ArrayList<>(Arrays.asList(5000, 60000, 80000, 90000, 150000, 300000, 600000)));
    Job j10 = new Job("Tänzer", new ArrayList<>(Arrays.asList(5000, 110000, 120000, 140000, 160000, 180000, 190000)));
    Job j11 = new Job("Super-Nanny", new ArrayList<>(Arrays.asList(5000, 10000, 50000, 80000, 120000, 220000, 270000)));
    Job j12 = new Job("Fotomodell", new ArrayList<>(Arrays.asList(5000, 10000, 20000, 50000, 200000, 500000, 700000)));
    Job j13 = new Job("Computerspezialist", new ArrayList<>(Arrays.asList(5000, 10000, 50000, 100000, 250000, 500000, 1000000)));
    Job j14 = new Job("Spion", new ArrayList<>(Arrays.asList(5000, 10000, 100000, 400000, 950000, 1200000, 1500000)));
    Job j15 = new Job("Abenteurer", new ArrayList<>(Arrays.asList(5000, 30000, 80000, 140000, 200000, 300000, 400000)));
    Job j16 = new Job("Journalist", new ArrayList<>(Arrays.asList(5000, 10000, 30000, 50000, 100000, 200000, 600000)));
    Job j17 = new Job("Bauunternehmer", new ArrayList<>(Arrays.asList(5000, 70000, 80000, 90000, 100000, 110000, 300000)));
    Job j18 = new Job("Kostümbildner", new ArrayList<>(Arrays.asList(5000, 30000, 80000, 140000, 200000, 300000, 400000)));
    Job j19 = new Job("Kommissar", new ArrayList<>(Arrays.asList(5000, 20000, 60000, 400000, 800000, 1000000, 1600000)));
    Job j20 = new Job("Schriftsteller", new ArrayList<>(Arrays.asList(5000, 10000, 50000, 80000, 120000, 220000, 500000)));
    public ArrayList<Job> jobList = new ArrayList<>();
    int countCard = 0;

    /**
     * Fills jobList with 20 Jobs.
     */
    public void fillJobList() {
        jobList.add(j1);
        jobList.add(j2);
        jobList.add(j3);
        jobList.add(j4);
        jobList.add(j5);
        jobList.add(j6);
        jobList.add(j7);
        jobList.add(j8);
        jobList.add(j9);
        jobList.add(j10);
        jobList.add(j11);
        jobList.add(j12);
        jobList.add(j13);
        jobList.add(j14);
        jobList.add(j15);
        jobList.add(j16);
        jobList.add(j17);
        jobList.add(j18);
        jobList.add(j19);
        jobList.add(j20);
    }

    /**
     * Returns two different Jobs and increments the countCard.
     *
     * @return two different Jobs and increments tho countCard.
     */
    public Job get2JobsToSelect(){
        return jobList.get(countCard++ & countCard++);
    }

    /**
     * Mixes jobList.
     */
    public void mixCards() {
        Collections.shuffle(jobList);
    }


}
