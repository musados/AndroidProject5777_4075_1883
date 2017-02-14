package com.siduron.java.iTravel.Model.Backend;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;


/**
 * Created by musad on 17/01/2017.
 */

public interface IBackEnd {

    int addUser(ContentValues user);
    int addBussines(ContentValues business);
    int addActivity(ContentValues activity);
    int addAdapter(ContentValues activityAdapter);
    int addBusinessAdapter(ContentValues businessAdapter);

    int removeUser(int userID);
    int removeBussines(int bussinessID);
    int removeActivity(int activityID);
    int removeActivityAdapter(int adapterID);
    int removeBusinessAdapter(int adapterID);

    boolean updateUser(int userID, ContentValues values);
    boolean updateBusiness(int bussinessID, ContentValues values);
    boolean updateActivity(int activityID, ContentValues values);
    boolean updateActivityAdapter(int adapterID, ContentValues values);
    boolean updateBusinessAdapter(int adapterID, ContentValues values);

    Cursor getUsers();
    Cursor getBusiness();
    Cursor getActivities();
    Cursor getAdapters();
    Cursor getBusinessAdapters();

    boolean isActivitiesChanged(Date date);
    boolean isBussinessChanged(Date date);
}
