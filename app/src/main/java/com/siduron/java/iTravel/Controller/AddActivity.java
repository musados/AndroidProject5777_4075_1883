package com.siduron.java.iTravel.Controller;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.siduron.java.androidproject5777_4075_4075.R;
import com.siduron.java.iTravel.Model.Backend.iTravelContentProvider;
import com.siduron.java.iTravel.Model.DataSource.Tools;
import com.siduron.java.iTravel.Model.DataSource.iContract;
import com.siduron.java.iTravel.Model.Entities.Activity;
import com.siduron.java.iTravel.Model.Entities.Business;
import com.siduron.java.iTravel.Model.Entities.Category;
import com.siduron.java.iTravel.Model.Entities.Gender;
import com.siduron.java.iTravel.Model.Entities.NavDrawerItem;
import com.siduron.java.iTravel.Model.Entities.User;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.siduron.java.iTravel.Model.DataSource.iContract.UserFields.USER_MAIN_KEY;
import static com.siduron.java.iTravel.Model.DataSource.iContract.iSharedPreference.LAST_USER_NAME;
import static com.siduron.java.iTravel.Model.DataSource.iContract.iSharedPreference.LAST_USER_PASSWORD;
import static com.siduron.java.iTravel.Model.DataSource.iContract.iSharedPreference.SAVE_LAST_USER;

public class AddActivity extends AppCompatActivity {

    iTravelContentProvider provider=new iTravelContentProvider();
    private User loggedUser;
    private ArrayList<Business> myBusinesses;

    private RelativeLayout mainLayout;
    private Spinner businessIDSpinner;
    private EditText activityName;
    private EditText activityDescription;
    private EditText activityLocation;
    private EditText activityPrice;
    DateTextPicker startDatePicker;
    DateTextPicker endDatePicker;
    private Spinner activityCategorySpinner;
    ArrayAdapter<String> businessAdapter;
    final ArrayList<String> userBusinessIds = new ArrayList<String>();

    HideProgress hideLoading;

    Button addButton;

    private Date startDate=new Date();
    private Date endDate=new Date();
    private Category selectedCategory=Category.SHORT_TRACK;
    private int selectedBusinessId=0;

    private final String TAG="Add activity";
    Context context=this;
    AddActivityTask addTask=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        mainLayout=(RelativeLayout)findViewById(R.id.AddActivityRelativeLayout);

        //#######  sets the loading cover   ###########
        ProgressBar _hideProg=(ProgressBar)findViewById(R.id.add_activity_progress);
        TextView _hideText = (TextView)findViewById(R.id.add_activity_progrees_text);

        hideLoading=new HideProgress(mainLayout,_hideProg,_hideText);
        hideLoading.showProgress(true);
        //#############################################

        try {
            loggedUser = (User) getIntent().getSerializableExtra(USER_MAIN_KEY);
        }
        catch (Exception e)
        {
            Log.e(TAG,"Failed to get the logged user.\n"+e.getMessage());
            Toast.makeText(AddActivity.this,"Failed to get the logged user",Toast.LENGTH_LONG).show();
            finish();
        }



        businessIDSpinner=(Spinner)findViewById(R.id.BussinessId);
        activityName=(EditText)findViewById(R.id.ActivityName);
        activityDescription=(EditText)findViewById(R.id.ActivityDescription);
        activityLocation=(EditText)findViewById(R.id.ActivityLocation);
        activityPrice=(EditText)findViewById(R.id.ActivityPrice);
        startDatePicker=(DateTextPicker)findViewById(R.id.StartDate);
        endDatePicker=(DateTextPicker)findViewById(R.id.EndDate);
        activityCategorySpinner=(Spinner)findViewById(R.id.ActivityCategory);
        addButton=(Button)findViewById(R.id.AddButton);



    }

    @Override
    protected void onStart()
    {
        super.onStart();
        //Validate that the start date will be before end date
        startDatePicker.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.w(TAG,"Editor listener started");
                if(startDatePicker!=null)
                {
                    Date temp = startDatePicker.getDate();
                    if(endDatePicker!=null&&(endDatePicker.getCalendar().getTimeInMillis())<=(startDatePicker.getCalendar().getTimeInMillis()))
                    {
                        temp.setHours(temp.getHours()+1);
                        endDatePicker.setDate(temp);
                    }
                }
            }
        });

        setSpinners();
        hideLoading.showProgress(false);
    }

    private void setSpinners() {
        final ArrayList<Category> categoryList = new ArrayList<Category>();
        categoryList.add(Category.DAY_TRIP);
        categoryList.add(Category.GUIDED_TOUR);
        categoryList.add(Category.HOTEL);
        categoryList.add(Category.WEEKEND);
        categoryList.add(Category.SHORT_TRACK);
        categoryList.add(Category.FAMILIS_TRIP);


        ArrayAdapter<CharSequence> categoryAdapter;

        categoryAdapter = ArrayAdapter.createFromResource(this, R.array.category_items_array, R.layout.category_spinner_item);
        categoryAdapter.setDropDownViewResource(R.layout.category_spinner_item);

        activityCategorySpinner.setAdapter(categoryAdapter);

        activityCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    selectedCategory = Category.fromInt(i);
                    Log.w(TAG,"selected category:"+selectedCategory);
                }
                catch (Exception e)
                {
                    Log.e(TAG,"Error during selecting category\n"+e.getMessage());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        businessAdapter =
                new ArrayAdapter<String>(AddActivity.this, R.layout.category_spinner_item, userBusinessIds);


        businessAdapter.setDropDownViewResource(R.layout.category_spinner_item);


        businessIDSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    selectedBusinessId = Integer.parseInt(businessIDSpinner.getSelectedItem().toString());
                } catch (Exception pe) {
                    Log.e(TAG, "Error during parsing selected business\n" + pe.getMessage());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        businessIDSpinner.setAdapter(businessAdapter);

        addTask=new AddActivityTask(R.id.BussinessId,null);
        addTask.execute(false);
    }

    public void tryToAddActivity(View view) {
        if (checkFields()) {
            Activity newActivity = new Activity(
                    0,
                    selectedBusinessId, activityName.getText().toString(),
                    activityDescription.getText().toString(),
                    startDatePicker.getDate(),
                    endDatePicker.getDate(),
                    selectedCategory,
                    activityLocation.getText().toString(),
                    Double.parseDouble(activityPrice.getText().toString()));

            addTask = new AddActivityTask(R.id.AddButton, newActivity);
            addTask.execute(true);
        }

        Log.i(TAG, "Trying to add activity");
    }


    /**
     * Check if all fields filled correctly
     * if true do nothing just return true
     * else mark all incorrect fields and focus on the first wrong field
     * @return if the fields are correct
     */
    private boolean checkFields() {
        View toFocus=null;
        boolean cancel = false;
        Calendar tempCalendar = Calendar.getInstance();
        Date minStart = tempCalendar.getTime();
        //minStart.setMinutes(minStart.getMinutes() + 10);
        tempCalendar.setTime(minStart);

        clearAllErrors();

        if (activityPrice.getText().toString().equals("")) {
            activityPrice.setError(getResources().getString(R.string.error_field_required));
            toFocus=activityPrice;
            cancel=true;
        }
        if (activityLocation.getText().equals("")) {
            activityLocation.setError(getResources().getString(R.string.error_field_required));
            toFocus=activityLocation;
            cancel=true;
        }
        if (endDatePicker.getCalendar().getTimeInMillis() < startDatePicker.getCalendar().getTimeInMillis()) {
            endDatePicker.setError(getResources().getString(R.string.end_date_error));
            toFocus=endDatePicker;
            cancel=true;

        }
        if (startDatePicker.getCalendar().getTime().getDay() < tempCalendar.getTime().getDay()) {
            startDatePicker.setError(getResources().getString(R.string.start_date_error));
            toFocus=startDatePicker;
            cancel=true;
        }
        if (activityDescription.getText().toString().equals("")) {
            activityDescription.setError(getResources().getString(R.string.error_field_required));
            toFocus=activityDescription;
            cancel=true;
        }
        if (activityName.getText().toString().equals("")) {
            activityName.setError(getResources().getString(R.string.error_field_required));
            toFocus=activityName;
            cancel=true;
        }

        if (selectedBusinessId==0) {
            Toast.makeText(AddActivity.this,getResources().getString(R.string.business_id_error),Toast.LENGTH_LONG).show();
            toFocus=businessIDSpinner;
            cancel=true;
        }

        if(cancel) {
            if (toFocus != null)
                toFocus.requestFocus();
        }


        return !cancel;
    }

    private void clearAllErrors() {
        for(int index=0;index<mainLayout.getChildCount();index++)
        {
            View temp=mainLayout.getChildAt(index);
            if(temp instanceof TextView) {
                ((TextView) temp).setError(null);
                temp.requestFocus();
                Log.i(TAG,temp.toString()+" Cleared!");
            }
            else if(temp instanceof DateTextPicker) {
                ((DateTextPicker) temp).setError(null);
                temp.requestFocus();
                Log.i(TAG,temp.toString()+" Cleared!");
            }
        }
    }

    /**
     * Add activity task
     *
     * @author Moshe Nahari & Haim Milikovsli
     */
    private class AddActivityTask extends AsyncTask<Boolean,Void,Boolean>
    {
        ProgressDialog dialog ;
        Activity activity;
        Cursor cursor;
        int result=-1;
        int operationId=0;
        ArrayList<String>bList=new ArrayList<String>();

        /**
         * Task CTOR

         */
        public AddActivityTask(int operationID, Activity ac) {
            operationId = operationID;
            activity = ac;
            cursor=null;
        }



        @Override
        protected void onPreExecute() {
            Log.i(TAG, "Starting pre progress");
            addButton.setEnabled(false);
            dialog=  ProgressDialog.show(AddActivity.this,
                    getResources().getString(R.string.add_activity_process_title),
                    getResources().getString(R.string.proccess_running_wait));
            Log.i(TAG, "Progress started");
        }



        @Override
        protected Boolean doInBackground(Boolean... booleen) {
            try {
                // Simulate network access.
                Thread.sleep(100);
                switch (operationId) {
                    case R.id.AddButton:
                        return operateAdding();
                    case R.id.BussinessId:
                            try {
                            bList.clear();
                            //Load the user businesses
                            cursor = provider.query(iContract.BussinessFields.BUSSINESS_URI, null, null, null, null);
                            Log.w(TAG, "Cursor count: " + cursor.getCount() + " Cursor columns count: " + cursor.getColumnCount());
                            //Add the ids to the adapter
                            while (cursor.moveToNext()) {
                                if (cursor.getInt(1) == loggedUser.getId()) {
                                    bList.add("" + cursor.getInt(0));

                                    Log.w(TAG, "Business ID: " + cursor.getInt(0) + "\nmanager ID: " +
                                            cursor.getInt(1) + " Added! ####");
                                }
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Field to load businesses"+e.getMessage());
                                e.printStackTrace();
                            return false;
                        }
                        return true;
                    default:
                        return false;
                }
            } catch (InterruptedException e) {
                return false;
            }
        }

        private Boolean operateAdding() {
            ContentValues content = Tools.ActivityToContentValues(activity);
            Uri uri = provider.insert(iContract.ActivityFields.ACTIVITY_URI, content);
            String resultId = uri.getLastPathSegment();

            try {
                result = Integer.parseInt(resultId);
            } catch (Exception e) {
                Log.e(TAG, "The uri:" + uri + "\nresultID:" + resultId + "\n" + e.getMessage());
                result = -1;
            }

            if (result >= 0) {
                Log.i(TAG, "Activity created!\nID: " + resultId);
                return true;
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            Log.i(TAG,"Started");
            //Hide the dialog
            dialog.dismiss();

            Log.i(TAG,"dialog hidded");
            addButton.setEnabled(true);
            if (success)
            {
                switch (operationId) {
                    case R.id.AddButton:
                        finish();
                        break;
                    case R.id.BussinessId:
                        businessAdapter.clear();
                        businessAdapter.addAll(bList);

                        if(userBusinessIds==null||userBusinessIds.size()==0)
                        {
                            Toast.makeText(AddActivity.this,
                                    getResources().getString(R.string.add_activity_must_be_business),
                                    Toast.LENGTH_LONG).show();
                            finish();
                        }
                        /*else {
                            for (Business item : myBusinesses) {

                            }
                        }*/
                }
            }
            else
            {

            }
            addTask=null;
            Log.i(TAG,"Finished");
        }

        @Override
        protected void onCancelled() {
            dialog.dismiss();
            addTask=null;
            addButton.setEnabled(true);
        }
    }




}
