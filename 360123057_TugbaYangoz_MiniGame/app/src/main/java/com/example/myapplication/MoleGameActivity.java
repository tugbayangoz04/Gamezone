package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MoleGameActivity extends AppCompatActivity {

    Button btnStartGame, btnBackToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mole_game);

        String usermail = getIntent().getStringExtra("usermail");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Whack a Mole");
        }

        btnStartGame = findViewById(R.id.btnStartGame);
        btnBackToMenu = findViewById(R.id.btnBackToMenu);



        btnBackToMenu.setOnClickListener(v -> {
            Intent intent = new Intent(MoleGameActivity.this,MoleGamePlayActivity.class);
            intent.putExtra("usermail",usermail);
            startActivity(intent);
        });

        btnStartGame.setOnClickListener(v -> {
            Intent intent = new Intent(MoleGameActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}