package com.siduron.java.iTravel.model.datasource;

/**
 * Created by musad on 20/01/2017.
 */


import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.Context;
import android.content.ContextWrapper;
import android.preference.PreferenceManager;
import android.util.Log;

import com.siduron.java.iTravel.model.entities.Activity;
import com.siduron.java.iTravel.model.entities.ActivityAdapter;
import com.siduron.java.iTravel.model.entities.Bussiness;
import com.siduron.java.iTravel.model.entities.Category;
import com.siduron.java.iTravel.model.entities.Gender;
import com.siduron.java.iTravel.model.entities.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.FormatFlagsConversionMismatchException;

import static android.content.Context.MODE_PRIVATE;
import static android.preference.PreferenceManager.*;
import static com.siduron.java.iTravel.model.datasource.iContract.*;
import static com.siduron.java.iTravel.model.datasource.iContract.iSharedPreference.SHARED_NAME;

/**
 *
 * more
 */
public class Tools
{

    private final static  String TAG="Tools";

    public Tools()
    {
        Log.i(TAG,"Created!");
    }


    //Convert Instances to ContentValues

    public static ContentValues UserToContentValues(User user) {
        ContentValues content = new ContentValues();
        content.put(UserFields.ID, user.getId());
        content.put(UserFields.USERNAME, user.getUsername());
        content.put(UserFields.PASSWORD, user.getPassword());
        content.put(UserFields.FIRST_NAME, user.getFirstName());
        content.put(UserFields.LAST_NAME, user.getLastName());
        content.put(UserFields.BIRTHDAY, user.getBirthDay().toString());
        content.put(UserFields.GENDER, String.valueOf(user.getGender()));
        content.put(UserFields.PHONE, user.getPhone());
        content.put(UserFields.ADDRESS, user.getAddress());

        return content;
    }

    public static ContentValues BussinessToContentValues(Bussiness bussiness) {
        ContentValues content = new ContentValues();
        content.put(BussinessFields.ID, bussiness.getId());
        content.put(BussinessFields.NAME, bussiness.getName());
        content.put(BussinessFields.PHONE, bussiness.getPhone());
        content.put(BussinessFields.DESCRIPTION, bussiness.getDescription());

        return content;
    }

    public static ContentValues ActivityToContentValues(Activity activity)
    {
        ContentValues content = new ContentValues();
        content.put(ActivityFields.ID, activity.getId());
        content.put(ActivityFields.BUSSINESS_ID, activity.getBussinessID());
        content.put(ActivityFields.NAME, activity.getActivityName());
        content.put(ActivityFields.DESCRIPTION, activity.getActivityDescription());
        content.put(ActivityFields.START_DATE, activity.getStartDay().toString());
        content.put(ActivityFields.END_DATE, activity.getEndDate().toString());
        content.put(ActivityFields.CATEGORY, String.valueOf(activity.getActivityCategory()));
        content.put(ActivityFields.LOCATION, activity.getActivitylocation());
        content.put(ActivityFields.PRICE, activity.getActivityPrice());

        return content;
    }

    public static ContentValues ActivityAdapterToContentValues(ActivityAdapter adapter) {
        ContentValues content = new ContentValues();
        content.put(ActivityAdapterFields.ID, adapter.getID());
        content.put(ActivityAdapterFields.ACTIVITY_ID, adapter.getID());
        content.put(ActivityAdapterFields.BUSSINESS_ID, adapter.getID());

        return content;
    }


    public static User ContentValuesToUser(ContentValues user)
    {
        int id=-1;
        String username,password,fname,lname,address,phone;
        Date birth=null;
        Gender gender;

        id = user.getAsInteger(UserFields.ID);
        username=user.getAsString(UserFields.USERNAME);
        password=user.getAsString(UserFields.PASSWORD);
        fname=user.getAsString(UserFields.FIRST_NAME);
        lname=user.getAsString(UserFields.LAST_NAME);
        address=user.getAsString(UserFields.ADDRESS);
        phone=user.getAsString(UserFields.PHONE);

        String temp=user.getAsString(UserFields.BIRTHDAY);
        birth=dateFromString(temp);

        gender=Gender.fromInt(user.getAsInteger(UserFields.GENDER));


        //Returns the new user
        return  new User(id,username,password,fname,lname,gender,birth,phone,address);
    }

    public static Bussiness ContentValuesToBussiness(ContentValues bussiness)
    {
        int id=-1;
        String name,description,phone;

        id = bussiness.getAsInteger(BussinessFields.ID);
        name=bussiness.getAsString(BussinessFields.NAME);
        phone=bussiness.getAsString(BussinessFields.PHONE);
        description=bussiness.getAsString(BussinessFields.DESCRIPTION);

        return new Bussiness(id,name,phone,description);
    }

    public static Activity ContentValuesToActivity(ContentValues activity) {
        int id = -1, bussinessId = -1;//V
        double price = 0;//V
        String name, description, location;
        Date start = null, end = null;//V
        Category category;

        id = activity.getAsInteger(ActivityFields.ID);
        bussinessId = activity.getAsInteger(ActivityFields.BUSSINESS_ID);
        name = activity.getAsString(ActivityFields.NAME);
        description = activity.getAsString(ActivityFields.DESCRIPTION);
        location = activity.getAsString(ActivityFields.LOCATION);
        category = Category.fromInt(activity.getAsInteger(ActivityFields.CATEGORY));
        price = activity.getAsDouble(ActivityFields.PRICE);

        String temp = activity.getAsString(ActivityFields.START_DATE);
        start = dateFromString(temp);
        temp = activity.getAsString(ActivityFields.END_DATE);
        end = dateFromString(temp);

        return new Activity(id, bussinessId, name, description, start, end, category, location, price);
    }

    public static ActivityAdapter ContentValuesToActivityAdapter(ContentValues adapter)
    {
        int id=-1,activityId=-1,bussinessId=-1;

        id = adapter.getAsInteger(ActivityAdapterFields.ID);
        activityId=adapter.getAsInteger(ActivityAdapterFields.ACTIVITY_ID);
        bussinessId=adapter.getAsInteger(ActivityAdapterFields.BUSSINESS_ID);

        return new ActivityAdapter(id,activityId,bussinessId);
    }




    /**
     * Convert String date to Date instace
     * @param dateText
     * @return Instace of Date (Java.util)
     */
    private static Date dateFromString(String dateText)
    {
        Date date=null;

        if(dateText!=null&&!dateText.equals("")) {
            try {
                DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
                date = format.parse(dateText);
            }
            catch (ParseException e)
            {
                Log.e(TAG,e.getMessage());
            }
        }
        else
            date=new Date();

        return date;
    }
}
