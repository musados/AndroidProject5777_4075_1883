package com.siduron.java.iTravel.Controller;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.siduron.java.androidproject5777_4075_4075.R;
import com.siduron.java.iTravel.Model.Backend.iTravelContentProvider;
import com.siduron.java.iTravel.Model.DataSource.Tools;
import com.siduron.java.iTravel.Model.DataSource.iContract;
import com.siduron.java.iTravel.Model.Entities.Activity;
import com.siduron.java.iTravel.Model.Entities.Business;
import com.siduron.java.iTravel.Model.Entities.Category;
import com.siduron.java.iTravel.Model.Entities.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.zip.Inflater;

import static com.siduron.java.iTravel.Model.DataSource.iContract.UserFields.USER_MAIN_KEY;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "User home fragment";
    private static final String ARG_PARAM2 = "param2";

    iTravelContentProvider provider=new iTravelContentProvider();
    User loggedUser;
    LoadingTask loadTask=null;
    ListView activitiesListView;
    ListView businessesListView;
    ArrayAdapter<Business> busAdapter;
    ArrayAdapter<Activity> actAdapter;

    //TextView activityLabel;
    //TextView businessLabel;

    ImageView emptyFrameIcon;

    ArrayList<Activity> activitiesList=new ArrayList<Activity>();
    ArrayList<Business> businessesList=new ArrayList<Business>();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(TAG, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(TAG);
            mParam2 = getArguments().getString(ARG_PARAM2);


            setFlowDirection();
        }

    }

    @Override
    public void onStart(){
        super.onStart();
        getActivity().setTitle(getResources().getString(R.string.user_home_lists));

    }



    @Override
    public void onResume() {
        super.onResume();
        loggedUser=(User)getActivity().getIntent().getSerializableExtra(USER_MAIN_KEY);
        if(loggedUser!=null) {
            Log.w(TAG,"logged user id:"+loggedUser.getId());
            setTabs();
            setLists();
            reloadUseritems();
        }

    }


    private void setTabs() {
        TabHost host = (TabHost) getActivity().findViewById(R.id.HomeFragment);

        host.setup();
        if (host.getTabWidget().getTabCount() == 0) {
            TabHost.TabSpec spec = host.newTabSpec("Tab1");

            spec.setContent(R.id.BusinessesListView);
            spec.setIndicator(getResources().getString(R.string.businesses_list_label), getResources().getDrawable(R.drawable.bussiness));

            host.addTab(spec);

            spec = host.newTabSpec("Tab2");
            spec.setContent(R.id.ActivitiesListView);
            spec.setIndicator(getResources().getString(R.string.activities_list_label), getResources().getDrawable(R.drawable.add_activity));

            host.addTab(spec);
        }
    }

    private void setFlowDirection() {
        LinearLayout mainContainer = (LinearLayout) getActivity().findViewById(R.id.mainContainer);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (Locale.getDefault().getDisplayLanguage().contains("עברית"))
                mainContainer.setLayoutDirection(RelativeLayout.LAYOUT_DIRECTION_RTL);
            else
                mainContainer.setLayoutDirection(RelativeLayout.LAYOUT_DIRECTION_LTR);
        }

    }

    private void setLists() {
        activitiesListView=(ListView)getView().findViewById(R.id.ActivitiesListView);
        businessesListView=(ListView)getView().findViewById(R.id.BusinessesListView);
        //activityLabel=(TextView)getView().findViewById(R.id.ActivitiesLabel);
        //businessLabel=(TextView)getView().findViewById(R.id.BusinessesLabel);

        emptyFrameIcon =(ImageView)getView().findViewById(R.id.EmptyFrameButton);

        busAdapter=new ArrayAdapter<Business>(getActivity(), R.layout.business_list_item, businessesList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null) {
                    convertView = View.inflate(getActivity(),
                            R.layout.business_list_item,
                            null);
                }


                final ImageView itemLogoView = (ImageView) convertView.findViewById(R.id.BusinessLogo);
                final TextView itemTitleView = (TextView) convertView.findViewById(R.id.BusinessName);
                final TextView itemIdView = (TextView) convertView.findViewById(R.id.BusinessID);
                final TextView itemDescriptionView = (TextView) convertView.findViewById(R.id.BusinessDescription);
                final TextView itemPhoneView = (TextView) convertView.findViewById(R.id.BusinessPhone);
                final TextView itemWebsiteView = (TextView) convertView.findViewById(R.id.BusinessWebsite);
                final TextView itemEmailView = (TextView) convertView.findViewById(R.id.BusinessEmail);
                final TextView itemAddressView = (TextView) convertView.findViewById(R.id.BusinessAddress);


                //Set the values
                itemLogoView.setImageResource(R.drawable.bussiness);
                itemTitleView.setText(businessesList.get(position).getName());
                itemIdView.setText("ID " + businessesList.get(position).getId());
                itemDescriptionView.setText(businessesList.get(position).getDescription());
                itemPhoneView.setText(businessesList.get(position).getPhone());
                itemWebsiteView.setText(businessesList.get(position).getWebsite());
                itemEmailView.setText(businessesList.get(position).getEmail());
                itemAddressView.setText(businessesList.get(position).getAddress());

                final UserPanel panel=new UserPanel();



                return convertView;
            }

        };

        actAdapter=new ArrayAdapter<Activity>(getActivity(), R.layout.activities_list_item, activitiesList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null) {
                    convertView = View.inflate(getActivity(),
                            R.layout.activities_list_item,
                            null);
                }


                ImageView itemLogoView = (ImageView) convertView.findViewById(R.id.ActivityLogo);
                TextView itemTitleView = (TextView) convertView.findViewById(R.id.Name);
                TextView itemIdView = (TextView) convertView.findViewById(R.id.ActivityID);
                TextView itemDescriptionView = (TextView) convertView.findViewById(R.id.Description);
                TextView itemBusinessIdView = (TextView) convertView.findViewById(R.id.Activity_BusinessID);
                TextView itemLocationView = (TextView) convertView.findViewById(R.id.Location);
                TextView itemStartView = (TextView) convertView.findViewById(R.id.StartDate);
                TextView itemEndView = (TextView) convertView.findViewById(R.id.EndDate);
                TextView itemCategoryView = (TextView) convertView.findViewById(R.id.Category);
                TextView itemPriceView = (TextView) convertView.findViewById(R.id.Price);


                String[] cat= getResources().getStringArray(R.array.category_items_array);
                String selectedCategory=activitiesList.get(position).getActivityCategory().toString();

                for(String item:cat)
                {
                    if(item.equals(activitiesList.get(position).getActivityCategory().toString()))
                        selectedCategory=item;
                }
                //Set the values
                itemLogoView.setImageResource(R.drawable.bussiness);
                itemTitleView.setText(activitiesList.get(position).getActivityName());
                itemIdView.setText(String.valueOf(activitiesList.get(position).getId()));
                itemBusinessIdView.setText(String.valueOf(activitiesList.get(position).getBussinessID()));
                itemDescriptionView.setText(activitiesList.get(position).getActivityDescription());
                itemLocationView.setText(activitiesList.get(position).getActivitylocation());
                itemStartView.setText(Tools.dateToString(activitiesList.get(position).getStartDay()));
                itemEndView.setText(Tools.dateToString(activitiesList.get(position).getEndDate()));
                itemCategoryView.setText(selectedCategory);
                itemPriceView.setText(String.valueOf(activitiesList.get(position).getActivityPrice()));

                //set clickable views
                itemLocationView.setClickable(true);

                return convertView;
            }

        };

        actAdapter.sort(new Comparator<Activity>() {
            @Override
            public int compare(Activity activity, Activity t1) {
                if(activity.getId()==t1.getId())
                    return 0;
                if(activity.getId()<t1.getId())
                    return -1;
                return 1;
            }
        });

        businessesListView.setAdapter(busAdapter);
        activitiesListView.setAdapter(actAdapter);
    }


    private void reloadUseritems() {

        loadTask=new LoadingTask(TaskProcessType.BUSINESSES_LOADING,String.valueOf(loggedUser.getId()));
        loadTask.execute(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private enum TaskProcessType{
        ACTIVITIES_LOADING,BUSINESSES_LOADING;
    }
    /**
     * Add activity task
     *
     * @author Moshe Nahari & Haim Milikovsli
     */
    private class LoadingTask extends AsyncTask<Boolean,Void,Boolean>
    {
        ProgressDialog dialog ;
        Activity activity;
        int result=-1;
        TaskProcessType operationId;
        String businessID="-1";
        String userID="-1";

        ArrayList<Business> bList=new ArrayList<Business>();
        ArrayList<Activity> aList=new ArrayList<Activity>();

        /**
         * Task CTOR

         */
        public LoadingTask(TaskProcessType type,String id) {
            operationId = type;
            if(type==TaskProcessType.BUSINESSES_LOADING) {
                businessID = "-1";
                userID=id;
            }
            else
            {
                businessID=id;
                userID="-1";
            }
        }



        @Override
        protected void onPreExecute() {
            Log.i(TAG, "Starting pre progress");
            dialog=  ProgressDialog.show(getActivity(),
                    getResources().getString(R.string.loading_info_title),
                    getResources().getString(R.string.proccess_running_wait));
            Log.i(TAG, "Progress started");
        }



        @Override
        protected Boolean doInBackground(Boolean... booleen) {
            try {
                // Simulate network access.
                Thread.sleep(500);
                switch (operationId) {
                    case ACTIVITIES_LOADING:
                        try {
                            //ContentValues content = Tools.ActivityToContentValues(activity);
                            Cursor cursor = provider.query(iContract.ActivityFields.ACTIVITY_URI, null, null, null, null);
                            Log.w(TAG, "activity cursor count: " + cursor.getCount() + "\ncursor columns: " + cursor.getColumnCount());

                            while (cursor.moveToNext()) {
                                Log.i(TAG, "activity business id:" + cursor.getInt(1) + "\nBusiness ID:" + businessID);

                                if (String.valueOf(cursor.getInt(1)).equals( businessID)) {
                                    aList.add(new Activity(
                                            cursor.getInt(0),
                                            cursor.getInt(1),
                                            cursor.getString(2),
                                            cursor.getString(3),
                                            Tools.dateFromString(cursor.getString(4)),
                                            Tools.dateFromString(cursor.getString(5)),
                                            Category.fromString(cursor.getString(6)),
                                            cursor.getString(7),
                                            cursor.getDouble(8)));
                                }
                                Log.w(TAG,"activitylist size: "+activitiesList.size());
                            }

                            return true;
                        } catch (Exception e) {
                            Log.e(TAG, "The operation to add activities failed" + e.getMessage());
                            result = -1;
                            return false;
                        }

                    case BUSINESSES_LOADING:
                        try {
                            Log.w(TAG, "Logged user id: " + userID);
                            try {
                                bList.clear();
                                //ContentValues content = Tools.ActivityToContentValues(activity);
                                Cursor cursor = provider.query(iContract.BussinessFields.BUSSINESS_URI, null, null, null, null);
                                Log.w(TAG, "Cursor count: " + cursor.getCount() + " Cursor columns count: " + cursor.getColumnCount());
                                while (cursor.moveToNext()) {
                                    bList.add(new Business(
                                            cursor.getInt(0),
                                            cursor.getInt(1),
                                            cursor.getString(2),
                                            cursor.getString(3),
                                            cursor.getString(4),
                                            cursor.getString(5),
                                            cursor.getString(6),
                                            cursor.getString(7)));
                                    Log.w(TAG, "Business ID: " + cursor.getInt(0) + "\nmanager ID: " + cursor.getInt(1) + "\nbusiness list count: " + businessesList.size());

                                }

                                return true;
                            } catch (Exception e) {
                                Log.e(TAG, "The operation to add activities failed" + e.getMessage());
                                result = -1;
                            }


                            return false;
                        } catch (Exception e) {
                            Log.e(TAG, "Failed to load businesses!");
                        }
                    default:
                        return false;
                }
            } catch (InterruptedException e) {
                return false;
            }
        }

        private Boolean loadBusinesses(String userID) {
            try {
                businessesList.clear();
                activitiesList.clear();
                aList.clear();
                actAdapter.clear();
                //ContentValues content = Tools.ActivityToContentValues(activity);
                Cursor cursor = provider.query(iContract.BussinessFields.BUSSINESS_URI, null,null , null, null);
                Log.w(TAG,"Cursor count: "+cursor.getCount()+" Cursor columns count: "+cursor.getColumnCount());
                while (cursor.moveToNext()) {
                    bList.add(new Business(
                            cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7)));
                    Log.w(TAG,"Business ID: "+cursor.getInt(0)+"\nmanager ID: "+cursor.getInt(1)+"\nbusiness list count: "+businessesList.size());

                }

                return true;
            } catch (Exception e) {
                Log.e(TAG, "The operation to add activities failed" + e.getMessage());
                result = -1;
            }


            return false;
        }

        private Boolean loadActivities(String businessId) {

            if(!businessId.equals("-1")) {
                try {
                    //ContentValues content = Tools.ActivityToContentValues(activity);
                    Cursor cursor = provider.query(iContract.ActivityFields.ACTIVITY_URI, null, null, null, null);
                    Log.w(TAG, "activity cursor count: " + cursor.getCount() + "\ncursor columns: " + cursor.getColumnCount());
                    aList.clear();
                    while (cursor.moveToNext()) {
                        Log.i(TAG, "activity business id:" + cursor.getInt(1) + "\nBusiness ID:" + businessId);

                        if (String.valueOf(cursor.getInt(1)) == businessId) {
                            aList.add(new Activity(
                                    cursor.getInt(0),
                                    cursor.getInt(1),
                                    cursor.getString(2),
                                    cursor.getString(3),
                                    Tools.dateFromString(cursor.getString(4)),
                                    Tools.dateFromString(cursor.getString(5)),
                                    Category.fromString(cursor.getString(6)),
                                    cursor.getString(7),
                                    cursor.getDouble(8)));
                        }

                    }

                    return true;
                } catch (Exception e) {
                    Log.e(TAG, "The operation to add activities failed" + e.getMessage());
                    result = -1;
                }
            }


            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            Log.i(TAG,"Started");
            //Hide the dialog
            dialog.dismiss();

            Log.i(TAG,"dialog hidded");
            if (!success)
            {

                Toast.makeText(getActivity(),"Failed to load "+operationId.toString(),Toast.LENGTH_LONG).show();
            }
            else {
                switch (operationId) {
                    case ACTIVITIES_LOADING:
                        Log.w(TAG,"activitiesList count: "+activitiesList.size());
                        for(Activity item :aList) {
                            if (String.valueOf(item.getBussinessID()).equals(businessID))       //Check if the activity is of current business
                                actAdapter.add(item);

                            Log.w(TAG, "After adapter cleaning list size: " + activitiesList.size());
                            //actAdapter.addAll(aList);
                        }
                        break;
                    case BUSINESSES_LOADING:
                        busAdapter.clear();
                        for (Business item : bList) {
                            busAdapter.add(item);
                            new LoadingTask(TaskProcessType.ACTIVITIES_LOADING, String.valueOf(item.getId())).execute(false);
                        }
                        break;
                }
                /*if(actAdapter.getCount()==0)
                    activityLabel.setVisibility(View.GONE);
                else
                activityLabel.setVisibility(View.VISIBLE);*/

                if(busAdapter.getCount()==0)
                {
                    //businessLabel.setVisibility(View.GONE);
                    emptyFrameIcon.setVisibility(View.VISIBLE);
                }
                else
                {
                    //businessLabel.setVisibility(View.VISIBLE);
                    emptyFrameIcon.setVisibility(View.GONE);
                }
            }
            if(operationId==TaskProcessType.ACTIVITIES_LOADING)
            loadTask=null;
            Log.i(TAG,"Finished");
        }

        @Override
        protected void onCancelled() {
            dialog.dismiss();
            loadTask=null;
        }
    }
}
