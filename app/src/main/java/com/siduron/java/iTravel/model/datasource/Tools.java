package com.siduron.java.iTravel.model.datasource;

/**
 * Created by musad on 20/01/2017.
 */


import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.Context;
import android.content.ContextWrapper;
import android.preference.PreferenceManager;

import com.siduron.java.iTravel.model.entities.Activity;
import com.siduron.java.iTravel.model.entities.ActivityAdapter;
import com.siduron.java.iTravel.model.entities.Bussiness;
import com.siduron.java.iTravel.model.entities.User;

import static android.content.Context.MODE_PRIVATE;
import static android.preference.PreferenceManager.*;
import static com.siduron.java.iTravel.model.datasource.iContract.iSharedPreference.SHARED_NAME;

/**
 *
 * more
 */
public class Tools
{

    public Tools()
    {
    }

    public ContentValues UserToContentValues(User user)
    {
        return null;
    }

    public ContentValues BussinessToContentValues(Bussiness bussiness)
    {
        return null;
    }

    public ContentValues ActivityToContentValues(Activity activity)
    {
        return null;
    }

    public ContentValues ActivityAdapterToContentValues(ActivityAdapter adapter)
    {
        return null;
    }

}
