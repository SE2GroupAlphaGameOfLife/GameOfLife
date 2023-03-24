package com.example.gameoflife;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;

import com.esotericsoftware.minlog.Log;
import com.example.gameoflife.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        activityMainBinding.joinGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateUserProfileActivity.class);
                intent.putExtra("isHost", false);

                android.util.Log.d("MainActivity", "Extras: " + intent.getBooleanExtra("isHost", false));

                MainActivity.this.startActivity(intent);
            }
        });

        activityMainBinding.hostGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateUserProfileActivity.class);
                intent.putExtra("isHost", true);

                android.util.Log.d("MainActivity", "Extras: " + intent.getBooleanExtra("isHost", true));

                MainActivity.this.startActivity(intent);
            }
        });

        Log.set(Log.LEVEL_DEBUG);
    }
}