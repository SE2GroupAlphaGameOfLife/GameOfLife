package com.example.gameoflife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.gameoflife.gamecards.FillData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new FillData().fillEventList();
        new FillData().fillCardList();


    }
}