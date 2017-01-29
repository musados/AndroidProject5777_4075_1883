package com.siduron.java.iTravel.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.siduron.java.androidproject5777_4075_4075.R;

public class SplashScreen extends AppCompatActivity {

    final Context contex = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ProgressBar prog = (ProgressBar) findViewById(R.id.progressBar);

        SetPoneStatusBar();

        prog.setActivated(true);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent t = new Intent(contex, MainActivity.class);
                startActivity(t);
                finish();
            }
        }, 1000);
    }

    public void SetPoneStatusBar() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            window.setStatusBarColor(this.getColor(R.color.colorPrimary));//.setStatusBarColor(ContextCompat.getColor(activity, R.color.example_color));
    }
}
