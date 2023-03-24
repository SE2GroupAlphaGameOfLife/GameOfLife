package com.example.gameoflife;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.gameoflife.databinding.ActivityCreateUserProfileBinding;

public class CreateUserProfileActivity extends AppCompatActivity {

    ActivityCreateUserProfileBinding activityCreateUserProfileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent mainIntent = getIntent();
        boolean isHost = mainIntent.getBooleanExtra("isHost", false);

        Log.d("CreateUserProfile", "Extras: " + isHost);

        activityCreateUserProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_user_profile);

        String inputUsername = activityCreateUserProfileBinding.inputUsername.getText().toString();

        activityCreateUserProfileBinding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateUserProfileActivity.this, MainActivity.class);
                CreateUserProfileActivity.this.startActivity(intent);
            }
        });

        if (isHost) {
            activityCreateUserProfileBinding.continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CreateUserProfileActivity.this, WaitingForUsersActivity.class);
                    intent.putExtra("username", inputUsername);
                    intent.putExtra("isHost", isHost);

                    Log.d("CreateUserProfile", "Extras: " + intent.getStringExtra("username"));
                    Log.d("CreateUserProfile", "Extras: " + intent.getBooleanExtra("isHost", false));

                    CreateUserProfileActivity.this.startActivity(intent);
                }
            });
        } else {
            activityCreateUserProfileBinding.continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CreateUserProfileActivity.this, JoinGameActivity.class);
                    intent.putExtra("username", inputUsername);
                    intent.putExtra("isHost", isHost);

                    Log.d("CreateUserProfile", "Extras: " + intent.getStringExtra("username"));
                    Log.d("CreateUserProfile", "Extras: " + intent.getBooleanExtra("isHost", false));

                    CreateUserProfileActivity.this.startActivity(intent);
                }
            });
        }



    }
}


