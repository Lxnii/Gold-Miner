package com.example.cmpt276_a3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.cmpt276_a3.cmpt276_a3_model.Score_Watcher;

/*
The main navigation interface of the game,
through which you can enter the interface for finding gold,
and jump to the help interface and settings interface.
*/
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//hide state bar
        setContentView(R.layout.activity_main);

        Button helpButton = findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });

        Button settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        YoYo.with(Techniques.Pulse)
                .duration(1350)
                .repeat(Animation.INFINITE)
                .playOn(findViewById(R.id.startButton));

        YoYo.with(Techniques.Pulse)
                .duration(1350)
                .repeat(Animation.INFINITE)
                .playOn(findViewById(R.id.helpButton));

        YoYo.with(Techniques.Pulse)
                .duration(1350)
                .repeat(Animation.INFINITE)
                .playOn(findViewById(R.id.settingsButton));

        YoYo.with(Techniques.ZoomIn)
                .duration(1400)
                .repeat(0)
                .playOn(findViewById(R.id.gold_miner));

        setupStartGameActivityButton();


    }

    private void setupStartGameActivityButton() {
        Button button = findViewById(R.id.startButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainGamePlayActivity.makeIntentForMainGameActivity(MainActivity.this);
                startActivity(intent);
            }
        });
    }


}

