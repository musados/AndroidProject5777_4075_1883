package com.siduron.java.iTravel.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.siduron.java.androidproject5777_4075_4075.R;

import static com.siduron.java.iTravel.Controller.MainActivity.*;
import static com.siduron.java.iTravel.Model.DataSource.iContract.iSharedPreference.LAST_USER_NAME;
import static com.siduron.java.iTravel.Model.DataSource.iContract.iSharedPreference.LAST_USER_PASSWORD;
import static com.siduron.java.iTravel.Model.DataSource.iContract.iSharedPreference.SHARED_NAME;

public class SplashScreen extends AppCompatActivity {

    final Context contex = this;
    //Login task as AsyncTask
    private LoginTask loginTask = null;

    //Sahred reference variables
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ProgressBar prog = (ProgressBar) findViewById(R.id.progressBar);

        SetPoneStatusBar();

        //Shared preferences
        sharedPreferences = getSharedPreferences(SHARED_NAME, MODE_PRIVATE);
        sharedEditor = sharedPreferences.edit();

        prog.setActivated(true);

        //Try login from saved user
        //loginBySavedLastUser();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent t = new Intent(contex, MainActivity.class);
                startActivity(t);
                finish();
            }
        }, 500);
    }

    public void SetPoneStatusBar() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            window.setStatusBarColor(this.getColor(R.color.colorPrimary));//.setStatusBarColor(ContextCompat.getColor(activity, R.color.example_color));
    }

    /**
     * Login by saved last user name (that was logged in)
     */
    private void loginBySavedLastUser() {

        String savedName = sharedPreferences.getString(LAST_USER_NAME, "");
        String savedPassword = sharedPreferences.getString(LAST_USER_PASSWORD, "");
        Log.w("Auto login info:", savedName + ", " + savedPassword);

        //If the user and password correct saved
        if (savedName != null && savedName != "" && savedPassword != null && savedPassword != "") {

            loginTask = new LoginTask(this, savedName, savedPassword);
            loginTask.execute((Void) null);

            if(loginTask.getLoginErrorCode()==0)
                finish();
            Log.w("SplashScreen Auto login", "Loged on!");

        }
    }
}
