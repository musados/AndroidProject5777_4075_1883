package com.siduron.java.iTravel.Model.DataSource;

/**
 * Created by musad on 20/01/2017.
 */


import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.util.Log;

import com.siduron.java.iTravel.Model.Entities.Activity;
import com.siduron.java.iTravel.Model.Entities.ActivityAdapter;
import com.siduron.java.iTravel.Model.Entities.Bussiness;
import com.siduron.java.iTravel.Model.Entities.Category;
import com.siduron.java.iTravel.Model.Entities.Gender;
import com.siduron.java.iTravel.Model.Entities.ITravelData;
import com.siduron.java.iTravel.Model.Entities.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.siduron.java.iTravel.Model.DataSource.iContract.*;

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
        content.put(UserFields.BIRTHDAY, dateToString(user.getBirthDay()));
        content.put(UserFields.GENDER, user.getGender().toString());
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
        content.put(ActivityFields.START_DATE, dateToString(activity.getStartDay()));
        content.put(ActivityFields.END_DATE, dateToString(activity.getEndDate()));
        content.put(ActivityFields.CATEGORY, activity.getActivityCategory().toString());
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

        gender=Gender.fromString(user.getAsString(UserFields.GENDER));


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
        category = Category.fromString(activity.getAsString(ActivityFields.CATEGORY));
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


    public static <ITRavelData> Cursor listToCursor(List<ITRavelData> arrayList,Class<ITRavelData> objectClass ,String[] columns) {
        MatrixCursor matrix = new MatrixCursor(columns);
        if (objectClass != null && (objectClass == User.class || objectClass == Bussiness.class ||
                objectClass == Activity.class || objectClass == ActivityAdapter.class)) {
            try {


                for (ITRavelData item : arrayList) {
                    ITravelData t = (ITravelData) item;
                    matrix.addRow(t.getRowData());
                }
            } catch (Exception e) {
                Log.e(TAG, "Error during casting of List<" + objectClass.toString() + "> to MatrixCursor");
                return null;
            }
        }

        return matrix;
    }

    public static Date GetCurrentTime()
    {
        return Calendar.getInstance().getTime();
    }


    /**
     * Convert String date to Date instace
     * @param dateText
     * @return Instace of Date (Java.util)
     */
    public static Date dateFromString(String dateText)
    {
        Date date=null;
        Log.i(TAG,"Trying to parse: "+dateText);

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

    public static String dateToString(Date date) {
        String text;
        Log.i(TAG, "Trying to parse: " + date.toString());

        if (date != null) {
            DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
            text = format.format(date);
        } else
            text = "1/01/1900";

        return text;
    }
}
