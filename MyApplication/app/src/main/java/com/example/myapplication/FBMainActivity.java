package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;



public class FBMainActivity extends AppCompatActivity {

    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flappy_bird_home_screen);
        FBAppHolder.assign(this.getApplicationContext());
        mButton = findViewById(R.id.exitBtn);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                finish();
                startActivity(intent);
                System.exit(0);
            }

        });
    }
    public void startGame(View view){
        Intent intent = new Intent(this,FBGameActivity.class);
        finish();
        startActivity(intent);
    }

}