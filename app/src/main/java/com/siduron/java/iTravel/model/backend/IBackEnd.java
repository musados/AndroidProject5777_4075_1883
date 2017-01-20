package com.siduron.java.iTravel.model.backend;

import android.content.ContentValues;
import android.database.Cursor;
import android.icu.text.DisplayContext;

import com.siduron.java.iTravel.model.entities.Activity;
import com.siduron.java.iTravel.model.entities.ActivityAdapter;
import com.siduron.java.iTravel.model.entities.Bussiness;
import com.siduron.java.iTravel.model.entities.User;


/**
 * Created by musad on 17/01/2017.
 */

public interface IBackEnd {

    int addUser(ContentValues user);
    int addBussines(ContentValues bussines);
    int addActivity(ContentValues activity);
    //int addAdapter(ContentValues activityAdapter);

    boolean removeUser(int userID);
    boolean removeBussines(int bussinessID);
    boolean removeActivity(int activityID);
    //boolean removeActivityAdapter(int adapterID);

    boolean updateUser(int userID, ContentValues values);
    boolean updateBusiness(int bussinessID, ContentValues values);
    boolean updateActivity(int activityID, ContentValues values);

    Cursor getUsers();
    Cursor getBusiness();
    Cursor getActivities();

    boolean isActivitiesChanged();
    boolean isBussinessChanged();
}
