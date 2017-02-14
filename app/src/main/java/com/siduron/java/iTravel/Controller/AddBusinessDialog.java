package com.siduron.java.iTravel.Controller;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.siduron.java.androidproject5777_4075_4075.R;
import com.siduron.java.iTravel.Model.Backend.iTravelContentProvider;
import com.siduron.java.iTravel.Model.DataSource.Tools;
import com.siduron.java.iTravel.Model.DataSource.iContract;
import com.siduron.java.iTravel.Model.Entities.Activity;
import com.siduron.java.iTravel.Model.Entities.Business;
import com.siduron.java.iTravel.Model.Entities.User;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by musad on 31/01/2017.
 */

public class AddBusinessDialog extends DialogFragment implements View.OnClickListener {

    //###############
    //fields

    TextView name;
    TextView description;
    TextView email;
    TextView phone;
    TextView address;
    TextView website;

    boolean canClose = false;


    private final String TAG = "Add activity";
    Context context=getActivity();
    AddBusinessTask addTask = null;
    iTravelContentProvider provider = new iTravelContentProvider();
    User loggedUser;

    @Override
    public void onClick(View view) {

    }

    public interface AddBusinessDialogListener {
        void onBusinessAddClicked(String username, String password);
        void onBusinessCancelClicked(String username);
    }

    private AddBusinessDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getResources().getString(R.string.add_business_title))
                /*.setPositiveButton(getResources().getString(R.string.add_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (checkFields()) {
                            tryAdd();

                        }

                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })*/
                .setView(getActivity().getLayoutInflater().inflate(R.layout.add_business_layout,null))
        .setCancelable(false);



        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_business_layout, container);
        //this.getDialog().setTitle(R.string.add_business_title);

        Log.w(TAG,"Creating the view");
        name = (TextView) getDialog().findViewById(R.id.AddBusinessName);
        description = (TextView) getDialog().findViewById(R.id.AddBusinessDescription);
        phone = (TextView) getDialog().findViewById(R.id.AddBusinessPhone);
        email = (TextView) getDialog().findViewById(R.id.AddBusinessEmail);
        website = (TextView) getDialog().findViewById(R.id.AddBusinessWebsite);
        address = (TextView) getDialog().findViewById(R.id.AddBusinessAddress);

        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                String add= getResources().getString(R.string.add_button);

                Button addButton = ((AlertDialog)dialogInterface).getButton(AlertDialog.BUTTON_POSITIVE);
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tryAdd();
                        return;
                    }
                });
            }
        });
        /*mReset = (Button) view.findViewById(R.id.reset_button);
        mLogin = (Button) view.findViewById(R.id.login_button);

        mReset.setOnClickListener(this);
        mLogin.setOnClickListener(this);*/

        return view;
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


        //this.getDialog().findViewById()
        //getDialog()
        if (address.getText().equals("")) {
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
     * Checks if the given user name is valid
     * as E-mail
     *
     * @param loginName the user name
     * @return if is valid e-mail returns true
     */
    private boolean isValidEmail(String loginName) {
        if (loginName.contains("@") && loginName.indexOf("@") != 0) {
            String domainPart = loginName.substring(loginName.indexOf("@"));

            if (domainPart.contains("."))
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
            messageDialog = ProgressDialog.show(getDialog().getContext(),
                    getResources().getString(R.string.add_business_process_title),
                    getResources().getString(R.string.proccess_running_wait));
            Log.i(TAG, "Progress started");
        }


        @Override
        protected Boolean doInBackground(Boolean... booleen) {
            try {
                //Add the business and get cursor
                 Thread.sleep(5000);
                ContentValues businessAsContentValues = Tools.BussinessToContentValues(business);

                Uri uri = provider.insert(iContract.BussinessFields.BUSSINESS_URI, businessAsContentValues);

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
                Log.i(TAG, "Business created!\nID: " + result);
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
                String message = getResources().getString(R.string.add_business_process_success) +
                        "\n" + getResources().getString(R.string.id_label) + ": " + resultString;

                Toast.makeText(getDialog().getContext(), message
                        , Toast.LENGTH_SHORT).show();

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



