package com.siduron.java.iTravel.Model.DataSource;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.siduron.java.iTravel.Model.Backend.IBackEnd;
import com.siduron.java.iTravel.Model.DataSource.iContract.UserFields;
import com.siduron.java.iTravel.Model.Entities.Activity;
import com.siduron.java.iTravel.Model.Entities.ActivityAdapter;
import com.siduron.java.iTravel.Model.Entities.Bussiness;
import com.siduron.java.iTravel.Model.Entities.User;

import java.util.ArrayList;
import java.util.Date;
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

    private static String TAG = "List Backend";
    private static Date usersChangedTime = null;
    private static Date bussinessChangedTime = null;
    private static Date activityChangedTime = null;
    private static Date adapterChangedTime = null;


    /**
     * Singleton provider - get method
     *
     * @return "Instace" of the class  - List_BackEnd class
     */
    public static List_BackEnd getInstance() {
        return Instance;
    }

    /**
     * Default CTOR (private)
     * Because the class have a Singleton instance
     */
    private List_BackEnd() {
    }

    //When you add instance to one list the id will be from these
    //Variables
    private static int userIndex = 0;
    private static int bussinessIndex = 0;
    private static int activityIndex = 0;
    private static int adapterIndex = 0;

    private static List<User> usersList = new ArrayList<User>();
    private static List<Bussiness> bussinessList = new ArrayList<Bussiness>();
    private static List<Activity> activityList = new ArrayList<Activity>();
    private static List<ActivityAdapter> activityAdapterList = new ArrayList<ActivityAdapter>();

    //Add methods
    @Override
    public int addUser(ContentValues user) {
        boolean flag = false;
        try {
            User temp = Tools.ContentValuesToUser(user);
            temp.setId(++userIndex);
            for (User item : usersList) {
                if (item.getId() == temp.getId() || item.getUsername().equals(temp.getUsername())) {
                    Log.e(TAG, "The user name is exist! choose another!");
                    return -2;
                }
            }
            usersList.add(temp);
            flag = true;
            return userIndex;

        } catch (Exception e) {
            Log.i(TAG, "Field to add user");
            return -1;
        } finally {
            if (flag)
                usersChangedTime = Tools.GetCurrentTime();
        }
    }

    @Override
    public int addBussines(ContentValues bussines) {
        boolean flag = false;
        try {
            Bussiness temp = Tools.ContentValuesToBussiness(bussines);
            temp.setId(++bussinessIndex);
            bussinessList.add(temp);
            flag = true;

            return bussinessIndex;

        } catch (Exception e) {
            Log.i(TAG, "Field to add bussiness");
            return -1;

        } finally {
            if (flag)
                bussinessChangedTime = Tools.GetCurrentTime();
        }

    }

    @Override
    public int addActivity(ContentValues activity) {
        boolean flag = false;
        try {
            Activity temp = Tools.ContentValuesToActivity(activity);
            temp.setId(++activityIndex);
            activityList.add(temp);
            flag = true;

            return activityIndex;

        } catch (Exception e) {
            Log.i(TAG, "Field to add activity");
            return -1;
        } finally {
            if (flag)
                activityChangedTime = Tools.GetCurrentTime();
        }
    }

    @Override
    public int addAdapter(ContentValues activityAdapter) {
        boolean flag = false;
        try {
            ActivityAdapter temp = Tools.ContentValuesToActivityAdapter(activityAdapter);
            temp.setId(++adapterIndex);
            activityAdapterList.add(temp);
            flag = true;

            return adapterIndex;

        } catch (Exception e) {
            Log.i(TAG, "Field to add adapter");
            return -1;
        } finally {
            if (flag)
                adapterChangedTime = Tools.GetCurrentTime();
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
        if (removed) {
            usersChangedTime = Tools.GetCurrentTime();
            return 1;
        }
        return 0;
    }

    @Override
    public int removeBussines(int bussinessID) {

        boolean removed = false;
        for (Bussiness item : bussinessList) {
            if (item.getId() == bussinessID)
                removed = bussinessList.remove(item);
        }
        if (removed) {
            bussinessChangedTime = Tools.GetCurrentTime();
            return 1;
        }
        return 0;
    }

    @Override
    public int removeActivity(int activityID) {

        boolean removed = false;
        for (Activity item : activityList) {
            if (item.getId() == activityID)
                removed = usersList.remove(item);
        }
        if (removed) {
            activityChangedTime = Tools.GetCurrentTime();
            return 1;
        }
        return 0;
    }

    @Override
    public int removeActivityAdapter(int adapterID) {

        boolean removed = false;
        for (ActivityAdapter item : activityAdapterList) {
            if (item.getID() == adapterID)
                removed = usersList.remove(item);
        }
        if (removed) {
            adapterChangedTime = Tools.GetCurrentTime();
            return 1;
        }
        return 0;
    }


    //Update methods

    @Override
    public boolean updateUser(int userID, ContentValues values) {
        boolean found = false;

        for (User item : usersList) {
            if (item.getId() == userID) {
                User temp = Tools.ContentValuesToUser(values);
                int index = usersList.indexOf(item);
                found = usersList.remove(item);
                if (found) {
                    usersList.add(index, temp);
                    usersChangedTime = Tools.GetCurrentTime();
                    return true;
                } else
                    return false;
            }
        }
        return false;
    }

    @Override
    public boolean updateBusiness(int bussinessID, ContentValues values) {

        boolean found = false;

        for (Bussiness item : bussinessList) {
            if (item.getId() == bussinessID) {
                Bussiness temp = Tools.ContentValuesToBussiness(values);
                int index = bussinessList.indexOf(item);
                found = bussinessList.remove(item);
                if (found) {
                    bussinessList.add(index, temp);
                    bussinessChangedTime = Tools.GetCurrentTime();
                    return true;
                } else
                    return false;
            }
        }
        return false;
    }

    @Override
    public boolean updateActivity(int activityID, ContentValues values) {

        boolean found = false;

        for (Activity item : activityList) {
            if (item.getId() == activityID) {
                Activity temp = Tools.ContentValuesToActivity(values);
                int index = activityList.indexOf(item);
                found = activityList.remove(item);
                if (found) {
                    activityList.add(index, temp);
                    activityChangedTime = Tools.GetCurrentTime();
                    return true;
                } else
                    return false;
            }
        }
        return false;
    }

    @Override
    public boolean updateActivityAdapter(int adapterID, ContentValues values) {

        boolean found = false;

        for (ActivityAdapter item : activityAdapterList) {
            if (item.getID() == adapterID) {
                ActivityAdapter temp = Tools.ContentValuesToActivityAdapter(values);
                int index = activityAdapterList.indexOf(item);
                found = activityAdapterList.remove(item);
                if (found) {
                    activityAdapterList.add(index, temp);
                    adapterChangedTime = Tools.GetCurrentTime();
                    return true;
                } else
                    return false;
            }
        }
        return false;
    }


    //Get cursor by generic method in the Tools class
    //provides the cursor of the given list by her columns and type


    @Override
    public Cursor getUsers() {
        return Tools.listToCursor(usersList, User.class, UserFields.COLUMNS);
    }

    @Override
    public Cursor getBusiness() {
        return Tools.listToCursor(bussinessList, Bussiness.class, iContract.BussinessFields.COLUMNS);
    }

    @Override
    public Cursor getActivities() {

        return Tools.listToCursor(activityList, Activity.class, iContract.ActivityFields.COLUMNS);
    }

    @Override
    public Cursor getAdapters() {

        return Tools.listToCursor(activityAdapterList, ActivityAdapter.class, iContract.ActivityAdapterFields.COLUMNS);
    }


    /**
     * if the activities list was changed after the received date
     *
     * @param date received date of who make the query last known changed time
     * @return if changed true else false
     */
    @Override
    public boolean isActivitiesChanged(Date date) {

        if (activityChangedTime != null && date.compareTo(activityChangedTime) < 0)
            return true;
        return false;
    }

    /**
     * if the bussinesses list was changed after the received date
     *
     * @param date received date of who make the query last known changed time
     * @return if changed true else false
     */
    @Override
    public boolean isBussinessChanged(Date date) {

        if (bussinessChangedTime != null && date.compareTo(bussinessChangedTime) < 0)
            return true;
        return false;
    }
}
