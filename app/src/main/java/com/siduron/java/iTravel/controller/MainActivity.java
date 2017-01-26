package com.siduron.java.iTravel.Controller;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.siduron.java.iTravel.Model.Backend.iTravelContentProvider;
import com.siduron.java.iTravel.Model.DataSource.iContract;
import com.siduron.java.iTravel.Model.DataSource.iContract.LoginUserKeys;

import com.siduron.java.androidproject5777_4075_4075.R;

import static com.siduron.java.iTravel.Model.DataSource.iContract.iSharedPreference.LAST_USER_NAME;
import static com.siduron.java.iTravel.Model.DataSource.iContract.iSharedPreference.LAST_USER_PASSWORD;
import static com.siduron.java.iTravel.Model.DataSource.iContract.iSharedPreference.SAVE_LAST_USER;
import static com.siduron.java.iTravel.Model.DataSource.iContract.iSharedPreference.SHARED_NAME;

public class MainActivity extends AppCompatActivity {
    final Context contex=this;

    //Show/hide login progress
    HideProgress hideLogin;

    //Login responsibles
    Button login,register;
    CheckBox checkBox;
    EditText username,password;

    //Login task as AsyncTask
    private UserLoginTask loginTask=null;

    //Sahred reference variables
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.LoginButton);
        register = (Button) findViewById(R.id.RegisterButton);
        username = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.userPassword);
        checkBox = (CheckBox) findViewById(R.id.SaveLastConnected);

        ProgressBar loginProgress = (ProgressBar)findViewById(R.id.login_progress);
        RelativeLayout loginForm=(RelativeLayout)findViewById(R.id.login_form);
        TextView loginProgressText=(TextView)findViewById(R.id.login_progrees_text);
        hideLogin=new HideProgress(loginForm,loginProgress,loginProgressText);

        sharedPreferences = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
        sharedEditor = sharedPreferences.edit();

        //Check if the user was made logout before he come to this activity
        boolean logout = checkLogout();
        if(logout) {
            Log.i("Logout value",logout+"");
            clearLastUser();
        }

        loginBySavedLastUser(!logout);

        setButtonsListeners();
    }

    /**
     * Login by saved last user name (that was logged in)
     *
     */
    private void loginBySavedLastUser(boolean login) {
        if(login) {
            String savedName = sharedPreferences.getString(LAST_USER_NAME, null);
            String savedPassword = sharedPreferences.getString(LAST_USER_PASSWORD, null);
            Log.i("Auto login info:",savedName+", "+savedPassword);

            //If the user and password correct saved
            if (savedName != null && savedPassword != null) {
                new UserLoginTask(savedName, savedPassword, true).execute((Void) null);
                Log.w("MainActivity Auto login", "Loged on!");
            }
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        boolean check=checkBox.isChecked();

        saveLastConnectedUser(username.getText().toString(),
                password.getText().toString(),
                check);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadLastConnectedUser();

        boolean logout = checkLogout();
        if(logout) {
            loadLastConnectedUser();
            clearLastUser();
        }
    }



    /**
     * Checks if the user made logut
     * @return the logout shared reference value as boolean
     */
    private boolean checkLogout() {
        return getIntent().getBooleanExtra(LoginUserKeys.LOGOUT_STATUS_KEY,false);
    }

    /**
     * Load saved login info from the shared preferences
     */
    private void loadLastConnectedUser() {
        String lastConnectedName="";
        String lastConnectedPassword="";
        Boolean checked=false;

        try {
            //Get the last connected user info (if exist)
            lastConnectedName = sharedPreferences.getString(LAST_USER_NAME, null);
            username.setText((lastConnectedName != null) ? lastConnectedName : "");
            Log.i("Password",sharedPreferences.getString(LAST_USER_PASSWORD, null));
            lastConnectedPassword = sharedPreferences.getString(LAST_USER_PASSWORD, null);

            password.setText((lastConnectedPassword != null) ? lastConnectedPassword : "");
            checked = sharedPreferences.getBoolean(SAVE_LAST_USER, false);
            checkBox.setChecked(checked);

        } catch (Exception e) {
            Log.e("Preferences Load", "Faild to load last user: "+
                    lastConnectedName+" " +
                    lastConnectedPassword+" " +
                    String.valueOf(checked) +
                    "\n"+ e.getMessage());
        }
    }

    /**
     * Save the last connected user to the shared preferences
     * @param name - user name
     * @param pass - user password
     * @param isCheck - checkbox status
     */
    private void saveLastConnectedUser(String name,String pass,boolean isCheck) {
        try
        {
            String mess = "Name: " + name + " \nPass: " + pass + "\nIs Checked: " + String.valueOf(isCheck);

            logStep("Save Last User", mess, 'i');

            if(isCheck)
            {
                sharedEditor.putString(LAST_USER_NAME, name);
                sharedEditor.putString(LAST_USER_PASSWORD, pass.toString());
            }
            else {
                sharedEditor.putString(LAST_USER_NAME, "");
                sharedEditor.putString(LAST_USER_PASSWORD, "");
            }

            sharedEditor.putBoolean(SAVE_LAST_USER, isCheck);
            sharedEditor.commit();
        }
        catch (Exception e) {
            logStep("Preferences save error", "Faild to save last user: " + e.getMessage(),'e');
        }
    }

    private void clearLastUser()
    {
        try {
            Log.i("Shared clearing","Cleared!");
            sharedEditor.clear();
            sharedEditor.commit();
        }
        catch (Exception e)
        {
            logStep("Shared clearing",e.getMessage(),'e');
        }
    }


    /**
     * Sets the buttons operations - sets their listeners
     */
    private void setButtonsListeners() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tryToLogin();

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(contex, RegisterActivity.class);

                password.setError(null);
                it.putExtra(LoginUserKeys.LOGIN_NAME_KEY,username.getText().toString());
                it.putExtra(LoginUserKeys.LOGIN_PASSWORD_KEY,password.getText().toString());

                startActivity(it);
            }
        });



        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b)
                {
                    clearLastUser();
                    Toast.makeText(contex,getResources().getString(R.string.saved_user_cleared),Toast.LENGTH_SHORT).show();
                }
            }
        });

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                tryToLogin();
                return true;
            }
        });
    }

    private void tryToLogin() {
        String loginName = username.getText().toString();
        String loginPassword = password.getText().toString();
        View toFocus = null;
        boolean cancel = false;

        if (loginPassword.equals("")) {
            cancel = true;
            password.setError(getResources().getString(R.string.error_field_required));
            toFocus=password;
        }
        else if (!isValidPassword(loginPassword)) {
            cancel = true;
            password.setError(getResources().getString(R.string.error_invalid_password));
            toFocus = password;
        }


        if (loginName.equals("")) {
            cancel = true;
            username.setError(getResources().getString(R.string.error_field_required));
            toFocus=username;
        }
        else if (!isValidUsername(loginName)) {
            cancel = true;
            username.setError(getResources().getString(R.string.error_invalid_email));
            toFocus=username;
        }

        if(cancel)
            toFocus.requestFocus();

        else
        {
            loginTask=new UserLoginTask(loginName,loginPassword,checkBox.isChecked());
            loginTask.execute((Void)null);
        }
    }

    /**
     * Checks if the given user name is valid
     * as E-mail
     *
     * @param loginName the user name
     * @return if is valid e-mail returns true
     */
    private boolean isValidUsername(String loginName)
    {
        if(loginName.contains("@")&&loginName.indexOf("@")!=0)
        {
            String domainPart=loginName.substring(loginName.indexOf("@"));

            if(domainPart.contains("."))
                return true;
        }

        return false;
    }


    /**
     * Check if user password is valid
     * @param loginPassword
     * @return valid or not valid
     */
    private boolean isValidPassword(String loginPassword)
    {
        return loginPassword.length()>=8;
    }

    /**
     * Login task
     *
     * @author Moshe Nahari & Haim Milikovsli
     */
    private class UserLoginTask extends AsyncTask<Void,Void,Boolean>
    {
        private String TAG="User Login Task";
        String LoginError="";

        //The ContentProvider
        iTravelContentProvider provider=new iTravelContentProvider();

        ProgressDialog dialog ;
        String user="";
        String pass="";
        boolean check=false;

        /**
         * Task CTOR
         * @param username
         * @param password
         */
        public UserLoginTask(String username, String password,boolean checked)
        {
            user=username;
            pass=password;
            check=checked;
        }


        @Override
        protected void onPreExecute() {
            Log.i(TAG, "Starting pre progress");
            login.setEnabled(false);
            //dialog=  ProgressDialog.show(contex,
                    //getResources().getString(R.string.title_activity_login),
                    //getResources().getString(R.string.proccess_running_wait));

            hideLogin.showProgress(true);
            Log.i(TAG, "Pre progress started");
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try
            {
                Log.i(TAG,"Do in background - Sleeping");
                // Simulate network access.
                Thread.sleep(500);
                Log.i(TAG,"Do in background - Awaik");

                //Login
                Log.i(TAG,"Do in background - Performing registration...");
                return operateLogin(user,pass);
            } catch (InterruptedException e)
            {
                Log.i(TAG,"Do in background - Finished in error"+e.getMessage());
            }
            catch (Exception ge)
            {
                Log.i(TAG,"Do in background - Finished in error"+ge.getMessage());
                return false;
            }
            return true;
        }


        private Boolean operateLogin(String username,String password) {

            try {
                Cursor usersCursor = provider.query(iContract.UserFields.USER_URI,null,null,null,null);

                while (usersCursor.moveToNext()) {
                    //If the user name is exist and the password is correct return true
                    if (usersCursor.getString(1).equals(username) && usersCursor.getString(2).equals(password))
                        return true;
                }

                //else if the user name is not exist - set generic message about the username and the password
                LoginError=getResources().getString(R.string.error_incorrect_password);
                return false;
            }
            catch (Exception e) {
                Log.e("Saving last user","Unsuccessful"+e.getMessage());//else if the user name is not exist - set generic message about the username and the password
                LoginError=getResources().getString(R.string.login_proccess_error);
                return false;
            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            logStep(TAG,"Started",'i');
            //Hide the dialog
            //dialog.dismiss();
            hideLogin.showProgress(false);
            Log.i(TAG,"dialog hidded");


            if (check) {
                saveLastConnectedUser(user, pass,true );
                logStep(TAG , "User saved",'i');
            }

            login.setEnabled(true);
            if (success)
            {
                password.setError(null);
                //if the user created and details are correct
                //-the register proccess was successful login with the new user
                Intent _userPanel = new Intent(contex, UserPanel.class);

                _userPanel.putExtra(iContract.LoginUserKeys.LOGIN_NAME_KEY, username.getText().toString());
                _userPanel.putExtra(iContract.LoginUserKeys.LOGIN_PASSWORD_KEY, password.getText().toString());

                startActivity(_userPanel);
                finish();
            }
            else
            {
                password.setError(LoginError);
                password.requestFocus();
            }
            loginTask=null;
            logStep(TAG,"Finished",'i');
        }

        @Override
        protected void onCancelled() {
            //dialog.dismiss();
            hideLogin.showProgress(false);
            loginTask=null;
            login.setEnabled(true);
        }
    }

    private void logStep(String stepTag,String message,char type)
    {
        try {
            switch (type) {
                case 'e':
                    Log.e(stepTag, message);
                    break;
                case 'd':
                    Log.d(stepTag, message);
                    break;
                case 'v':
                    Log.v(stepTag, message);
                    break;
                case 'w':
                    Log.w(stepTag, message);
                    break;
                default:
                    Log.i(stepTag, message);
                    break;
            }
        }
        catch (Exception e) {
            Log.e("Log step error:", e.getMessage());
            e.printStackTrace();
        }
    }


}
