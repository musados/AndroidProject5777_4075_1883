package com.siduron.java.iTravel.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.siduron.java.androidproject5777_4075_4075.R;
import com.siduron.java.iTravel.Model.DataSource.iContract;

import static com.siduron.java.iTravel.Model.DataSource.iContract.iSharedPreference.SHARED_NAME;

public class UserPanel extends AppCompatActivity {
    //Sahred reference variables
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);

        sharedPreferences = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
        sharedEditor = sharedPreferences.edit();

        //Clear Logout sharedReference value
        sharedEditor.putBoolean(iContract.LoginUserKeys.LOGOUT_STATUS_KEY,false);
    }

    @SuppressWarnings("deprecation")
    protected void logout(View view)
    {
        Intent loginIntent=new Intent(this,MainActivity.class);

        loginIntent.putExtra(iContract.LoginUserKeys.LOGOUT_STATUS_KEY,true);
        startActivity(loginIntent);
        finish();
    }

}
