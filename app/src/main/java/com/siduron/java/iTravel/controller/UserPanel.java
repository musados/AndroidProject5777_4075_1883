package com.siduron.java.iTravel.Controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.siduron.java.androidproject5777_4075_4075.R;
import com.siduron.java.iTravel.Model.DataSource.iContract;
import com.siduron.java.iTravel.Model.Entities.NavDrawerItem;

import java.util.ArrayList;
import java.util.Locale;

import static com.siduron.java.iTravel.Model.DataSource.iContract.iSharedPreference.SHARED_NAME;

public class UserPanel extends AppCompatActivity {
    //Sahred reference variables
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;

    //Main layout
    DrawerLayout mainLayout;
    android.support.v7.widget.Toolbar tool;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    // nav drawer title
    private CharSequence mDrawerTitle;
    // used to store app title
    private CharSequence mTitle;
    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private ListAdapter adapter;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);

        setDrawer(savedInstanceState);


        try {
            SplashScreen s = new SplashScreen();
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.setStatusBarColor(this.getColor(R.color.colorPrimary));
        } catch (Exception e) {
        }


        setFlowDirection();
        sharedPreferences = getSharedPreferences(SHARED_NAME, MODE_PRIVATE);
        sharedEditor = sharedPreferences.edit();

        //Clear Logout sharedReference value
        sharedEditor.putBoolean(iContract.LoginUserKeys.LOGOUT_STATUS_KEY, false);
        sharedEditor.commit();
    }


    private void setDrawer(Bundle savedInstanceState) {

        mTitle = mDrawerTitle = getTitle();
        tool=(android.support.v7.widget.Toolbar) findViewById(R.id.actionBar);
        tool.setTitle(mTitle);
        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.user_panel_menu_items);
        // nav drawer icons from resources
        navMenuIcons = getResources().obtainTypedArray(R.array.user_panel_menu_icons);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        mDrawerList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        navDrawerItems = new ArrayList<NavDrawerItem>();
        mainLayout = (DrawerLayout) findViewById(R.id.activity_user_panel);

        // Update
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Settings
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Logout
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));

        // Recycle the typed array         navMenuIcons.recycle();
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                displayView(i);
            }
        });

        // setting the nav drawer list adapter
        adapter = new ArrayAdapter<NavDrawerItem>(this,R.layout.drawer_menu_row_item,navDrawerItems) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null) {
                    convertView = View.inflate(UserPanel.this,
                            R.layout.drawer_menu_row_item,
                            null);
                }


                TextView itemTextView = (TextView) convertView.findViewById(R.id.itemTitle);


                ImageView itemImageView = (ImageView) convertView.findViewById(R.id.itemIcon);

                itemTextView.setText(navDrawerItems.get(position).getTitle());
                itemImageView.setImageResource(navDrawerItems.get(position).getIcon());


                return convertView;
            }

        };

        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button         getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mainLayout,tool,
                R.string.app_name, R.string.app_name);

        //  Connect the drawer to toggle
        mainLayout.setDrawerListener(mDrawerToggle);
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
    }


    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                //fragment = new HomeFragment();
                break;
            case 1:
                //fragment = new FindPeopleFragment();
                break;
            case 2:
                //fragment = new PhotosFragment();
                break;
            case 3:
                //fragment = new CommunityFragment();
                break;
            case 4:
                //fragment = new PagesFragment();
                break;
            case 5:
                //fragment = new WhatsHotFragment();
                break;
            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.FrameContainer, fragment).commit();
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mainLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }


    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override     protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items         boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);         menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.UpdateUserProfile:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


/**
 * Set page flow direction
 */
    private void setFlowDirection() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (Locale.getDefault().getDisplayLanguage().contains("עברית"))
                mainLayout.setLayoutDirection(RelativeLayout.LAYOUT_DIRECTION_RTL);
            else
                mainLayout.setLayoutDirection(RelativeLayout.LAYOUT_DIRECTION_LTR);
        }

    }


    @SuppressWarnings("deprecation")
    protected void logout(View view) {
        Intent loginIntent = new Intent(this, MainActivity.class);

        loginIntent.putExtra(iContract.LoginUserKeys.LOGOUT_STATUS_KEY, true);
        startActivity(loginIntent);
        finish();
    }

    /*@Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_panel_menu, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {     // Handle item selection
        switch (item.getItemId()) {
            case R.id.UpdateUserProfile:
                Toast.makeText(this,"User profile update performed",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.LogoutUser:
                Toast.makeText(this,"Logout performed",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}
