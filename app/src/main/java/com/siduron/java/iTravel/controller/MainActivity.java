package com.siduron.java.iTravel.controller;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.siduron.java.androidproject5777_4075_4075.R;

public class MainActivity extends AppCompatActivity {
    final Context contex=this;
    Button login;
    EditText username,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.LoginButton);
        username=(EditText)findViewById(R.id.userName);
        password=(EditText)findViewById(R.id.userPassword);

        setButtonsListeners();
    }


    private void setButtonsListeners() {
         login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isUserNameCorrect = checkUserName(username.getText().toString());
                if(!isUserNameCorrect)
                {
                    Toast.makeText(contex, "User name invalid", Toast.LENGTH_SHORT).show();
                }
                //Intent l = new Intent(contex,LoginActivity.class);
                //startActivity(l);
                /*Dialog d = new Dialog(contex);
                d.setContentView(R.layout.activity_user_login);
                d.setCancelable(true);
                Button login = (Button) d.findViewById(R.id.login);

                AutoCompleteTextView emailaddr = (AutoCompleteTextView) d.findViewById(R.id.email);
                EditText password = (EditText) d.findViewById(R.id.password);
                d.show();*/
            }
        });
    }

    private boolean checkUserName(String name)
    {
        if(!name.contains("@"))
            return false;

        return true;
    }
}
