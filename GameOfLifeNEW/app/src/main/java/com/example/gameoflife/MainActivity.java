package com.example.gameoflife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;

import com.example.gameoflife.databinding.ActivityMainBinding;
import com.example.gameoflife.networking.client.ClientClass;
import com.example.gameoflife.networking.server.ServerClass;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ClientClass client;
    private ServerClass server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        client = new ClientClass();
        server = new ServerClass();

        binding.btnNewGame.setOnClickListener(view -> {
            server.start();
            client.connect("localhost", server.getTCPport(), server.getUDPport());
        });

        server.start();

        binding.btnJoinGame.setOnClickListener(view -> {
            //this.server.close();
            List<InetAddress> ips = client.discoverServers(this.server.getUDPport());
            client.connect(ips.get(1), this.server.getTCPport(), this.server.getUDPport());
        });
    }
}