package com.siduron.java.iTravel.Controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.siduron.java.androidproject5777_4075_4075.R;
import com.siduron.java.iTravel.Model.DataSource.iContract;
import com.siduron.java.iTravel.Model.Entities.NavDrawerItem;
import com.siduron.java.iTravel.Model.Entities.User;

import java.util.ArrayList;
import java.util.Locale;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_CONTACTS;
import static android.content.Intent.ACTION_CALL;
import static android.content.Intent.ACTION_DIAL;
import static android.content.Intent.ACTION_SEND;
import static android.content.Intent.ACTION_SENDTO;
import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.ACTION_WEB_SEARCH;
import static com.siduron.java.iTravel.Model.DataSource.iContract.UserFields.USER_MAIN_KEY;
import static com.siduron.java.iTravel.Model.DataSource.iContract.iSharedPreference.SHARED_NAME;

public class UserPanel extends AppCompatActivity
        implements HomeFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener,
        UpdateUserFragment.OnFragmentInteractionListener,
        BusinessesFragment.OnFragmentInteractionListener{

    //Log tag
    private final String TAG = "User control panel";

    //Loged on user details
    private User loggedUser = null;

    private int currentFrameIndex = 0;
    private String currentTag = TAG_HOME;
    private int currentID = ID_HOME;

    // tags used to attach the fragments
    private static String TAG_HOME;//= new HomeFragment().getTag();
    private static String TAG_Update_USER;// = new UpdateUserFragment().getTag();
    private static String TAG_SETTINGS;//= new SettingsFragment().getTag();
    private static String TAG_BUSINESSES;// = "share";
    private static String TAG_SHARE;// = "share";

    private static int ID_HOME;//=R.string.user_home_lists;
    private static int ID_UPDATE;//=R.string.update_user_profile;
    private static int ID_SETTINGS;//=R.string.user_profile_settings;
    private static int ID_BUSSINESS;


    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = false;

    private Handler mHandler = new Handler();

    //Sahred reference variables
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;

    //Main layout
    DrawerLayout mainLayout;
    Toolbar tool;
    NavigationView NavView;
    FloatingActionButton fab;
    Fragment mainFragment;

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

    //User details in the menu
    TextView MenuUser;
    TextView MenuAccount;
    ImageView MenuImage;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    /**
     * OnCreate activity method
     *
     * @param savedInstanceState
     */
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);
        mTitle = mDrawerTitle = getTitle();
        //Set the Strings IDs
        ID_HOME = R.string.user_home_lists;
        ID_UPDATE = R.string.update_user_profile;
        ID_SETTINGS = R.string.user_profile_settings;
        ID_BUSSINESS =R.string.businesses_list_label;
        //Sets the TAGs
        TAG_HOME = "HomeFragment";
        TAG_Update_USER = "UpdateUserFragment";
        TAG_SETTINGS = "SettingsFragment";
        TAG_BUSINESSES = "BusinessesFragment";
        TAG_SHARE = "share";


        setDrawer(savedInstanceState);

        setStatusBar();     //Set status bar color

        setFlowDirection();
        sharedPreferences = getSharedPreferences(SHARED_NAME, MODE_PRIVATE);
        sharedEditor = sharedPreferences.edit();

        //Clear Logout sharedReference value
        sharedEditor.putBoolean(iContract.LoginUserKeys.LOGOUT_STATUS_KEY, false);
        sharedEditor.commit();

        //Try to load the home frame
        try {
            if (savedInstanceState == null) {
                loadHomeFragment();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }


        MenuUser = (TextView) NavView.getHeaderView(0).findViewById(R.id.MenuUsername);
        MenuAccount = (TextView) NavView.getHeaderView(0).findViewById(R.id.MenuUserAccount);
        MenuImage = (ImageView) NavView.getHeaderView(0).findViewById(R.id.MenuUserImage);

        loadLoggedUser();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    /**
     * Load the loged on user from the extras
     */
    private void loadLoggedUser() {
        try {
            loggedUser = (User) getIntent().getSerializableExtra(USER_MAIN_KEY);
            MenuUser.setText("TestUser");
            if (loggedUser != null) {
                MenuUser.setText(loggedUser.getFirstName() + " " + loggedUser.getLastName());
                MenuAccount.setText(loggedUser.getUsername());
            } else
                Log.w(TAG, "User is null");
        } catch (Exception e) {
            Log.e(TAG, "Error during serializing logged user");
        }
    }


    /**
     * Sets the statusBar color
     */
    private void setStatusBar() {
        try {
            SplashScreen s = new SplashScreen();
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.setStatusBarColor(this.getColor(R.color.colorPrimary));
        } catch (Exception e) {
            Log.e(TAG, "Status bar color wasn't setted");
        }
    }


    /**
     * Inits the drawer and his elements
     *
     * @param savedInstanceState
     */
    private void setDrawer(Bundle savedInstanceState) {

        mTitle = mDrawerTitle = getTitle();

        tool = (Toolbar) findViewById(R.id.actionBar);
        tool.setTitle(mTitle);

        NavView = (NavigationView) findViewById(R.id.nav_view);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        setUpNavigationView();

        /* The old Area*/
        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.user_panel_menu_items);
        // nav drawer icons from resources
        ////navMenuIcons = getResources().obtainTypedArray(R.array.user_panel_menu_icons);

        //ListView
        ///mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        ///mDrawerList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //Nav items as NavDrawerItem class ArrayList
        ////navDrawerItems = new ArrayList<NavDrawerItem>();

        //Main Drawer container
        mainLayout = (DrawerLayout) findViewById(R.id.activity_user_panel);

        // Update
        ////navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Settings
        ////navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1,-1)));
        // Logout
        ////navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));

        // Recycle the typed array
        //navMenuIcons.recycle();

       /* mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                displayView(i);
            }
        });*/

        // setting the nav drawer list adapter
        adapter = new ArrayAdapter<NavDrawerItem>(this, R.layout.drawer_menu_row_item, navDrawerItems) {
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

        ////mDrawerList.setAdapter(adapter);


        mDrawerToggle = new ActionBarDrawerToggle(this, mainLayout, tool,
                R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }


        };


        //  Connect the drawer to toggle
        mainLayout.setDrawerListener(mDrawerToggle);

        // enabling action bar app icon and behaving it as toggle button
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //calling sync state is necessary or else your hamburger icon wont show up
        mDrawerToggle.syncState();

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
            //replaceFragment();
        }
    }


    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                currentTag = TAG_HOME;
                break;
            case 1:
                fragment = new UpdateUserFragment();
                currentTag = TAG_Update_USER;
                break;
            case 2:
                fragment = new SettingsFragment();
                currentTag = TAG_SETTINGS;
                //fragment = new CommunityFragment();
                break;
            case 3:
                //fragment = new PagesFragment();
                break;
            case 4:
                //fragment = new WhatsHotFragment();
                break;
            default:
                fragment = new HomeFragment();
                break;
        }
        if (fragment != null) {
            try {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FrameContainer, fragment);
                transaction.addToBackStack(currentTag);
                transaction.commit();
                // update selected item and title, then close the drawer
                //mDrawerList.setItemChecked(position, true);
                //mDrawerList.setSelection(position);
                if (navMenuTitles != null) {
                    String title = navMenuTitles[position];
                    setTitle(title);
                    toggleFab();
                }
                mainLayout.closeDrawer(NavView);
            } catch (Exception e) {
                Log.e(TAG, navMenuTitles[position] + " Position: " + position + "\nfrom: " + navMenuTitles.length + "\n" + e.getMessage());
            }
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }


    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        setSupportActionBar(tool);
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
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
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mainLayout.isDrawerOpen(NavView);
        //menu.findItem(R.id.nav_share).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }


    /**
     * Set page flow direction
     */
    private void setFlowDirection() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (Locale.getDefault().getDisplayLanguage().contains("עברית")) {
                mainLayout.setLayoutDirection(RelativeLayout.LAYOUT_DIRECTION_RTL);
            }
            else {
                mainLayout.setLayoutDirection(RelativeLayout.LAYOUT_DIRECTION_LTR);
            }
        }

    }


    @SuppressWarnings("deprecation")
    protected void logout(View view) {
        Intent loginIntent = new Intent(this, MainActivity.class);

        loginIntent.putExtra(iContract.LoginUserKeys.LOGOUT_STATUS_KEY, true);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_activity_bussiness, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.addActivity:
                addActivity();
                return true;
            case R.id.addBussiness:
                addBusiness();
                return true;

        }
        return onContextItemSelected(item);
    }


    /**
     * Add Activity - starting new "AddActivity" activity
     *
     * with extra of the serializible logged user!
     */
    private void addActivity() {
        Intent addActivity = new Intent(this, AddActivity.class);
        addActivity.putExtra(USER_MAIN_KEY, loggedUser);
        startActivity(addActivity);
    }


    /**
     * Event handler for the business icon displayed in the home frame
     * @param view
     */
    public void EmptFrameIconClick(View view)
    {
        addBusiness();
    }

    /**
     * Add Business - starting new "AddBusiness" activity
     *
     * with extra of the serializible logged user!
     */
    private void addBusiness() {
        Intent addBusiness = new Intent(this, AddBusiness.class);
        addBusiness.putExtra(USER_MAIN_KEY, loggedUser);
        startActivity(addBusiness);
    }


    private void showAddBusinessDialog() {
        AddBusinessDialog businessDialog = new AddBusinessDialog();
        businessDialog.show(getFragmentManager(), TAG);
        businessDialog.setCancelable(true);
    }



    /**
     * Floating action button click event handler
     *
     * @param view Clicked button
     */
    public void floatingActionClick(View view) {
        registerForContextMenu(fab);
        openContextMenu(fab);
        unregisterForContextMenu(fab);
    }


    @Override
    public void onBackPressed() {
        if (mainLayout.isDrawerOpen(GravityCompat.START)) {
            mainLayout.closeDrawers();
            return;
        }


        //int index = getFragmentManager().getBackStackEntryCount() - 1;
        //FragmentManager.BackStackEntry backEntry = (FragmentManager.BackStackEntry) getFragmentManager().getBackStackEntryAt(0);

        //Log.w(TAG,"Name of stack index: "+index);
        //String name=backEntry.getName();

        //Log.w(TAG,"Name of stack entry: "+name);

        //setTitle(navMenuTitles[currentID]);
        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (currentFrameIndex != 0) {
                currentFrameIndex = 0;
                currentTag = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();

        if (mainFragment instanceof HomeFragment)
            setTitle(getResources().getString(ID_HOME));
        else if(mainFragment instanceof UpdateUserFragment)
            setTitle(getResources().getString(ID_UPDATE));
        else if(mainFragment instanceof SettingsFragment)
            setTitle(getResources().getString(ID_SETTINGS));


    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        NavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.UserLists:
                        currentFrameIndex = 0;
                        currentTag = TAG_HOME;
                        Log.w(TAG, "Current Tag: " + currentTag);
                        currentID = ID_HOME;
                        Log.w(TAG, "Home pressed");
                        break;
                    case R.id.UpdateUserDetails:
                        currentFrameIndex = 1;
                        currentTag = TAG_Update_USER;
                        Log.w(TAG, "Current Tag: " + currentTag);
                        currentID = ID_UPDATE;
                        Log.w(TAG, "Update user pressed");
                        break;
                    case R.id.UserSettings:
                        currentFrameIndex = 2;
                        currentTag = TAG_SETTINGS;
                        Log.w(TAG, "Current Tag: " + currentTag);
                        currentID = ID_SETTINGS;
                        Log.w(TAG, "Settings pressed");
                        break;
                    case R.id.LogoutUser:
                        logout(null);
                        Log.w(TAG, "Logout pressed");
                        return false;
                    case R.id.nav_share:
                        Log.w(TAG, "Share pressed");
                        break;
                    case R.id.nav_send:
                        // launch new intent instead of loading fragment
                        //startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        //mainLayout.closeDrawers();
                        Log.w(TAG, "Send pressed");
                        break;

                    default:
                        currentFrameIndex = 0;
                }

                //replaceFragment();

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                displayView(currentFrameIndex);

                return true;
            }
        });




        /*ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mainLayout, tool, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };*/

        //Setting the actionbarToggle to drawer layout
        //// mainLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        ////actionBarDrawerToggle.syncState();
    }

    private void selectNavMenu() {
        try {
            NavView.getMenu().getItem(currentFrameIndex).setChecked(true);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }


    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setTitle(getResources().getString(currentID));

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(currentTag) != null) {
            mainLayout.closeDrawers();

            // show or hide the fab button
            toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                replaceFragment();

            }
        };

        setTitle(getString(currentID));

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        toggleFab();

        //Closing drawer on item click
        mainLayout.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }


    /**
     * Performing the framework replacement
     */
    private void replaceFragment() {
        // update the main content by replacing fragmentsagment
        Fragment fragment = getHomeFragment();
        Log.w(TAG, fragment + " The tag name");
        if (fragment != null && fragment.getTag() != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.FrameContainer, fragment, currentTag);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commitAllowingStateLoss();
        }

        Log.w(TAG, fragment.getView() + " The tag name");

    }

    private Fragment getHomeFragment() {
        switch (currentFrameIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // photos
                UpdateUserFragment updateUserFragment = new UpdateUserFragment();
                return updateUserFragment;
            case 2:
                // movies fragment
                SettingsFragment settingsFragment = new SettingsFragment();
                return settingsFragment;


            default:
                return new HomeFragment();
        }
    }

    // show or hide the fab
    private void toggleFab() {
        if (currentFrameIndex == 0)
            fab.show();
        else
            fab.hide();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_activity_bussiness, menu);
        //registerForContextMenu(fab);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.addBussiness:
                return onOptionsItemSelected(item);
            case R.id.addActivity:
                return onOptionsItemSelected(item);
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("UserPanel Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    /**
     * Make a call when business phone or other phone pressed
     *
     * @param view Phone text view
     */
    public void makeCall(View view) {
        String phone = "";
        Intent callerIntent;

        if (view instanceof TextView) {
            phone = ((TextView) view).getText().toString().trim();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return;
            }
            callerIntent = new Intent(ACTION_DIAL);
            callerIntent.setType("vnd.android.cursor.dir/call");
            callerIntent.setData(Uri.parse("tel:" + phone));

            //Toast.makeText(this, "Call permissins: " + ((checkSelfPermission(ACTION_CALL) == PackageManager.PERMISSION_GRANTED) ? "Granted" : "Denied"), Toast.LENGTH_SHORT).show();

            if (false&&checkSelfPermission(ACTION_CALL) != PackageManager.PERMISSION_GRANTED){
                //request permission from user if the app hasn't got the required permission
                requestPermissions(new String[]{CALL_PHONE},0);
            }
            else {
                try {
                    callerIntent.setAction(android.content.Intent.ACTION_CALL).setData(Uri.parse("tel:"+((TextView) view).getText()));

                    startActivity(callerIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }


    /**
     * Go to the web browser when Link pressed
     * @param view
     */
    public void goToWebsite(View view) {
        if(view instanceof TextView) {
            String website="";
            try {
                website = ((TextView) view).getText().toString().trim();
                if(!website.startsWith("http://")&&!website.startsWith("https://"))
                    website="http://"+website;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    return;
                }
                Intent browserIntent = new Intent(ACTION_VIEW, Uri.parse(website));
                startActivity(browserIntent);
            }
            catch (Exception e)
            {
                Log.e(TAG,"Failed to open website address: "+website);
            }
        }
    }

    /**
     * Go to the web browser when Link pressed
     * @param view
     */
    public void geoSearch(View view) {
        if(view instanceof TextView) {
            String address ="";
            try {
                address = ((TextView) view).getText().toString().trim();

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    return;
                }
                Intent browserIntent = new Intent(ACTION_VIEW);
                browserIntent.setData(Uri.parse("geo:<lat>,<long>?q=("+address+")"));
                startActivity(browserIntent);
            }
            catch (Exception e)
            {
                Log.e(TAG,"Failed to open website address: "+address);
            }
        }
    }

    /**
     * Sending email when email is pressed
     * @param view
     */
    public void sendMail(View view) {
        if(view instanceof TextView) {
            String email ="";
            try {
                email = ((TextView) view).getText().toString().trim();

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    return;
                }

                Intent intent = new Intent(Intent.ACTION_SEND);//,Uri.parse("To"+":"+ email));
                intent.setType("vnd.android.cursor.dir/email");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});

                startActivity(Intent.createChooser(intent,"Send mail"));
            }
            catch (Exception e)
            {
                Log.e(TAG,"Failed to send email - address: "+email);
            }
        }
    }



}
