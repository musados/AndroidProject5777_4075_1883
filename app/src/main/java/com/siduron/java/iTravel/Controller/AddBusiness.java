package com.siduron.java.iTravel.Controller;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.siduron.java.androidproject5777_4075_4075.R;
import com.siduron.java.iTravel.Model.Backend.iTravelContentProvider;
import com.siduron.java.iTravel.Model.DataSource.Tools;
import com.siduron.java.iTravel.Model.DataSource.iContract;
import com.siduron.java.iTravel.Model.Entities.Business;
import com.siduron.java.iTravel.Model.Entities.BusinessAdapter;
import com.siduron.java.iTravel.Model.Entities.User;

import static com.siduron.java.iTravel.Model.DataSource.iContract.UserFields.USER_MAIN_KEY;

public class AddBusiness extends AppCompatActivity {


    //###############
    //fields

    TextView name;
    TextView description;
    TextView email;
    TextView phone;
    TextView address;
    TextView website;

    Button addButton;
    Button cancelButton;

    boolean somthingChanged=false;

    //Listener for any changes of on of the fields for safe back navigation
    TextWatcher listenForChanges = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            somthingChanged=true;
        }
    };


    private final String TAG = "Add activity";
    Context context=this;
    AddBusinessTask addTask = null;
    iTravelContentProvider provider = new iTravelContentProvider();
    User loggedUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business);

        loggedUser = (User)getIntent().getSerializableExtra(USER_MAIN_KEY);
        setControls();
    }


    /**
     * Sets the view elements - texts and buttons...
     */
    private void setControls()
    {
        Log.w(TAG,"Creating the view");
        name = (TextView) findViewById(R.id.AddBusinessName);
        description = (TextView) findViewById(R.id.AddBusinessDescription);
        phone = (TextView) findViewById(R.id.AddBusinessPhone);
        email = (TextView) findViewById(R.id.AddBusinessEmail);
        website = (TextView) findViewById(R.id.AddBusinessWebsite);
        address = (TextView) findViewById(R.id.AddBusinessAddress);

        //Set buttons
        addButton = (Button) findViewById(R.id.AddButton_AddBusiness);
        cancelButton = (Button) findViewById(R.id.CancelButton_AddBusiness);
        setButtonsClickListeners();

        RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainContainer);

        for(int i=0;i<mainLayout.getChildCount();i++)
        {
            View item = mainLayout.getChildAt(i);
            if(item instanceof EditText)
            {
                ((EditText) item).addTextChangedListener(listenForChanges);
            }
        }
    }

    /**
     * Sets the buttons clic listeners
     */
    private void setButtonsClickListeners() {
        if(addButton!=null)
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tryAdd();
                }
            });

        if(cancelButton!=null)
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
    }


    private void tryAdd() {
        if(checkFields()) {
            Business temp = new Business(
                    0,
                    loggedUser.getId(),
                    name.getText().toString(),
                    email.getText().toString(),
                    phone.getText().toString(),
                    address.getText().toString(),
                    website.getText().toString(),
                    description.getText().toString());

            addTask = new AddBusinessTask(temp);
            addTask.execute(true);

        }
    }

    /**
     * Check if all fields filled correctly
     * if true do nothing just return true
     * else mark all incorrect fields and focus on the first wrong field
     *
     * @return if the fields are correct
     */
    private boolean checkFields() {
        View toFocus = null;
        boolean cancel = false;


        if(!isValidWebsite(website.getText().toString()))
        {
            website.setError(getResources().getString(R.string.incorrect_website_error));
            toFocus=website;
            cancel=true;
        }
        if (address.getText().toString().equals("")||address.getText().toString().length()<8) {
            address.setError(getResources().getString(R.string.error_field_required));
            toFocus = address;
            cancel = true;
        }
        if (!isValidPhone(phone.getText().toString())) {
            phone.setError(getResources().getString(R.string.error_phone_format));
            toFocus = phone;
            cancel = true;

        }
        if (!isValidEmail(email.getText().toString())) {
            email.setError(getResources().getString(R.string.error_invalid_email));
            toFocus = email;
            cancel = true;
        }
        if (description.getText().toString().equals("")) {
            description.setError(getResources().getString(R.string.error_field_required));
            toFocus = description;
            cancel = true;
        }
        if (name.getText().toString().equals("")) {
            name.setError(getResources().getString(R.string.error_field_required));
            toFocus = name;
            cancel = true;
        }


        if (cancel) {
            if (toFocus != null)
                toFocus.requestFocus();
        }


        return !cancel;
    }

    /**
     * Validate website address
     * @param s  is the string of the website
     * @return  true if is valid
     */
    private boolean isValidWebsite(String s) {
        if(!s.isEmpty())
        {
            boolean len=s.length()>=8;
            boolean containsDomain=s.contains(".")&&s.lastIndexOf(".")<s.length()-2;
            boolean prefix=s.toLowerCase().startsWith("http://")||s.toLowerCase().startsWith("https://");
            if(!len||!containsDomain||!prefix)
                return false;
            return true;
        }

        //When website was not filled - isn't have website
        return true;
    }

    @Override
    public void onBackPressed() {
        if(somthingChanged) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.dialog_are_you_sure_cancel_title));
            builder.setMessage(getResources().getString(R.string.dialog_are_you_sure_cancel_message));
            builder.setPositiveButton(getResources().getString(R.string.dialog_are_you_sure_cancel_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });

            builder.setNegativeButton(getResources().getString(R.string.dialog_are_you_sure_cancel_stay), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else
            super.onBackPressed();
    }

    /**
     * Checks if the given user name is valid
     * as E-mail
     *
     * @param loginName the user name
     * @return if is valid e-mail returns true
     */
    private boolean isValidEmail(String loginName) {
        if (loginName.contains("@") && loginName.indexOf("@") != 0) {
            String domainPart = loginName.substring(loginName.indexOf("@"));

            if (domainPart.contains(".")&&domainPart.lastIndexOf(".")<domainPart.length()-1)
                return true;
        }

        return false;
    }


    private boolean isValidPhone(String phone) {
        if (phone.length() > 0 && phone.length() <= 12) {
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

        return false;
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
     * Add Business task
     *
     * @author Moshe Nahari & Haim Milikovsli
     */
    private class AddBusinessTask extends AsyncTask<Boolean, Void, Boolean> {
        ProgressDialog messageDialog;
        Business business;
        int result = -1;
        String resultString = "-1";

        public int getResult() {
            return result;
        }

        public String getResultString() {
            return resultString;
        }

        Cursor cursor;

        /**
         * Task CTOR
         */
        public AddBusinessTask(Business b) {
            business = b;
        }


        @Override
        protected void onPreExecute() {
            Log.i(TAG, "Starting pre progress");
            messageDialog = ProgressDialog.show(context,
                    getResources().getString(R.string.add_business_process_title),
                    getResources().getString(R.string.proccess_running_wait));
            Log.i(TAG, "Progress started");
        }


        @Override
        protected Boolean doInBackground(Boolean... booleen) {
            try {
                //Add the business and get cursor
                Thread.sleep(500);
                ContentValues businessAsContentValues = Tools.BussinessToContentValues(business);

                Uri uri = provider.insert(iContract.BussinessFields.BUSSINESS_URI, businessAsContentValues);
                Log.w(TAG,"Bus id:"+business.getId()+"\nbus manager ID: "+business.getManagerID()+"\nloggeduser ID: "+loggedUser.getId());
                resultString = uri.getLastPathSegment();

                return checkResult();

            } catch (Exception e) {
                return false;
            }
        }

        private Boolean checkResult() {


            try {
                result = Integer.parseInt(resultString);
            } catch (Exception e) {
                Log.e(TAG, "The result ID:" + result + "\n" + e.getMessage());
                result = -1;
            }

            if (result >= 0) {
                Log.i(TAG, "Business created!\nID: " + result+"\nCreating the adapter...");
                ContentValues adapter = Tools.BussinessAdapterToContentValues(new BusinessAdapter(0,loggedUser.getId(),business.getId()));
                Uri uri = provider.insert(iContract.BusinessAdapterFields.BUSSINESS_Adapter_URI,adapter);

                return true;
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            Log.i(TAG, "Started");
            //Hide the dialog
            messageDialog.dismiss();

            Log.i(TAG, "dialog hidded");
            if (success) {

                //Show the success dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(AddBusiness.this);
                builder.setTitle(getResources().getString(R.string.add_business_process_success));
                builder.setMessage(getResources().getString(R.string.id_label) + " " + resultString);
                builder.setPositiveButton(
                        getResources().getString(R.string.ok_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

            } else {
                if (result == -1) {
                    name.setError(getResources().getString(R.string.add_business_failed_error));
                } else if (result == -2) {
                    name.setError(getResources().getString(R.string.add_business_exist_error));
                } else
                    name.setError(getResources().getString(R.string.add_business_unknown_error));
                name.requestFocus();
            }
            addTask = null;
            messageDialog.dismiss();
            Log.i(TAG, "Finished");
        }

        @Override
        protected void onCancelled() {
            messageDialog.dismiss();
            addTask = null;

        }
    }

}
