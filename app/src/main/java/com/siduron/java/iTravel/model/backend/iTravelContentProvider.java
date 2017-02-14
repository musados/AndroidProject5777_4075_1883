package com.siduron.java.iTravel.Model.Backend;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import com.siduron.java.iTravel.Model.DataSource.iContract;
import com.siduron.java.iTravel.Model.DataSource.iContract.UserFields;
import com.siduron.java.iTravel.Model.DataSource.iContract.BussinessFields;
import com.siduron.java.iTravel.Model.DataSource.iContract.ActivityFields;
import com.siduron.java.iTravel.Model.DataSource.iContract.ActivityAdapterFields;


import android.util.Log;

import com.siduron.java.iTravel.Model.Entities.BusinessAdapter;
import com.siduron.java.iTravel.Model.Entities.DBType;

import static com.siduron.java.iTravel.Model.DataSource.iContract.*;


/**
 * Created by musad on 20/01/2017.
 */

public class iTravelContentProvider extends ContentProvider {


    IBackEnd backend = BackendFactor.getIBackend(DBType.WEB_DB);


    private static final String TAG = "iTravel Provider";

    static final UriMatcher uriMatcher;//=new UriMatcher(UriMatcher.NO_MATCH);
    static final int USER_CODE = 1,
            BUSSINESS_CODE = 2,
            ACTIVITY_CODE = 3,
            ACTIVITY_ADAPTER_CODE = 4,
            BUSSINESS_ADAPTER_CODE = 5;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(iContract.ContentProvider.AUTHORITY, UserFields.TABLE_NAME, USER_CODE);
        uriMatcher.addURI(iContract.ContentProvider.AUTHORITY, UserFields.TABLE_NAME + "/*", USER_CODE);

        uriMatcher.addURI(iContract.ContentProvider.AUTHORITY, BussinessFields.TABLE_NAME, BUSSINESS_CODE);
        uriMatcher.addURI(iContract.ContentProvider.AUTHORITY, BussinessFields.TABLE_NAME + "/*", BUSSINESS_CODE);

        uriMatcher.addURI(iContract.ContentProvider.AUTHORITY, ActivityFields.TABLE_NAME, ACTIVITY_CODE);
        uriMatcher.addURI(iContract.ContentProvider.AUTHORITY, ActivityFields.TABLE_NAME + "/*", ACTIVITY_CODE);

        uriMatcher.addURI(iContract.ContentProvider.AUTHORITY, ActivityAdapterFields.TABLE_NAME, ACTIVITY_ADAPTER_CODE);
        uriMatcher.addURI(iContract.ContentProvider.AUTHORITY, ActivityAdapterFields.TABLE_NAME + "/*", ACTIVITY_ADAPTER_CODE);

        uriMatcher.addURI(iContract.ContentProvider.AUTHORITY, BusinessAdapterFields.TABLE_NAME, BUSSINESS_ADAPTER_CODE);
        uriMatcher.addURI(iContract.ContentProvider.AUTHORITY, BusinessAdapterFields.TABLE_NAME + "/*", BUSSINESS_ADAPTER_CODE);
    }

    @Override
    public boolean onCreate() {
        Log.i("Content Provider", "Created!");

        return false;
    }

    /**
     * Insert implementation
     * Returns the cursor of the DB or the list db
     * @param uri  the uri of the type of requested objects - User, Business or others
     * @param strings the columns requested
     * @param s Requested column query term
     * @param strings1 the columns requested
     * @param s1 Requested column query term
     * @return the cursor instance
     */
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {

        Cursor cursor = null;

        switch (uriMatcher.match(uri)) {
            case USER_CODE:
                cursor = backend.getUsers();
                //cursor.setNotificationUri(getContext().getContentResolver(),uri);
                break;
            case BUSSINESS_CODE:
                cursor = backend.getBusiness();
                break;
            case ACTIVITY_CODE:
                cursor = backend.getActivities();
                break;
            case ACTIVITY_ADAPTER_CODE:
                cursor = backend.getAdapters();
                break;
            case BUSSINESS_ADAPTER_CODE:
                cursor = backend.getAdapters();
                break;
            default:
                throw new IllegalArgumentException(TAG+" Unknown Uri received!");
        }

        Log.i(TAG,"Cursor list columns count: "+String.valueOf(cursor.getColumnCount())+"\nCursor rows count: "+cursor.getCount());
        //null if it was problem with the query or the result cursor if it
        //was correct query
        return cursor;
    }

    @Override
    public String getType(Uri uri)
    {

        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        Uri result = null;
        int resultId = -1;

        switch (uriMatcher.match(uri)) {
            case USER_CODE:
                resultId = backend.addUser(contentValues);
                break;
            case BUSSINESS_CODE:
                resultId = backend.addBussines(contentValues);
                break;
            case ACTIVITY_CODE:
                resultId = backend.addActivity(contentValues);
                break;
            case ACTIVITY_ADAPTER_CODE:
                resultId = backend.addAdapter(contentValues);
            case BUSSINESS_ADAPTER_CODE:
                resultId = backend.addBusinessAdapter(contentValues);
                break;
            default:
                break;

        }

        Log.i(TAG, uri.getLastPathSegment() + "Added - his Id is:" + resultId);

        result = Uri.parse(uri + "/" + resultId);

        return result;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case USER_CODE:
                break;
            case BUSSINESS_CODE:
                break;
            case ACTIVITY_CODE:
                break;
            case ACTIVITY_ADAPTER_CODE:
                break;
            default:
                break;

        }

        return count;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        switch (uriMatcher.match(uri)) {
            case USER_CODE:
                break;
            case BUSSINESS_CODE:
                break;
            case ACTIVITY_CODE:
                break;
            case ACTIVITY_ADAPTER_CODE:
                break;
            default:
                break;

        }
        return 0;
    }


}
