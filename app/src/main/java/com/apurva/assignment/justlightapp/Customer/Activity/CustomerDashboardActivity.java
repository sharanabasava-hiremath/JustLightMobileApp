package com.apurva.assignment.justlightapp.Customer.Activity;

/**
 * Created by Apurva on 11/17/2017.
 */
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apurva.assignment.justlightapp.R;
import com.apurva.assignment.justlightapp.Utility.SessionManager;
import com.apurva.assignment.justlightapp.Customer.Fragments.AuthroizationRequsetFragment;
import com.apurva.assignment.justlightapp.Customer.Fragments.GreenSolutionFragment;
import com.apurva.assignment.justlightapp.Customer.Fragments.HelpFragment;
import com.apurva.assignment.justlightapp.Customer.Fragments.HomeFragment;
import com.apurva.assignment.justlightapp.Customer.Fragments.InviteFragment;
import com.apurva.assignment.justlightapp.Customer.Fragments.LogoutFragment;
import com.apurva.assignment.justlightapp.Customer.Fragments.OrderHistoryFragment;
import com.apurva.assignment.justlightapp.Customer.Fragments.ProfileFragment;
import com.apurva.assignment.justlightapp.Customer.Fragments.RatingFragment;
import com.apurva.assignment.justlightapp.Customer.Fragments.SettingsFragment;


public class CustomerDashboardActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener, AuthroizationRequsetFragment.OnFragmentInteractionListener,
        GreenSolutionFragment.OnFragmentInteractionListener, InviteFragment.OnFragmentInteractionListener,
        HelpFragment.OnFragmentInteractionListener,OrderHistoryFragment.OnFragmentInteractionListener,
        RatingFragment.OnFragmentInteractionListener,SettingsFragment.OnFragmentInteractionListener,
        LogoutFragment.OnFragmentInteractionListener{
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;


    // index to identify current nav menu item
    public static int navItemIndex = -1;

    // tags used to attach the fragments
    private static final String TAG_HOME = "Home";
    private static final String TAG_PROFILE = "Profile";
    private static final String TAG_SOLUTIONS = "Solutions";
    private static final String TAG_AUTHORIZATION = "Authorization Request";
    private static final String TAG_ORDER = "Order History";
    private static final String TAG_INVITE = "Invite";
    private static final String TAG_RATING = "Rating";
    private static final String TAG_HELP = "Help";
    private static final String TAG_SETTINGS = "Settings";
    private static final String TAG_LOGOUT = "Logout";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mHandler = new Handler();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
//        fab.setOnClickListener(new View.OnClickListener() {
//                                   @Override
//                                   public void onClick(View view) {
//                                       Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                                               .setAction("Action", null).show();
//                                   }
//        });
        loadNavHeader();
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
    }

    /***
     * Load notification menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website
        txtName.setText("Apurva Kumari");
        txtWebsite.setText("apurva.kumari@sjsu.edu");

        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }
    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // profile
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            case 2:
                // solution fragment
                GreenSolutionFragment solutionFragment = new GreenSolutionFragment();
                return solutionFragment;
            case 3:
                // authorization request fragment
                AuthroizationRequsetFragment requestFragment = new AuthroizationRequsetFragment();
                return requestFragment;

            case 4:
                //  order hostory fragment
                OrderHistoryFragment orderFragment = new OrderHistoryFragment();
                return orderFragment;

            case 5:
                // invite fragment
                InviteFragment inviteFragment = new InviteFragment();
                return inviteFragment;

            case 6:
                // Help fragment
                HelpFragment helpFragment = new HelpFragment();
                return helpFragment;

            case 7:
                // settings fragment
                SettingsFragment settingsFragment = new SettingsFragment();
                return settingsFragment;

            case 8:
                // rating fragment
                RatingFragment ratingFragment = new RatingFragment();
                return ratingFragment;
            /*case 9:
                // Logout fragment
                //LogoutFragment logoutFragment = new LogoutFragment();
                return logoutFragment;*/
            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }


    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_profile:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PROFILE;
                        break;
                    case R.id.nav_greenSolution:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_SOLUTIONS;
                        break;
                    case R.id.nav_authorizationRequest:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_AUTHORIZATION;
                        break;
                    case R.id.nav_orderHistory:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_ORDER;
                        break;
                    case R.id.nav_invite:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_INVITE;
                        break;
                    case R.id.nav_help:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_HELP;
                        break;
                    case R.id.nav_settings:
                        navItemIndex = 7;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;
                    case R.id.nav_rate:
                        navItemIndex = 8;
                        CURRENT_TAG = TAG_RATING;
                        break;
                    case R.id.nav_logout:
                        SessionManager session = new SessionManager(getApplicationContext());
                        session.logoutUser();
                        navItemIndex = 9;
                        CURRENT_TAG = TAG_LOGOUT;
                        startActivity(new Intent(CustomerDashboardActivity.this, UserSignInActivity.class));
                        break;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

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

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex != -1) {
            getMenuInflater().inflate(R.menu.logout_menu, menu);
        }

        // when fragment is notifications, load the menu created for notifications
        if (navItemIndex == 3) {
            getMenuInflater().inflate(R.menu.notification, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
            return true;
        }

        // user is in notifications fragment
        // and selected 'Mark all as Read'
        if (id == R.id.action_mark_all_read) {
            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
        }

        // user is in notifications fragment
        // and selected 'Clear All'
        if (id == R.id.action_clear_notifications) {
            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

// let it stay blank. No need for any code here.

    }
}
