package com.siduron.java.iTravel.Model.DataSource;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.util.Log;

import com.siduron.java.iTravel.Model.Backend.IBackEnd;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.siduron.java.iTravel.Model.DataSource.iContract.BussinessFields.EMAIL;
import static com.siduron.java.iTravel.Model.DataSource.iContract.BussinessFields.MANAGER_ID;
import static com.siduron.java.iTravel.Model.DataSource.iContract.BussinessFields.NAME;
import static com.siduron.java.iTravel.Model.DataSource.iContract.UserFields.ADDRESS;
import static com.siduron.java.iTravel.Model.DataSource.iContract.UserFields.BIRTHDAY;
import static com.siduron.java.iTravel.Model.DataSource.iContract.UserFields.FIRST_NAME;
import static com.siduron.java.iTravel.Model.DataSource.iContract.UserFields.GENDER;
import static com.siduron.java.iTravel.Model.DataSource.iContract.UserFields.ID;
import static com.siduron.java.iTravel.Model.DataSource.iContract.UserFields.LAST_NAME;
import static com.siduron.java.iTravel.Model.DataSource.iContract.UserFields.PASSWORD;
import static com.siduron.java.iTravel.Model.DataSource.iContract.UserFields.PHONE;
import static com.siduron.java.iTravel.Model.DataSource.iContract.UserFields.USERNAME;

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


    private static final String TAG = "Remote DB";

    /**
     * Singleton provider - get method
     *
     * @return "Instace" of the class  - DBS_BackEnd class
     */
    public static DBS_BackEnd getInstance() {
        return Instance;
    }

    /**
     * Default CTOR (private)
     * Because the class have a Singleton instance
     */
    private DBS_BackEnd() {
    }

    @Override
    public int addUser(ContentValues user) {
        int result = 0;
        try {
            Map<String, Object> params = new LinkedHashMap<>();

            params.put(USERNAME, user.getAsString(USERNAME));
            params.put(PASSWORD, user.getAsString(PASSWORD));
            params.put(FIRST_NAME, user.getAsString(FIRST_NAME));
            params.put(LAST_NAME, user.getAsString(LAST_NAME));
            params.put(GENDER, user.getAsString(GENDER));
            params.put(BIRTHDAY, user.getAsString(BIRTHDAY));
            params.put(PHONE, user.getAsInteger(PHONE));
            params.put(ADDRESS, user.getAsString(ADDRESS));

            String results = "";
            JSONArray array = new JSONObject(results = PHPTools.POST(iContract.WebInterfaces.URL + "/add_user.php", params)).getJSONArray("users");

            JSONObject resultJson = array.getJSONObject(0);

            result = resultJson.getInt("_id");

            //String results = PHPTools.POST(iContract.WebInterfaces.URL + "/add_user.php", params);
            if (results.equals("")) {
                throw new Exception("An error occurred on the server's side");
            }
            if (results.length() >= 5 && results.substring(0, 5).equalsIgnoreCase("error")) {
                throw new Exception(results.substring(5));
            }

            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public int addBussines(ContentValues business) {
        int result = 0;
        try {
            Map<String, Object> params = new LinkedHashMap<>();

            params.put(iContract.BussinessFields.MANAGER_ID, business.getAsString(iContract.BussinessFields.MANAGER_ID));
            params.put(iContract.BussinessFields.NAME, business.getAsString(iContract.BussinessFields.NAME));
            params.put(iContract.BussinessFields.EMAIL, business.getAsString(iContract.BussinessFields.EMAIL));
            params.put(iContract.BussinessFields.PHONE, business.getAsString(iContract.BussinessFields.PHONE));
            params.put(iContract.BussinessFields.ADDRESS, business.getAsString(iContract.BussinessFields.ADDRESS));
            params.put(iContract.BussinessFields.WEBSITE, business.getAsString(iContract.BussinessFields.WEBSITE));
            params.put(iContract.BussinessFields.DESCRIPTION, business.getAsInteger(iContract.BussinessFields.DESCRIPTION));

            String results = "";
            JSONArray array = new JSONObject(results = PHPTools.POST(iContract.WebInterfaces.URL +
                    "/add_business.php", params)).getJSONArray("businesses");

            JSONObject resultJson = array.getJSONObject(0);

            result = resultJson.getInt("_id");

            //String results = PHPTools.POST(iContract.WebInterfaces.URL + "/add_user.php", params);
            if (results.equals("")) {
                throw new Exception("An error occurred on the server's side");
            }
            if (results.length() >= 5 && results.substring(0, 5).equalsIgnoreCase("error")) {
                throw new Exception(results.substring(5));
            }

            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public int addActivity(ContentValues activity) {

        int result = 0;
        try {
            Map<String, Object> params = new LinkedHashMap<>();

            params.put(iContract.ActivityFields.BUSSINESS_ID, activity.getAsString(iContract.ActivityFields.BUSSINESS_ID));
            params.put(iContract.ActivityFields.NAME, activity.getAsString(iContract.ActivityFields.NAME));
            params.put(iContract.ActivityFields.DESCRIPTION, activity.getAsString(iContract.ActivityFields.DESCRIPTION));
            params.put(iContract.ActivityFields.START_DATE, activity.getAsString(iContract.ActivityFields.START_DATE));
            params.put(iContract.ActivityFields.END_DATE, activity.getAsString(iContract.ActivityFields.END_DATE));
            params.put(iContract.ActivityFields.CATEGORY, activity.getAsString(iContract.ActivityFields.CATEGORY));
            params.put(iContract.ActivityFields.LOCATION, activity.getAsString(iContract.ActivityFields.LOCATION));
            params.put(iContract.ActivityFields.PRICE, activity.getAsString(iContract.ActivityFields.PRICE));

            String results = "";
            JSONArray array = new JSONObject(results = PHPTools.POST(iContract.WebInterfaces.URL +
                    "/add_activity.php", params)).getJSONArray("activities");

            JSONObject resultJson = array.getJSONObject(0);

            result = resultJson.getInt("_id");

            //String results = PHPTools.POST(iContract.WebInterfaces.URL + "/add_user.php", params);
            if (results.equals("")) {
                throw new Exception("An error occurred on the server's side");
            }
            if (results.length() >= 5 && results.substring(0, 5).equalsIgnoreCase("error")) {
                throw new Exception(results.substring(5));
            }

            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public int addAdapter(ContentValues activityAdapter) {
        int result = 0;
        try {
            Map<String, Object> params = new LinkedHashMap<>();

            params.put(iContract.ActivityAdapterFields.ACTIVITY_ID, activityAdapter.getAsString(iContract.ActivityAdapterFields.ACTIVITY_ID));
            params.put(iContract.ActivityAdapterFields.BUSSINESS_ID, activityAdapter.getAsString(iContract.ActivityAdapterFields.BUSSINESS_ID));

            String results = "";
            JSONArray array = new JSONObject(results = PHPTools.POST(iContract.WebInterfaces.URL +
                    "/add_activity_adapter.php", params)).getJSONArray("activity_adapter");

            JSONObject resultJson = array.getJSONObject(0);

            result = resultJson.getInt("_id");

            //String results = PHPTools.POST(iContract.WebInterfaces.URL + "/add_user.php", params);
            if (results.equals("")) {
                throw new Exception("An error occurred on the server's side");
            }
            if (results.length() >= 5 && results.substring(0, 5).equalsIgnoreCase("error")) {
                throw new Exception(results.substring(5));
            }

            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public int addBusinessAdapter(ContentValues businessAdapter) {
        int result = 0;
        try {
            Map<String, Object> params = new LinkedHashMap<>();

            params.put(iContract.BusinessAdapterFields.USER_ID, businessAdapter.getAsString(iContract.BusinessAdapterFields.USER_ID));
            params.put(iContract.BusinessAdapterFields.BUSINESS_ID, businessAdapter.getAsString(iContract.BusinessAdapterFields.BUSINESS_ID));

            String results = "";
            JSONArray array = new JSONObject(results = PHPTools.POST(iContract.WebInterfaces.URL +
                    "/add_business_adapter.php", params)).getJSONArray("business_adapter");

            JSONObject resultJson = array.getJSONObject(0);

            result = resultJson.getInt("_id");

            //String results = PHPTools.POST(iContract.WebInterfaces.URL + "/add_user.php", params);
            if (results.equals("")) {
                throw new Exception("An error occurred on the server's side");
            }
            if (results.length() >= 5 && results.substring(0, 5).equalsIgnoreCase("error")) {
                throw new Exception(results.substring(5));
            }

            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
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
    public int removeBusinessAdapter(int adapterID) {
        return 0;
    }



    //Updates Area

    @Override
    public boolean updateUser(int userID, ContentValues values) {
        boolean result = false;
        try {
            Map<String, Object> params = new LinkedHashMap<>();

            params.put(iContract.UserFields.ID, String.valueOf(userID));
            params.put(iContract.UserFields.USERNAME, values.getAsString(iContract.UserFields.USERNAME));
            params.put(iContract.UserFields.PASSWORD, values.getAsString(iContract.UserFields.PASSWORD));
            params.put(iContract.UserFields.FIRST_NAME, values.getAsString(iContract.UserFields.FIRST_NAME));
            params.put(iContract.UserFields.LAST_NAME, values.getAsString(iContract.UserFields.LAST_NAME));
            params.put(iContract.UserFields.GENDER, values.getAsString(iContract.UserFields.GENDER));
            params.put(iContract.UserFields.BIRTHDAY, values.getAsString(iContract.UserFields.BIRTHDAY));
            params.put(iContract.UserFields.PHONE, values.getAsString(iContract.UserFields.PHONE));
            params.put(iContract.UserFields.ADDRESS, values.getAsString(iContract.UserFields.ADDRESS));

            String results = "";
            results = PHPTools.POST(iContract.WebInterfaces.URL +
                    "/update_user.php", params);

            result = results.trim().equals("1");

            //String results = PHPTools.POST(iContract.WebInterfaces.URL + "/add_user.php", params);
            if (results.trim().equals("")) {
                throw new Exception("An error occurred on the server's side");
            }
            if (results.trim().equals("-1")) {
                throw new Exception("Error: Incorrect given ID");
            }
            if (results.trim().equals("0")) {
                throw new Exception("Error: Incorrect given parameters that not exist");
            }
            if (results.trim().length() >= 5 && results.substring(0, 5).equalsIgnoreCase("error")) {
                throw new Exception(results.substring(5));
            }

            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }


    /**
     * Update business details by given business's ID
     * @param bussinessID int of the business's ID
     * @param values business as ContentValues
     * @return if the task was successful
     */
    @Override
    public boolean updateBusiness(int bussinessID, ContentValues values) {
        boolean result = false;
        try {
            Map<String, Object> params = new LinkedHashMap<>();

            params.put(iContract.BussinessFields.ID, String.valueOf(bussinessID));
            params.put(iContract.BussinessFields.MANAGER_ID, values.getAsString(iContract.BussinessFields.MANAGER_ID));
            params.put(iContract.BussinessFields.NAME, values.getAsString(iContract.BussinessFields.NAME));
            params.put(iContract.BussinessFields.EMAIL, values.getAsString(iContract.BussinessFields.EMAIL));
            params.put(iContract.BussinessFields.PHONE, values.getAsString(iContract.BussinessFields.PHONE));
            params.put(iContract.BussinessFields.ADDRESS, values.getAsString(iContract.BussinessFields.ADDRESS));
            params.put(iContract.BussinessFields.WEBSITE, values.getAsString(iContract.BussinessFields.WEBSITE));
            params.put(iContract.BussinessFields.DESCRIPTION, values.getAsString(iContract.BussinessFields.DESCRIPTION));

            String results = "";
            results = PHPTools.POST(iContract.WebInterfaces.URL +
                    "/update_business.php", params);

            result = results.trim().equals("1");

            //String results = PHPTools.POST(iContract.WebInterfaces.URL + "/add_user.php", params);
            if (results.trim().equals("")) {
                throw new Exception("An error occurred on the server's side");
            }
            if (results.trim().equals("-1")) {
                throw new Exception("Error: Incorrect given ID");
            }
            if (results.trim().equals("0")) {
                throw new Exception("Error: Incorrect given parameters that not exist");
            }
            if (results.trim().length() >= 5 && results.substring(0, 5).equalsIgnoreCase("error")) {
                throw new Exception(results.substring(5));
            }

            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }


    @Override
    public boolean updateActivity(int activityID, ContentValues values) {
        boolean result = false;
        try {
            Map<String, Object> params = new LinkedHashMap<>();

            params.put(iContract.ActivityFields.ID, String.valueOf(activityID));
            params.put(iContract.ActivityFields.NAME, values.getAsString(iContract.ActivityFields.NAME));
            params.put(iContract.ActivityFields.BUSSINESS_ID, values.getAsString(iContract.ActivityFields.BUSSINESS_ID));
            params.put(iContract.ActivityFields.DESCRIPTION, values.getAsString(iContract.ActivityFields.DESCRIPTION));
            params.put(iContract.ActivityFields.START_DATE, values.getAsString(iContract.ActivityFields.START_DATE));
            params.put(iContract.ActivityFields.END_DATE, values.getAsString(iContract.ActivityFields.END_DATE));
            params.put(iContract.ActivityFields.CATEGORY, values.getAsString(iContract.ActivityFields.CATEGORY));
            params.put(iContract.ActivityFields.LOCATION, values.getAsString(iContract.ActivityFields.LOCATION));
            params.put(iContract.ActivityFields.PRICE, values.getAsString(iContract.ActivityFields.PRICE));

            String results = "";
            results = PHPTools.POST(iContract.WebInterfaces.URL +
                    "/update_activity.php", params);

            result = results.trim().equals("1");

            //String results = PHPTools.POST(iContract.WebInterfaces.URL + "/add_user.php", params);
            if (results.trim().equals("")) {
                throw new Exception("An error occurred on the server's side");
            }
            if (results.trim().equals("-1")) {
                throw new Exception("Error: Incorrect given ID");
            }
            if (results.trim().equals("0")) {
                throw new Exception("Error: Incorrect given parameters that not exist");
            }
            if (results.trim().length() >= 5 && results.substring(0, 5).equalsIgnoreCase("error")) {
                throw new Exception(results.substring(5));
            }

            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public boolean updateActivityAdapter(int adapterID, ContentValues values) {
        boolean result = false;
        try {
            Map<String, Object> params = new LinkedHashMap<>();

            params.put(iContract.ActivityAdapterFields.ID, String.valueOf(adapterID));
            params.put(iContract.ActivityAdapterFields.ACTIVITY_ID, values.getAsString(iContract.ActivityAdapterFields.ACTIVITY_ID));
            params.put(iContract.ActivityAdapterFields.BUSSINESS_ID, values.getAsString(iContract.ActivityAdapterFields.BUSSINESS_ID));

            String results = "";
            results = PHPTools.POST(iContract.WebInterfaces.URL +
                    "/update_activity_adapter.php", params);

            result = results.trim().equals("1");

            //String results = PHPTools.POST(iContract.WebInterfaces.URL + "/add_user.php", params);
            if (results.equals("")) {
                throw new Exception("An error occurred on the server's side");
            }
            if (results.length() >= 5 && results.substring(0, 5).equalsIgnoreCase("error")) {
                throw new Exception(results.substring(5));
            }

            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public boolean updateBusinessAdapter(int adapterID, ContentValues values) {
        boolean result = false;
        try {
            Map<String, Object> params = new LinkedHashMap<>();

            params.put(iContract.BusinessAdapterFields.ID, String.valueOf(adapterID));
            params.put(iContract.BusinessAdapterFields.USER_ID, values.getAsString(iContract.BusinessAdapterFields.USER_ID));
            params.put(iContract.BusinessAdapterFields.BUSINESS_ID, values.getAsString(iContract.BusinessAdapterFields.BUSINESS_ID));

            String results = "";
            results = PHPTools.POST(iContract.WebInterfaces.URL +
                    "/update_business_adapter.php", params);

            result = results.trim().equals("1");

            //String results = PHPTools.POST(iContract.WebInterfaces.URL + "/add_user.php", params);
            if (results.equals("")) {
                throw new Exception("An error occurred on the server's side");
            }
            if (results.length() >= 5 && results.substring(0, 5).equalsIgnoreCase("error")) {
                throw new Exception(results.substring(5));
            }

            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }



    //Get methods area

    @Override
    public Cursor getUsers() {
        MatrixCursor usersCursor = new MatrixCursor(iContract.UserFields.COLUMNS);

        try {
            JSONArray array = new JSONObject(PHPTools.GET(iContract.WebInterfaces.URL + "/get_users.php")).getJSONArray("users");
            for (int i = 0; i < array.length(); i++) {
                JSONObject user = array.getJSONObject(i);
                usersCursor.addRow(new Object[]{
                        user.get(ID),
                        user.get(USERNAME),
                        user.get(PASSWORD),
                        user.get(FIRST_NAME),
                        user.get(LAST_NAME),
                        user.get(GENDER),
                        user.get(BIRTHDAY),
                        user.get(PHONE),
                        user.get(ADDRESS)
                });
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return usersCursor;
    }



    @Override
    public Cursor getBusiness() {

        MatrixCursor businessesCursor = new MatrixCursor(iContract.BussinessFields.COLUMNS);

        try {
            JSONArray array = new JSONObject(PHPTools.GET(iContract.WebInterfaces.URL + "/get_businesses.php")).getJSONArray("businesses");
            for (int i = 0; i < array.length(); i++) {
                JSONObject business = array.getJSONObject(i);
                Log.i("PHP tools business",business.toString());
                businessesCursor.addRow(new Object[]{
                        business.get(iContract.BussinessFields.ID),
                        business.get(iContract.BussinessFields.MANAGER_ID),
                        business.get(iContract.BussinessFields.NAME),
                        business.get(iContract.BussinessFields.EMAIL),
                        business.get(iContract.BussinessFields.PHONE),
                        business.get(iContract.BussinessFields.ADDRESS),
                        business.get(iContract.BussinessFields.WEBSITE),
                        business.get(iContract.BussinessFields.DESCRIPTION)
                });
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return businessesCursor;
    }

    @Override
    public Cursor getActivities() {

        MatrixCursor activitiesCursor = new MatrixCursor(iContract.ActivityFields.COLUMNS);

        try {
            JSONArray array = new JSONObject(PHPTools.GET(iContract.WebInterfaces.URL + "/get_activities.php")).getJSONArray("activities");
            for (int i = 0; i < array.length(); i++) {
                JSONObject activity = array.getJSONObject(i);
                Log.w("Get remote activities",activity.toString());
                activitiesCursor.addRow(new Object[]{
                        activity.get(iContract.ActivityFields.ID),
                        activity.get(iContract.ActivityFields.BUSSINESS_ID),
                        activity.get(iContract.ActivityFields.NAME),
                        activity.get(iContract.ActivityFields.DESCRIPTION),
                        activity.get(iContract.ActivityFields.START_DATE),
                        activity.get(iContract.ActivityFields.END_DATE),
                        activity.get(iContract.ActivityFields.CATEGORY),
                        activity.get(iContract.ActivityFields.LOCATION),
                        activity.get(iContract.ActivityFields.PRICE)
                });
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return activitiesCursor;
    }

    @Override
    public Cursor getAdapters() {
        MatrixCursor adaptersCursor = new MatrixCursor(iContract.ActivityAdapterFields.COLUMNS);

        try {
            JSONArray array = new JSONObject(PHPTools.GET(iContract.WebInterfaces.URL + "/get_activity_adapters.php")).getJSONArray("activity_adapters");
            for (int i = 0; i < array.length(); i++) {
                JSONObject adapter = array.getJSONObject(i);
                Log.w("Get remote adapters",adapter.toString());
                adaptersCursor.addRow(new Object[]{
                        adapter.get(iContract.ActivityAdapterFields.ID),
                        adapter.get(iContract.ActivityAdapterFields.ACTIVITY_ID),
                        adapter.get(iContract.ActivityAdapterFields.BUSSINESS_ID)
                });
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return adaptersCursor;
    }

    @Override
    public Cursor getBusinessAdapters() {
        MatrixCursor businessAdaptersCursor = new MatrixCursor(iContract.BusinessAdapterFields.COLUMNS);

        try {
            JSONArray array = new JSONObject(PHPTools.GET(iContract.WebInterfaces.URL + "/get_business_adapters.php")).getJSONArray("business_adapters");
            for (int i = 0; i < array.length(); i++) {
                JSONObject bAdapter = array.getJSONObject(i);
                Log.w("Get remote B adapters",bAdapter.toString());
                businessAdaptersCursor.addRow(new Object[]{
                        bAdapter.get(iContract.BusinessAdapterFields.ID),
                        bAdapter.get(iContract.BusinessAdapterFields.USER_ID),
                        bAdapter.get(iContract.BusinessAdapterFields.BUSINESS_ID)
                });
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return businessAdaptersCursor;
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
