package com.example.cmpt276_a3;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.view.WindowManager;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Timer;
import java.util.TimerTask;
/*
The game's welcome interface, including welcome animations, as well as authors.
This interface can be skipped manually.
*/

//https://blog.csdn.net/qq_19681347/article/details/81738350
public class SplashActivity extends AppCompatActivity {

    private Button go;
    private int i;
    private Timer timer;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch(msg.what){
                case 200:
                    go.setText("SKIP : "+ i);
                    i--;
                    if (i<0){
                        //close timer
                        timer.cancel();
                        //jump to main page
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        //close splash page
                        finish();
                    }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//hide state bar
        getSupportActionBar().hide();//hide act bar
        setContentView(R.layout.activity_splash);
        initView();
        YoYo.with(Techniques.RubberBand)
                .duration(1400)
                .repeat(20)
                .playOn(findViewById(R.id.gold_miner));

        YoYo.with(Techniques.RubberBand)
                .duration(1400)
                .repeat(20)
                .playOn(findViewById(R.id.gold_miner));

        //timer
        Countdown();

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //close timer
                timer.cancel();
                //jump to main page
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                // close splash page
                finish();
            }
        });

    }

    private void Countdown() {

        //set the time
        i = 5;

        //set timer
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //Send status value to handler
                handler.sendEmptyMessage(200);
            }
        };

        //start timer，1000 ms time difference
        timer.schedule(task,1,1000);
    }

    private void initView() {
        go = findViewById(R.id.skip_button);
    }
}

