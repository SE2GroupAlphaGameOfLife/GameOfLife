package com.example.gameoflife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.gameoflife.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //binding.lblHelloWorld.setText("Hello Alphas");
    }
}