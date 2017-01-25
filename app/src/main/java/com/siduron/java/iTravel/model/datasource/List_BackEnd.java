package com.siduron.java.iTravel.model.datasource;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.siduron.java.iTravel.model.backend.IBackEnd;
import com.siduron.java.iTravel.model.datasource.Tools;
import com.siduron.java.iTravel.model.datasource.iContract.UserFields;
import com.siduron.java.iTravel.model.entities.Activity;
import com.siduron.java.iTravel.model.entities.ActivityAdapter;
import com.siduron.java.iTravel.model.entities.Bussiness;
import com.siduron.java.iTravel.model.entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by musad on 17/01/2017.
 */

/**
 *IBackend implementation of List DB
 *
 * @author Moshe Nahari & Haim Milikovski
 */
public class List_BackEnd implements IBackEnd {

    //Signleton instance
    private static List_BackEnd Instance = new List_BackEnd();

    private static String TAG="List Backend";


    /**
     * Singleton provider - get method
     *
     * @return "Instace" of the class  - List_BackEnd class
     */
    public static List_BackEnd getInstance(){ return Instance;}

    /**
     * Default CTOR (private)
     * Because the class have a Singleton instance
     */
    private List_BackEnd()
    {}

    //When you add instance to one list the id will be from these
    //Variables
    private static int userIndex=0;
    private static int bussinessIndex=0;
    private static int activityIndex=0;
    private static int adapterIndex=0;

    private static List<User> usersList=new ArrayList<User>();
    private static List<Bussiness> bussinessList=new ArrayList<Bussiness>();
    private static List<Activity> activityList=new ArrayList<Activity>();
    private static List<ActivityAdapter> activityAdapterList=new ArrayList<ActivityAdapter>();

    //Add methods
    @Override
    public int addUser(ContentValues user)
    {
        try {
            User temp = Tools.ContentValuesToUser(user);
            temp.setId(++userIndex);
            usersList.add(temp);
            return userIndex;
        }
        catch (Exception e) {
            Log.i(TAG,"Field to add user");
            return -1;
        }
    }

    @Override
    public int addBussines(ContentValues bussines) {
        try {
            Bussiness temp = Tools.ContentValuesToBussiness(bussines);
            temp.setId(++bussinessIndex);
            bussinessList.add(temp);
            return bussinessIndex;
        } catch (Exception e) {
            Log.i(TAG, "Field to add bussiness");
            return -1;
        }
    }

    @Override
    public int addActivity(ContentValues activity) {
        try {
            Activity temp = Tools.ContentValuesToActivity(activity);
            temp.setId(++activityIndex);
            activityList.add(temp);
            return activityIndex;
        } catch (Exception e) {
            Log.i(TAG, "Field to add activity");
            return -1;
        }
    }

    @Override
    public int addAdapter(ContentValues activityAdapter) {
        try {
            ActivityAdapter temp = Tools.ContentValuesToActivityAdapter(activityAdapter);
            temp.setId(++adapterIndex);
            activityAdapterList.add(temp);
            return adapterIndex;
        } catch (Exception e) {
            Log.i(TAG, "Field to add adapter");
            return -1;
        }
    }

    //Remove methods
    @Override
    public int removeUser(int userID) {
        boolean removed = false;
        for (User item : usersList) {
            if (item.getId() == userID)
                removed = usersList.remove(item);
        }
        if (removed)
            return 1;
        return 0;
    }

    @Override
    public int removeBussines(int bussinessID) {

        boolean removed = false;
        for (Bussiness item : bussinessList) {
            if (item.getId() == bussinessID)
                removed = bussinessList.remove(item);
        }
        if (removed)
            return 1;
        return 0;
    }

    @Override
    public int removeActivity(int activityID) {

        boolean removed = false;
        for (Activity item : activityList) {
            if (item.getId() == activityID)
                removed = usersList.remove(item);
        }
        if (removed)
            return 1;
        return 0;
    }

    @Override
    public int removeActivityAdapter(int adapterID) {

        boolean removed = false;
        for (ActivityAdapter item : activityAdapterList) {
            if (item.getID() == adapterID)
                removed = usersList.remove(item);
        }
        if (removed)
            return 1;
        return 0;
    }


    //Update methods

    @Override
    public boolean updateUser(int userID, ContentValues values) {
        boolean found=false;

        for (User item:usersList)
        {
            if(item.getId()==userID)
            {
                User temp=Tools.ContentValuesToUser(values);
                int index=usersList.indexOf(item);
                found = usersList.remove(item);
                if (found) {
                    usersList.add(index, temp);
                    return true;
                }
                else
                    return false;
            }
        }
        return false;
    }

    @Override
    public boolean updateBusiness(int bussinessID, ContentValues values) {

        boolean found=false;

        for (Bussiness item:bussinessList)
        {
            if(item.getId()==bussinessID)
            {
                Bussiness temp=Tools.ContentValuesToBussiness(values);
                int index=bussinessList.indexOf(item);
                found = bussinessList.remove(item);
                if (found) {
                    bussinessList.add(index, temp);
                    return true;
                }
                else
                    return false;
            }
        }
        return false;
    }

    @Override
    public boolean updateActivity(int activityID, ContentValues values) {

        boolean found=false;

        for (Activity item:activityList)
        {
            if(item.getId()==activityID)
            {
                Activity temp=Tools.ContentValuesToActivity(values);
                int index=activityList.indexOf(item);
                found = activityList.remove(item);
                if (found) {
                    activityList.add(index, temp);
                    return true;
                }
                else
                    return false;
            }
        }
        return false;
    }

    @Override
    public boolean updateActivityAdapter(int adapterID, ContentValues values) {

        boolean found=false;

        for (ActivityAdapter item:activityAdapterList)
        {
            if(item.getID()==adapterID)
            {
                ActivityAdapter temp=Tools.ContentValuesToActivityAdapter(values);
                int index=activityAdapterList.indexOf(item);
                found = activityAdapterList.remove(item);
                if (found) {
                    activityAdapterList.add(index, temp);
                    return true;
                }
                else
                    return false;
            }
        }
        return false;
    }


    @Override
    public Cursor getUsers() {
        return null;
    }

    @Override
    public Cursor getBusiness() {
        return null;
    }

    @Override
    public Cursor getActivities() {
        return null;
    }

    @Override
    public Cursor getAdapters() {
        return null;
    }


    @Override
    public boolean isActivitiesChanged() {
        return false;
    }

    @Override
    public boolean isBussinessChanged() {
        return false;
    }
}
