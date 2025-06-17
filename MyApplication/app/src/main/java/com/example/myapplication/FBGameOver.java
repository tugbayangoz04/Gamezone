package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FBGameOver extends AppCompatActivity {

    Button mRestatrtButton;
    TextView tScore,tBest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbgame_over_screen);
        mRestatrtButton = findViewById(R.id.btnRestart);
        int scoreCount = getIntent().getExtras().getInt("score");
        SharedPreferences pref = getSharedPreferences("myStoragePreference",0);
        int scoreBest = pref.getInt("scoreBest",0);
        SharedPreferences.Editor edit = pref.edit();
        if (scoreCount > scoreBest){
            scoreBest = scoreCount;
            edit.putInt("scoreBest",scoreBest);
            edit.apply();
        }
        tScore = findViewById(R.id.scoreDisplay);
        tBest = findViewById(R.id.BestDisplay);
        tScore.setText(""+scoreCount);
        tBest.setText(""+scoreBest);


        mRestatrtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(FBGameOver.this,FBMainActivity.class);
                startActivity(myIntent);
            }
        });


    }
}