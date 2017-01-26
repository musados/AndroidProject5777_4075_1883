package com.siduron.java.iTravel.Model.DataSource;

import android.content.ContentValues;
import android.database.Cursor;

import com.siduron.java.iTravel.Model.Backend.IBackEnd;

import java.util.Date;

/**
 * Created by musad on 17/01/2017.
 */

/**
 *Class who manage the SQL light DB
 *
 * @author Moshe Nahari & Haim Milikovski
 */
public class DBS_BackEnd implements IBackEnd {

    static DBS_BackEnd Instance = new DBS_BackEnd();


    /**
     * Singleton provider - get method
     *
     * @return "Instace" of the class  - DBS_BackEnd class
     */
    public static DBS_BackEnd getInstance(){ return Instance;}

    /**
     * Default CTOR (private)
     * Because the class have a Singleton instance
     */
    private DBS_BackEnd()
    {}

    @Override
    public int addUser(ContentValues user) {
        return 0;
    }

    @Override
    public int addBussines(ContentValues bussines) {
        return 0;
    }

    @Override
    public int addActivity(ContentValues activity) {
        return 0;
    }

    @Override
    public int addAdapter(ContentValues activityAdapter) {
        return 0;
    }

    @Override
    public int removeUser(int userID) {
        return 0;
    }

    @Override
    public int removeBussines(int bussinessID) {
        return 0;
    }

    @Override
    public int removeActivity(int activityID) {
        return 0;
    }

    @Override
    public int removeActivityAdapter(int adapterID) {
        return 0;
    }

    @Override
    public boolean updateUser(int userID, ContentValues values) {
        return false;
    }

    @Override
    public boolean updateBusiness(int bussinessID, ContentValues values) {
        return false;
    }

    @Override
    public boolean updateActivity(int activityID, ContentValues values) {
        return false;
    }

    @Override
    public boolean updateActivityAdapter(int adapterID, ContentValues values) {
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
    public boolean isActivitiesChanged(Date date) {
        return false;
    }

    @Override
    public boolean isBussinessChanged(Date date) {
        return false;
    }
}
