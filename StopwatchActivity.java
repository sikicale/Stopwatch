package com.hrad.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopwatchActivity extends AppCompatActivity {

    private int seconds = 0;// beleži broj sekundi
    private boolean running; // da li štoperica radi
    private boolean wasRunning; // da li je radila

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null){        // uzimamo zapamćene promenljive pri rotiranju ekrana
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }
    @Override
    protected void onResume(){
        super.onResume();
        if (wasRunning)
            running =true;
    }
    @Override
    protected void onPause(){
        super.onPause();
        wasRunning = running;
        running = false;
    }
    public void onSaveInstanceState(Bundle savedInstanceState){ // pamtimo promenljive kad se okrene ekran
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds",seconds);
        savedInstanceState.putBoolean("running",running);
        savedInstanceState.putBoolean("wasRunning",wasRunning);

    }
    // pokretanje štoperice
    public void onClickStart(View view){
        running = true;
    }
    // zaustavljanje štoperice
    public void onClickStop(View view){
        running = false;
    }
    // resetovanje štoperice
    public void onClickReset(View view){
        running = false;
        seconds = 0;
    }
    private void runTimer(){
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {       // kod u metodu run() izvršava(neprestano se izvršava)
            @Override
            public void run(){  // neprestano se izvršava
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;

                String time = String.format(Locale.getDefault(),"%d:%02d:%02d",hours,minutes,secs);
                timeView.setText(time);
                if(running){
                    seconds++;
                }
                handler.postDelayed(this,1000);// da čeka 1s
            }
        });


    }
}