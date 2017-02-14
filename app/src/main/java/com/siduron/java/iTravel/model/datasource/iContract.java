package com.siduron.java.iTravel.Model.DataSource;

/**
 * Created by Moshe Nahari on 20/01/2017.
 */

import android.net.Uri;

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
    public static class UserFields {
        public static final String ID = "_id";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String GENDER = "gender";
        public static final String BIRTHDAY = "birthday";
        public static final String PHONE = "phone";
        public static final String ADDRESS = "address";

        public static final String USER_MAIN_KEY="com.siduron.java.iTravel.USER_MAIN_KEY";

        public static final String TABLE_NAME = "Users_table";
        public static final String[] COLUMNS = new String[]{ID, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, GENDER, BIRTHDAY, PHONE, ADDRESS};
        public static final Uri USER_URI = Uri.parse(ContentProvider.AUTHORITY_URI + "/" + TABLE_NAME);
    }

    /**
     * Business DB fields and table names
     * @author Moshe Nahari & Haim Milikovski
     */
    public static class BussinessFields {
        public static final String ID = "_id";
        public static final String MANAGER_ID = "manager_id";
        public static final String NAME = "name";
        public static final String EMAIL = "email";
        public static final String PHONE = "phone";
        public static final String ADDRESS = "address";
        public static final String WEBSITE = "website";
        public static final String DESCRIPTION = "description";

        public static final String TABLE_NAME = "Bussiness_table";
        public static final String[] COLUMNS = new String[]{ID,MANAGER_ID, NAME, EMAIL, PHONE, ADDRESS, WEBSITE, DESCRIPTION};
        public static final Uri BUSSINESS_URI = Uri.parse(ContentProvider.AUTHORITY_URI + "/" + TABLE_NAME);
    }

    /**
     * Business DB fields and table names
     * @author Moshe Nahari & Haim Milikovski
     */
    public static class BusinessAdapterFields {
        public static final String ID = "_id";
        public static final String USER_ID = "user_id";
        public static final String BUSINESS_ID = "business_id";

        public static final String TABLE_NAME = "Bussiness_Adapter_table";
        public static final String[] COLUMNS = new String[]{ID,USER_ID, BUSINESS_ID};
        public static final Uri BUSSINESS_Adapter_URI = Uri.parse(ContentProvider.AUTHORITY_URI + "/" + TABLE_NAME);
    }

    /**
     * Activity DB fields and table names
     * @author Moshe Nahari & Haim Milikovski
     */
    public static class ActivityFields {
        public static final String ID = "_id";
        public static final String BUSSINESS_ID = "business_id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String START_DATE = "start_date";
        public static final String END_DATE = "end_date";
        public static final String CATEGORY = "category";
        public static final String LOCATION = "location";
        public static final String PRICE = "price";

        public static final String TABLE_NAME = "Activities_table";
        public static final String[] COLUMNS = new String[]{ID, BUSSINESS_ID, NAME, DESCRIPTION, START_DATE, END_DATE, CATEGORY, LOCATION, PRICE};
        public static final Uri ACTIVITY_URI = Uri.parse(ContentProvider.AUTHORITY_URI + "/" + TABLE_NAME);
    }

    /**
     * Activity adapter DB fields and table names
     * @author Moshe Nahari & Haim Milikovski
     */
    public static class ActivityAdapterFields {
        public static final String ID = "_id";
        public static final String ACTIVITY_ID = "activity_id";
        public static final String BUSSINESS_ID = "business_id";

        public static final String TABLE_NAME = "Activity_adapters_table";
        public static final String[] COLUMNS = new String[]{ID, ACTIVITY_ID, BUSSINESS_ID};
        public static final Uri ACTIVITY_ADAPTER_URI = Uri.parse(ContentProvider.AUTHORITY_URI + "/" + TABLE_NAME);
    }

    /**
     * Content provider settings
     * @author Moshe Nahari & Haim Milikovski
     */
    public static class ContentProvider
    {
        public static final String AUTHORITY ="com.siduron.java.iTravel.model.backend.iTravelContentProvider";
        public static final String NAME=".iTravelContentProvider";
        public static final Uri AUTHORITY_URI= Uri.parse("content://"+AUTHORITY);
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
        public static final String LOGOUT_STATUS_KEY=KEY_BASE+"_logoutStatus";
    }
}
