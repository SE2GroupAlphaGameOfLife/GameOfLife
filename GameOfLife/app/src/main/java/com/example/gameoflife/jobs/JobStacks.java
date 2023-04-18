package com.example.gameoflife.jobs;

import com.example.gameoflife.gamecards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JobStacks {
    private List<Job> jobList = new ArrayList<>();

    private int countCard = 0;

    public Job getOneJob(){
        if(countCard>=jobList.size()){
            countCard = 0;
            mixJobCards();
        }
        return jobList.get(countCard++);
    }

    public void mixJobCards(){
        Collections.shuffle(jobList);
    }

    public void addJobs(List<Job> newCards){
        for (int i = 0; i < newCards.size(); i++) {
            jobList.add(newCards.get(i));
        }
    }


}
