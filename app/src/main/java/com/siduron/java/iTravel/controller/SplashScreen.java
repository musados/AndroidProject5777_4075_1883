package com.siduron.java.iTravel.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.siduron.java.androidproject5777_4075_4075.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ProgressBar prog=(ProgressBar) findViewById(R.id.progressBar);


        prog.setActivated(true);


        final Context contex=this;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent t = new Intent(contex,MainActivity.class);
                startActivity(t);
                finish();
            }
        },3000);
    }
}
