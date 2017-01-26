package com.siduron.java.iTravel.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.siduron.java.androidproject5777_4075_4075.R;
import com.siduron.java.iTravel.Model.DataSource.iContract;

public class UserPanel extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);


    }

    @SuppressWarnings("deprecation")
    protected void logout(View view)
    {
        Intent loginIntent=new Intent(this,MainActivity.class);

        loginIntent.putExtra(iContract.LoginUserKeys.LOGOUT_STATUS_KEY,true);
        startActivity(loginIntent);
    }

}
