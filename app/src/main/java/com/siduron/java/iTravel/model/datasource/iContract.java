package com.siduron.java.iTravel.model.datasource;

/**
 * Created by musad on 20/01/2017.
 */

import android.net.Uri;

import java.net.URI;

/**
 * This class defining the SQL tables's columns names and more
 * fields names as constant values
 *
 * @author Moshe Nahari & Haim Milikovski
 */
public class iContract
{
    /**
     * User DB fields and table names
     * @author Moshe Nahari & Haim Milikovski
     */
    public static class User {
        public static final String ID = "_id";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String GENDER = "gender";
        public static final String BIRTHDAY = "birthday";
        public static final String PHONE = "phone";
        public static final String ADDRESS = "address";

        public static final String TABLE_NAME="Users_table";
    }

    /**
     * Bussiness DB fields and table names
     * @author Moshe Nahari & Haim Milikovski
     */
    public static class Bussiness {
        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String PHONE = "phone";
        public static final String DESCRIPTION = "description";

        public static final String TABLE_NAME="Bussiness_table";
    }

    /**
     * Activity DB fields and table names
     * @author Moshe Nahari & Haim Milikovski
     */
    public static class Activity {
        public static final String ID = "_id";
        public static final String BUSSINESS_ID = "bussiness_id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String START_DATE = "start_date";
        public static final String END_DATE = "end_date";
        public static final String CATEGORY = "category";
        public static final String LOCATION = "location";
        public static final String PRICE = "price";

        public static final String TABLE_NAME="Activities_table";
    }

    /**
     * Activity adapter DB fields and table names
     * @author Moshe Nahari & Haim Milikovski
     */
    public static class ActivityAdapter {
        public static final String ID = "_id";
        public static final String ACTIVITY_ID = "activity_id";
        public static final String BUSSINESS_ID = "bussiness_id";

        public static final String TABLE_NAME="Activity_adapters_table";
    }

    /**
     * Content provider settings
     * @author Moshe Nahari & Haim Milikovski
     */
    public static class ContentProvider
    {
        public static final String NAME="com.siduron.java.iTravel.model.backend.iTravelContentProvider";
        public static final String AUTHORITY=".iTravelContentProvider";
        public static final Uri URI= Uri.parse("content://"+AUTHORITY);
    }



    /**
     * Web site - addresses details (settings)
     * @author Moshe Nahari & Haim Milikovski
     */
    public static class WebInterfaces
    {
        public static final String DOMAIN_AUTHORITY="mnahari.vlab.jct.ac.il";
        public static final String URL= "http://"+DOMAIN_AUTHORITY;
    }

    public  static  class iSharedPreference
    {
        public final static String SHARED_NAME="iSharedSettings";
        public final static String LAST_USER_NAME="LastUserName";
        public final static String LAST_USER_PASSWORD="LastUserPassword";
        public final static String SAVE_LAST_USER="SaveLastUser";
    }

    public static class LoginUserKeys
    {
        private static final String KEY_BASE="com.siduron.java.iTravel";
        public static final String LOGIN_NAME_KEY=KEY_BASE+"_login_name";
        public static final String LOGIN_PASSWORD_KEY=KEY_BASE+"_login_password";
    }
}
