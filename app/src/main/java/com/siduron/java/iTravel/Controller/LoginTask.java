package com.siduron.java.iTravel.Controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.siduron.java.androidproject5777_4075_4075.R;
import com.siduron.java.iTravel.Model.Backend.iTravelContentProvider;
import com.siduron.java.iTravel.Model.DataSource.Tools;
import com.siduron.java.iTravel.Model.DataSource.iContract;
import com.siduron.java.iTravel.Model.Entities.Gender;
import com.siduron.java.iTravel.Model.Entities.User;

import static com.siduron.java.iTravel.Model.DataSource.iContract.UserFields.USER_MAIN_KEY;
import static java.lang.System.out;

/**
 * Created by musad on 01/02/2017.
 */

public class LoginTask extends AsyncTask<Void,Void,Boolean>
{
    private String TAG="User Login Task";
    String LoginError="";
    int LoginErrorCode=0;
    Context context;

    //The ContentProvider
    iTravelContentProvider provider=new iTravelContentProvider();

    ProgressDialog dialog ;

    public String getLoginError() {
        return LoginError;
    }

    public int getLoginErrorCode() {
        return LoginErrorCode;
    }

    User userAccount=null;
    String user="";
    String pass="";
    boolean check=false;

    /**
     * Task CTOR
     * @param username
     * @param password
     */
    public LoginTask(Context c, String username, String password)
    {
        user=username;
        pass=password;
        context=c;
    }


    @Override
    protected void onPreExecute() {
        Log.i(TAG, "Starting pre progress");
        dialog=  ProgressDialog.show(context,
        context.getResources().getString(R.string.title_activity_login),
        context.getResources().getString(R.string.proccess_running_wait));

        Log.i(TAG, "Pre progress started");
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try
        {
            //Login
            Log.i(TAG,"Do in background - Performing registration...");
            return operateLogin(user,pass);
        }
        catch (Exception ge)
        {
            Log.i(TAG,"Do in background - Finished in error"+ge.getMessage());
            return false;
        }
    }


    private Boolean operateLogin(String username,String password) {

        try {
            Cursor usersCursor = provider.query(iContract.UserFields.USER_URI,null,null,null,null);

            while (usersCursor.moveToNext()) {
                //If the user name is exist and the password is correct return true
                if (usersCursor.getString(1).equals(username) && usersCursor.getString(2).equals(password)) {
                    userAccount = new User(
                            Integer.parseInt(usersCursor.getString(0)),
                            usersCursor.getString(1),
                            usersCursor.getString(2),
                            usersCursor.getString(3),
                            usersCursor.getString(4),
                            Gender.fromString(usersCursor.getString(5)),
                            Tools.dateFromString(usersCursor.getString(6)),
                            usersCursor.getString(7),
                            usersCursor.getString(8));

                    Log.w(TAG,"User account ID: "+userAccount.getId());
                    return true;
                }
            }

            //else if the user name is not exist - set generic message about the username and the password
            LoginError=context.getResources().getString(R.string.error_incorrect_password);
            return false;
        }
        catch (Exception e) {
            Log.e("Saving last user","Unsuccessful"+e.getMessage());//else if the user name is not exist - set generic message about the username and the password
            LoginError=context.getResources().getString(R.string.login_proccess_error);
            return false;
        }

    }

    @Override
    protected void onPostExecute(final Boolean success) {
        Log.i(TAG,"Started");
        //Hide the dialog
        dialog.dismiss();

        Log.i(TAG,"dialog hidded");

        if (success)
        {
            //if the user created and details are correct
            //-the register proccess was successful login with the new user
            Intent _userPanel = new Intent(context, UserPanel.class);

            _userPanel.putExtra(USER_MAIN_KEY,userAccount);

            context.startActivity(_userPanel);
            LoginError="Login Success";
            LoginErrorCode=0;
        }
        else
        LoginErrorCode=1;

        Log.i(TAG,"Login Finished");
    }

    @Override
    protected void onCancelled() {
        dialog.dismiss();
    }
}
