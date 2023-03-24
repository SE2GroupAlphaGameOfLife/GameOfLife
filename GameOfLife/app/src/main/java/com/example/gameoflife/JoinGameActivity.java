package com.example.gameoflife;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import com.example.gameoflife.databinding.ActivityJoinGameBinding;
import com.example.gameoflife.fragments.ServerListFragment;
import com.example.gameoflife.models.ServerListViewModel;

public class JoinGameActivity extends AppCompatActivity {

    ActivityJoinGameBinding activityJoinGameBinding;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityJoinGameBinding = DataBindingUtil.setContentView(this, R.layout.activity_join_game);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        boolean isHost = intent.getBooleanExtra("isHost", false);

        String ipAddress = ""; // server ip address

        // Log.d("JoinGameActivity", "IP Address: " + ipAddress);

        ServerListViewModel serverListViewModel = new ServerListViewModel();

        //String serverListViewItem = ipAddress + " " + username;

        //serverListViewModel.addServerListItem(serverListViewItem);

        ServerListFragment serverListFragment = new ServerListFragment();

        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putBoolean("isHost", isHost);
        //bundle.putString("ipAddress", ipAddress);

        serverListFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.fragmentContainerView, serverListFragment);
        fragmentTransaction.commit();

        activityJoinGameBinding.returnToMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinGameActivity.this, MainActivity.class);
                JoinGameActivity.this.startActivity(intent);
            }
        });

    }
}