package com.siduron.java.iTravel.Controller;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.siduron.java.androidproject5777_4075_4075.R;
import com.siduron.java.iTravel.Model.Backend.iTravelContentProvider;
import com.siduron.java.iTravel.Model.DataSource.Tools;
import com.siduron.java.iTravel.Model.DataSource.iContract;
import com.siduron.java.iTravel.Model.Entities.Gender;
import com.siduron.java.iTravel.Model.Entities.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static com.siduron.java.iTravel.Model.DataSource.iContract.iSharedPreference.LAST_USER_NAME;
import static com.siduron.java.iTravel.Model.DataSource.iContract.iSharedPreference.LAST_USER_PASSWORD;
import static com.siduron.java.iTravel.Model.DataSource.iContract.iSharedPreference.SAVE_LAST_USER;
import static com.siduron.java.iTravel.Model.DataSource.iContract.iSharedPreference.SHARED_NAME;

public class RegisterActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    /**
    * Id to identity READ_CONTACTS permission request.
    */
    private static final int REQUEST_READ_CONTACTS = 0;

    private final Context context=this;
    private UserRegisteringTask registerTask=null;
    iTravelContentProvider provider=new iTravelContentProvider();

    private String TAG="Register activity";

    private RelativeLayout mainLayout;
    private Button Register, login;
    private AutoCompleteTextView username;
    private EditText password,
            rePassword,
            firstName,
            lastName,
            //birthday,
            phoneNumber,
            address;

    //BirthDay variiables
    private TextView birthday;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;

    RadioGroup radioGroup;
    Gender selectedGender=Gender.UNKNOWN;

    //Sahred reference variables
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setFieldsAsVariables();
        setFlowDirection();

        populateAutoComplete();

        //set birthday fiels
        calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        autoLoadLoginInfo();


        //Load the shared references from the contex
        sharedPreferences = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
        sharedEditor = sharedPreferences.edit();
    }

    private void setFlowDirection() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        mainLayout.setLayoutDirection(RelativeLayout.LAYOUT_DIRECTION_RTL);
    }


    /**
     * When the user is exist in the shared references-is correct
     * try to login
     */
    private void autoLoadLoginInfo() {
        Intent current=getIntent();
        String _user=current.getStringExtra(iContract.LoginUserKeys.LOGIN_NAME_KEY);
        String _password=current.getStringExtra(iContract.LoginUserKeys.LOGIN_PASSWORD_KEY);

        username.setText((_user!=null)?_user:"");
        password.setText((_password!=null)?_password:"");
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    //applying values to the variables
                    year =  arg1 ;
                    month = arg2 ;
                    day = arg3;
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        birthday.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    /**
     * Sets the buttons, text fields - all form elements variables
     */
    private void setFieldsAsVariables() {
        //Main layout
        mainLayout=(RelativeLayout)findViewById(R.id.activity_register);

        //Register and login buttons
        Register = (Button) findViewById(R.id.RegisterButton);
        login = (Button) findViewById(R.id.LoginButton);

        //User name field
        username = (AutoCompleteTextView) findViewById(R.id.userName);

        //Other EditTexts fields
        password = (EditText) findViewById(R.id.userPassword);
        rePassword = (EditText) findViewById(R.id.userRePassword);

        firstName = (EditText) findViewById(R.id.userFirstName);
        lastName = (EditText) findViewById(R.id.userLastName);
        birthday = (TextView) findViewById(R.id.BirthdayText);
        phoneNumber = (EditText) findViewById(R.id.userPhone);
        address = (EditText) findViewById(R.id.userAddress);
        address.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                tryToRegister();
               return true;
            }
        });

        //Sets gender radio group listener
        radioGroup=(RadioGroup)findViewById(R.id.UserGender);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(radioGroup.getCheckedRadioButtonId()==R.id.MaleRadioButton) {
                    selectedGender = Gender.MALE;
                    Log.i("Gender Changed","MALE");
                }
                else {
                    selectedGender = Gender.FEMALE;
                    Log.i("Gender Changed","FEMALE");
                }
            }
        });

        setButtons();
    }

    /**
     * Sets the buttons events
     */
    private void setButtons() {
        //Register event
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryToRegister();
            }
        });

        //Login button operation
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(context,MainActivity.class));
                //Closing the registering activity and destroy it
                // for moving back to the login activity
                finish();
            }
        });
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.minimum_age_toast_message),
                Toast.LENGTH_SHORT)
                .show();
    }

    /**
     * Try to register
     * If the fields are correct and validated - register.
     * else show errors
     */
    private void tryToRegister()
    {
        //if the registering task is in progress
        if(registerTask!=null)
            return;

        try
        {
            Boolean success = checkFields();
            if(success) {
                Log.i("Try register-check", "Success");
                User user=new User(0,username.getText().toString().trim(),password.getText().toString().trim());
                user.setFirstName(firstName.getText().toString().trim());
                user.setLastName(lastName.getText().toString().trim());

                DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
                Date temp = format.parse(birthday.getText().toString());
                user.setBirthDay(temp);
                user.setGender(selectedGender);
                user.setPhone(phoneNumber.getText().toString().trim());
                user.setAddress(address.getText().toString());
                registerTask=new UserRegisteringTask(user);
                registerTask.execute((Void)null);
            }
            else
                Log.i("Try register","Unsuccessful registering!");
        }
        catch (IllegalArgumentException ie)
        {
            Log.d("Try register",ie.getMessage()+"\n"+ie.getStackTrace());
        }
        catch (Exception e)
        {
            Log.d("Try register",e.getMessage()+"\n"+e.getStackTrace());
        }
    }

    /**
     * Check if the fields filled correctly
     * and set their error messages
     */
    private boolean checkFields()
    {
        //Check the login details
        String loginName=username.getText().toString();
        String loginPass=password.getText().toString();
        String loginRePass=rePassword.getText().toString();
        String first=firstName.getText().toString();
        String last=lastName.getText().toString();
        String phone = phoneNumber.getText().toString().trim();
        String addr=address.getText().toString();

        View toFocus = null;
        boolean cancel=false;

        if(!isValidPhone(phone)) {
            cancel = true;
            phoneNumber.setError(getResources().getString(R.string.error_phone_format));
            toFocus = phoneNumber;
        }

        if((calendar.get(Calendar.YEAR)-year)<=9) {
            cancel=true;
            birthday.setError(getResources().getString(R.string.error_age_must_more_9));
            toFocus=birthday;
        }

        if(last.equals(""))
        {
            cancel=true;
            lastName.setError(getResources().getString(R.string.error_field_required));
            toFocus=lastName;
        }

        if(first.equals("")) {
            cancel = true;
            firstName.setError(getResources().getString(R.string.error_field_required));
            toFocus = firstName;
        }

        if(loginPass.equals(""))
        {
            cancel=true;
            password.setError(getResources().getString(R.string.error_field_required));
            toFocus=password;
        }
        else if(!isValidPassword(loginPass))
        {
            cancel=true;
            password.setError(getResources().getString(R.string.error_invalid_password));
            toFocus=password;
        }
        else if(!loginRePass.equals(loginPass))
        {
            cancel=true;
            rePassword.setError(getResources().getString(R.string.error_invalid_confirm_password));
            toFocus=rePassword;
        }

        if(loginName.equals(""))
        {
            cancel=true;
            username.setError(getResources().getString(R.string.error_field_required));
            toFocus=username;
        }
        else if(!isValidUsername(loginName))
        {
            cancel=true;
            username.setError(getResources().getString(R.string.error_invalid_email));
            toFocus=username;
        }


        //if the one or more from the fields are invalid
        if(cancel)
        {
            toFocus.requestFocus();
        }


        return !cancel;
    }

    private boolean isValidPhone(String phone) {
        if (phone.length()>0&&phone.length() <= 12) {
            switch (phone.substring(0, 1)) {
                case "+":
                    return isValidGlobalPhoneNumber(phone.substring(1));
                case "0":
                case "1":
                case "2":
                case "3":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                    return isValidLocalPhoneNumber(phone);
            }
        }

        return true;
    }

    private boolean isValidLocalPhoneNumber(String phone) {
        String[] phoneParts = phone.split("-");
        String temp = "";

        if (phoneParts.length == 1)
            temp = phoneParts[0];
        else {
            for (String item : phoneParts)
                temp += item;
        }
        return temp.length() == 10;
    }

    private boolean isValidGlobalPhoneNumber(String phone) {
        String[] phoneParts = phone.split("-");
        String temp = "";

        if (phone.contains("-")) {
            if (phoneParts.length == 1)
                temp = phoneParts[0];
            else {
                for (String item : phoneParts)
                    temp += item;
            }
        } else
            temp = phone;
        return temp.length() == 10 && temp.length() <= 12;
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


    private boolean isValidPassword(String loginPassword)
    {
        return loginPassword.length()>=8;
    }


    /**
     * Registering task
     *
     * @author Moshe Nahari & Haim Milikovsli
     */
    private class UserRegisteringTask extends AsyncTask<Void,Void,Boolean>
    {
        ProgressDialog dialog ;
        User user=null;
        int result=-1;

        /**
         * Task CTOR
         * @param username
         * @param password
         * @param first
         * @param last
         * @param birth
         * @param gender
         * @param phone
         * @param address
         */
        public UserRegisteringTask(String username, String password,String first, String last,
                                   Date birth, Gender gender,String phone,String address)
        {
            user = new User(0, username, password, first, last, gender, birth, phone, address);
        }

        /**
         * CTOR 2
         * @param user0
         */
        public  UserRegisteringTask(User user0)
        {
            user=user0;
        }

        @Override
        protected void onPreExecute() {
            Log.i("Registering Pre Task", "Starting progress");
            Register.setEnabled(false);
            dialog=  ProgressDialog.show(context,
                    getResources().getString(R.string.registering_proccess_title),
                    getResources().getString(R.string.proccess_running_wait));
            Log.i("Registering Pre Task", "Progress started");
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try
            {
                Log.i("Registering InBackround","Sleeping");
                // Simulate network access.
                Thread.sleep(500);
                Log.i("Registering InBackround","Awaik");
            } catch (InterruptedException e)
            {
                Log.i("Registering InBackround","Finished in error"+e.getMessage());
                return false;
            }

            Log.i("Registering InBackround","Performing registration...");
            return operateRegistering(user);

        }


        private Boolean operateRegistering(User user) {
            ContentValues content = Tools.UserToContentValues(user);
            Uri uri = provider.insert(iContract.UserFields.USER_URI, content);
            String resultId = uri.getLastPathSegment();

            try {
                result=Integer.parseInt(resultId);
            }
            catch (Exception e) {
                Log.e(TAG, "The uri:" + uri + "\nresultID:" + resultId + "\n" + e.getMessage());
                result = -1;
            }

            if (result>=0) {
                Log.i(TAG, "User created!\nID: " + resultId);
                return true;
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            Log.i("Registering post","Started");
            //Hide the dialog
            dialog.dismiss();

            Log.i("Registering post","dialog hidded");
            Register.setEnabled(true);
            if (success)
            {
                //Save the new user's login info for auto login
                //at the next app lunching time
                sharedEditor.putString(LAST_USER_NAME,user.getUsername());
                sharedEditor.putString(LAST_USER_PASSWORD,user.getPassword());
                sharedEditor.putBoolean(SAVE_LAST_USER,true);
                sharedEditor.commit();

                //if the user created and details are correct
                //-the register proccess was successful login with the new userIntent
                Intent _userPanel = new Intent(context, UserPanel.class);

                _userPanel.putExtra(iContract.LoginUserKeys.LOGIN_NAME_KEY, username.getText().toString());
                _userPanel.putExtra(iContract.LoginUserKeys.LOGIN_PASSWORD_KEY, password.getText().toString());

                _userPanel.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                _userPanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(_userPanel);
                finish();
            }
            else
            {
                if(result==-2)
                {
                   username.setError(getResources().getString(R.string.error_username_exist));
                    username.requestFocus();
                }
                Toast.makeText(context,
                        "Registration error:\n" + getResources().getString(R.string.error_username_exist),
                        Toast.LENGTH_LONG).show();
            }
            registerTask=null;
            Log.i("Registering post","Finished");
        }

        @Override
        protected void onCancelled() {
            dialog.dismiss();
            registerTask=null;
            Register.setEnabled(true);
        }
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, RegisterActivity.this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(username, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), RegisterActivity.ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");

    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(RegisterActivity.ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        Log.i("Contacts Cursor size: ",""+cursor.getCount()+"\nemails count: "+emails.size());
        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(RegisterActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        Log.d("Sets the Auto complete","List:" + emailAddressCollection.size() + "\nAdapter:"+String.valueOf(adapter.getCount()));

        username.setAdapter(adapter);
    }





}
