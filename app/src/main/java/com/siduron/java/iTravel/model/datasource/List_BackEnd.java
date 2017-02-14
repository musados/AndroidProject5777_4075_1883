package com.siduron.java.iTravel.Model.DataSource;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.siduron.java.iTravel.Model.Backend.IBackEnd;
import com.siduron.java.iTravel.Model.DataSource.iContract.UserFields;
import com.siduron.java.iTravel.Model.Entities.Activity;
import com.siduron.java.iTravel.Model.Entities.ActivityAdapter;
import com.siduron.java.iTravel.Model.Entities.Business;
import com.siduron.java.iTravel.Model.Entities.BusinessAdapter;
import com.siduron.java.iTravel.Model.Entities.Gender;
import com.siduron.java.iTravel.Model.Entities.User;

import java.util.ArrayList;
import java.util.Calendar;
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
    private static Date businessAdapterChangedTime = null;


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

        /*if(businessList==null)
            businessList=new ArrayList<Business>();
        if(usersList==null)
            usersList=new ArrayList<User>();*/

        usersList = new ArrayList<User>();
        businessList = new ArrayList<Business>();
        activityList = new ArrayList<Activity>();
        activityAdapterList = new ArrayList<ActivityAdapter>();
        businessAdapterList = new ArrayList<BusinessAdapter>();

        /*businessList.add(new Business(
                1234,
                1235,
                "Mushon",
                "siduron10@gmail.com",
                "+972509374049",
                "Jerusalem",
                "http://mousephone.siduron.com",
                "Test business account"));*/

        usersList.add(new User(
                1235,
                "musados@gmail.com",
                "Password1",
                "משה", "נהרי"
                , Gender.MALE,
                Calendar.getInstance().getTime(),
                "0509374049",
                "רבבות אפרים 38, קדומים"));

        /*businessAdapterList.add(new BusinessAdapter(1236,1235,1234));*/
    }


    //When you add instance to one list the id will be from these
    //Variables
    private static int index = 10000000;

    private static List<User> usersList;//= new ArrayList<User>();
    private static List<Business> businessList;// = new ArrayList<Business>();
    private static List<Activity> activityList;//= new ArrayList<Activity>();
    private static List<ActivityAdapter> activityAdapterList;//= new ArrayList<ActivityAdapter>();
    private static List<BusinessAdapter> businessAdapterList;// = new ArrayList<BusinessAdapter>();

    //Add methods
    @Override
    public int addUser(ContentValues user) {
        boolean flag = false;
        try {
            User temp = Tools.ContentValuesToUser(user);
            temp.setId(index + 1);
            for (User item : usersList) {
                if (item.getId() == temp.getId() || item.getUsername().equals(temp.getUsername())) {
                    Log.e(TAG, "The user name is exist! choose another!");
                    return -2;
                }
            }
            usersList.add(temp);
            flag = true;
            return index + 1;

        } catch (Exception e) {
            Log.i(TAG, "Field to add user");
            return -1;
        } finally {
            if (flag) {
                index++;
                usersChangedTime = Tools.GetCurrentTime();
            }
        }
    }

    @Override
    public int addBussines(ContentValues business) {
        boolean flag = false;
        try {
            Business temp = Tools.ContentValuesToBussiness(business);
            temp.setId(index + 1);
            for (Business item : businessList) {
                if (item.getId() == temp.getId())
                    temp.setId(temp.getId() + 1);
                else if (item.getName().equals(temp.getName()))
                    return -2;
            }
            businessList.add(temp);
            flag = true;

            return index + 1;

        } catch (Exception e) {
            Log.i(TAG, "Field to add bussiness");
            return -1;

        } finally {
            if (flag) {
                index++;
                bussinessChangedTime = Tools.GetCurrentTime();
            }
        }

    }

    @Override
    public int addActivity(ContentValues activity) {
        boolean flag = false;
        try {
            Activity temp = Tools.ContentValuesToActivity(activity);
            temp.setId(++index);
            activityList.add(temp);
            activityAdapterList.add(new ActivityAdapter(++index, temp.getId(), temp.getBussinessID()));
            flag = true;

            return index;

        } catch (Exception e) {
            Log.i(TAG, "Field to add activity");
            return -1;
        } finally {
            if (flag) {
                activityChangedTime = Tools.GetCurrentTime();
            }
        }
    }

    @Override
    public int addAdapter(ContentValues activityAdapter) {
        boolean flag = false;
        try {
            ActivityAdapter temp = Tools.ContentValuesToActivityAdapter(activityAdapter);
            temp.setId(index + 1);
            activityAdapterList.add(temp);
            flag = true;

            return index + 1;

        } catch (Exception e) {
            Log.i(TAG, "Field to add adapter");
            return -1;
        } finally {
            if (flag) {
                index++;
                adapterChangedTime = Tools.GetCurrentTime();
            }
        }
    }

    @Override
    public int addBusinessAdapter(ContentValues businessAdapter) {
        boolean flag = false;
        try {
            BusinessAdapter temp = Tools.ContentValuesToBusinessAdapter(businessAdapter);
            temp.id = index + 1;
            businessAdapterList.add(temp);
            flag = true;

            return index + 1;

        } catch (Exception e) {
            Log.i(TAG, "Field to add business adapter");
            return -1;
        } finally {
            if (flag) {
                index++;
                businessAdapterChangedTime = Tools.GetCurrentTime();
            }
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
        for (Business item : businessList) {
            if (item.getId() == bussinessID)
                removed = businessList.remove(item);
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
                removed = activityList.remove(item);
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
                removed = activityAdapterList.remove(item);
        }
        if (removed) {
            adapterChangedTime = Tools.GetCurrentTime();
            return 1;
        }
        return 0;
    }

    @Override
    public int removeBusinessAdapter(int adapterID) {
        boolean removed = false;
        for (BusinessAdapter item : businessAdapterList) {
            if (item.id == adapterID)
                removed = businessAdapterList.remove(item);
        }
        if (removed) {
            businessAdapterChangedTime = Tools.GetCurrentTime();
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

        for (Business item : businessList) {
            if (item.getId() == bussinessID) {
                Business temp = Tools.ContentValuesToBussiness(values);
                int index = businessList.indexOf(item);
                found = businessList.remove(item);
                if (found) {
                    businessList.add(index, temp);
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

    @Override
    public boolean updateBusinessAdapter(int adapterID, ContentValues values) {
        boolean found = false;

        for (BusinessAdapter item : businessAdapterList) {
            if (item.id == adapterID) {
                BusinessAdapter temp = Tools.ContentValuesToBusinessAdapter(values);
                int index = businessAdapterList.indexOf(item);
                found = businessAdapterList.remove(item);
                if (found) {
                    businessAdapterList.add(index, temp);
                    businessAdapterChangedTime = Tools.GetCurrentTime();
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
        return Tools.listToCursor(businessList, Business.class, iContract.BussinessFields.COLUMNS);
    }

    @Override
    public Cursor getActivities() {

        return Tools.listToCursor(activityList, Activity.class, iContract.ActivityFields.COLUMNS);
    }

    @Override
    public Cursor getAdapters() {

        return Tools.listToCursor(activityAdapterList, ActivityAdapter.class, iContract.ActivityAdapterFields.COLUMNS);
    }

    @Override
    public Cursor getBusinessAdapters() {
        return Tools.listToCursor(businessAdapterList, BusinessAdapter.class, iContract.BusinessAdapterFields.COLUMNS);
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
