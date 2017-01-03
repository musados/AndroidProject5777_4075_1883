package com.siduron.java.androidproject5777_4075_4075;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context contex=this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login=(Button) findViewById(R.id.LoginButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent l = new Intent(contex,LoginActivity.class);
                //startActivity(l);
                Dialog d = new Dialog(contex);
                d.setContentView(R.layout.activity_user_login);
                d.setCancelable(true);
                Button login = (Button) d.findViewById(R.id.login);

                AutoCompleteTextView emailaddr = (AutoCompleteTextView) d.findViewById(R.id.email);
                EditText password = (EditText) d.findViewById(R.id.password);
                d.show();
            }
        });
    }
}
