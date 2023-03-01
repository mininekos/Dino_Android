package com.example.dino;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class juego extends AppCompatActivity {

    private GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        gameView = new GameView(this);
        setContentView(gameView);
    }

    @Override
    public void onBackPressed() {
        if(gameView.isGameOver()) {
            gameView.destroy();
            finish();
        }
    }
}