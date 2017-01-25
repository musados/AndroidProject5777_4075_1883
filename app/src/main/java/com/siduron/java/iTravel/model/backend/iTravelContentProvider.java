package com.siduron.java.iTravel.model.backend;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import com.siduron.java.iTravel.model.datasource.iContract;
import com.siduron.java.iTravel.model.datasource.iContract.UserFields;
import com.siduron.java.iTravel.model.datasource.iContract.BussinessFields;
import com.siduron.java.iTravel.model.datasource.iContract.ActivityFields;
import com.siduron.java.iTravel.model.datasource.iContract.ActivityAdapterFields;


import android.util.Log;

import com.siduron.java.iTravel.model.datasource.iContract;
import com.siduron.java.iTravel.model.entities.DBType;

import com.siduron.java.iTravel.model.datasource.EntitiesUriCodes;


/**
 * Created by musad on 20/01/2017.
 */

public class iTravelContentProvider extends ContentProvider {


    IBackEnd backend = BackendFactor.getIBackend(DBType.LIST);

    private static final String TAG = "iTravel Provider";

    static final UriMatcher uriMatcher;//=new UriMatcher(UriMatcher.NO_MATCH);
    static final int USER_CODE = 1,
            BUSSINESS_CODE = 2,
            ACTIVITY_CODE = 3,
            ACTIVITY_ADAPTER_CODE = 4;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(iContract.ContentProvider.AUTHORITY, iContract.UserFields.TABLE_NAME, USER_CODE);
        uriMatcher.addURI(iContract.ContentProvider.AUTHORITY, UserFields.TABLE_NAME + "/*", USER_CODE);
        uriMatcher.addURI(iContract.ContentProvider.AUTHORITY, BussinessFields.TABLE_NAME, BUSSINESS_CODE);
        uriMatcher.addURI(iContract.ContentProvider.AUTHORITY, BussinessFields.TABLE_NAME + "/*", BUSSINESS_CODE);
        uriMatcher.addURI(iContract.ContentProvider.AUTHORITY, ActivityFields.TABLE_NAME, ACTIVITY_CODE);
        uriMatcher.addURI(iContract.ContentProvider.AUTHORITY, ActivityFields.TABLE_NAME + "/*", ACTIVITY_CODE);
        uriMatcher.addURI(iContract.ContentProvider.AUTHORITY, ActivityAdapterFields.TABLE_NAME, ACTIVITY_ADAPTER_CODE);
        uriMatcher.addURI(iContract.ContentProvider.AUTHORITY, ActivityAdapterFields.TABLE_NAME + "/*", ACTIVITY_ADAPTER_CODE);
    }

    @Override
    public boolean onCreate() {
        Log.i("Content Provider", "Created!");

        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {

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
        return null;
    }

    @Override
    public String getType(Uri uri) {
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
